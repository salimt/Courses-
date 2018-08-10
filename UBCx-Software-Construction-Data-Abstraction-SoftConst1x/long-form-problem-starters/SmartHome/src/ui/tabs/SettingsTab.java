package ui.tabs;

import model.appliances.Appliance;
import ui.ButtonNames;
import ui.SmartHomeUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsTab extends Tab {

    private static final String SETTINGS_TEXT = "Change preferences using the menu below.";
    private static final String DIALOG_TYPE = "Error";
    private static final String INVALID_ON = "You can't turn on this appliance while you're away from home.";
    private static final String INVALID_OFF = "You must uncheck \"Runs While Away?\" before turning this off.";

    private static final int COLS_IN_ROW = 4;
    private static final int ROW_HEIGHT = 50;
    private static final int NUMBER_OF_TICKS = 5;
    private static final int LEFT_PADDING = 70;
    private static final int BOTTOM_PADDING = 5;
    private static final int CENTER = SwingConstants.CENTER;
    private static final int TOP = SwingConstants.TOP;

    private Border border;
    private GridLayout rowLayout;

    //REQUIRES: SmartHomeUI controller that holds this tab
    //EFFECTS: creates settings tab that sets on/off, temp and whether runs while away for each
    //         appliance managed by main controller console
    public SettingsTab(SmartHomeUI controller){
        super(controller);

        border = BorderFactory.createEmptyBorder(0, LEFT_PADDING, BOTTOM_PADDING, 0);
        rowLayout = new GridLayout(1, COLS_IN_ROW);

        JLabel settings = new JLabel(SETTINGS_TEXT);
        add(settings);

        JPanel headerRow = new JPanel(rowLayout);
        headerRow.setPreferredSize(new Dimension(SmartHomeUI.WIDTH - LEFT_PADDING, ROW_HEIGHT));
        headerRow.add(new JLabel("Appliance", CENTER));
        headerRow.add(new JLabel("Temperature", CENTER));
        headerRow.add(new JLabel("On/Off", CENTER));
        headerRow.add(new JLabel("Runs While Away?", CENTER));

        add(headerRow);

        for(Appliance a: controller.getSmartHome().getAllAppliances()){
            this.add(makeOneApplianceControl(a));
        }

    }

    //REQUIRES: a != null
    //MODIFIES: this
    //EFFECTS: creates single row to control appliance temp, on/off and whether runs while away
    private JPanel makeOneApplianceControl(Appliance a){
        JPanel row = new JPanel(rowLayout);
        row.setPreferredSize(new Dimension(SmartHomeUI.WIDTH, ROW_HEIGHT));
        row.setBorder(border);

        JLabel name = new JLabel(a.getClass().getSimpleName());
        name.setVerticalAlignment(TOP);

        JSlider slider = getTempSlider(a);
        JCheckBox checkBox = getCheckBox(a);
        JPanel onOffButtons = getOnOffButtons(a, slider);

        row.add(name);
        row.add(slider);
        row.add(onOffButtons);
        row.add(checkBox);
        return row;
    }

    //REQUIRES: a != null
    //EFFECTS: returns slider to control a's temp
    private JSlider getTempSlider(Appliance a){
        JSlider slider = new JSlider(a.minTemp(), a.maxTemp(), a.getTemp());
        slider.setMajorTickSpacing(Math.round((a.maxTemp() - a.minTemp()) / NUMBER_OF_TICKS));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SliderListener(a));

        return slider;
    }

    //REQUIRES: a != null
    //EFFECTS: returns checkbox to control whether a runs while away
    private JCheckBox getCheckBox(Appliance a){
        JCheckBox check = new JCheckBox("");
        check.setSelected(a.runsWhileAway());
        check.setHorizontalAlignment(CENTER);
        check.setVerticalAlignment(TOP);
        check.addActionListener(new CheckboxListener(a));

        return check;
    }

    //REQUIRES: a, s != null
    //EFFECTS: creates on/off buttons in group with listeners for this appliance
    private JPanel getOnOffButtons(Appliance a, JSlider slider){
        JRadioButton on = new JRadioButton(ButtonNames.ON.getValue(), a.isRunning());
        JRadioButton off = new JRadioButton(ButtonNames.OFF.getValue(), !a.isRunning());

        ButtonGroup buttons = new ButtonGroup();
        buttons.add(on);
        buttons.add(off);
        on.addActionListener(new OnOffListener(a, slider, buttons));
        off.addActionListener(new OnOffListener(a, slider, buttons));

        JPanel onOffButton = new JPanel();
        onOffButton.add(on);
        onOffButton.add(off);

        return onOffButton;
    }




    private class CheckboxListener implements ActionListener {
        Appliance app;

        //REQUIRES: Appliance a != null
        public CheckboxListener(Appliance a){
            app = a;
        }

        //MODIFIES: this, a
        //EFFECTS: sets appliance to run while away
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            boolean runsWhileAway = checkBox.getModel().isSelected();
            app.setRunsWhileAway(runsWhileAway);
        }
    }

    private class OnOffListener implements ActionListener {
        Appliance app;
        JSlider slider;
        ButtonGroup parent;

        //REQUIRES: a, s and p != null
        public OnOffListener(Appliance a, JSlider s, ButtonGroup p){
            app = a;
            slider = s;
            parent = p;
        }

        //MODIFIES: this, a, p
        //EFFECTS: if 'on' button clicked, turns on unless no one is home and it doesn't run while away.
        //         if 'off' button clicked, turns off unless no one is home and it does run while away.
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if(command.equals(ButtonNames.ON.getValue())) {
                if(!getController().getSmartHome().isHome() && !app.runsWhileAway()){
                    JOptionPane.showMessageDialog(slider, INVALID_ON, DIALOG_TYPE, JOptionPane.ERROR_MESSAGE);
                    parent.clearSelection();
                } else {
                    app.turnOn();
                    app.setTemp(slider.getValue());
                }
            } else if (command.equals(ButtonNames.OFF.getValue())) {
                if(app.runsWhileAway()) {
                    JOptionPane.showMessageDialog(slider, INVALID_OFF, DIALOG_TYPE, JOptionPane.ERROR_MESSAGE);
                    parent.clearSelection();
                } else {
                    app.turnOff();
                }
            }
        }
    }

    private class SliderListener implements ChangeListener {

        Appliance app;

        //REQUIRES: Appliance a != null
        public SliderListener(Appliance a){
            app = a;
        }

        //MODIFIES: this, a
        //EFFECTS: sets temperature of appliance to value of slider
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            if(!slider.getValueIsAdjusting()){
                app.setTemp(slider.getValue());
            }
        }
    }
}
