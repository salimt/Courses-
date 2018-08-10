package ui.tabs;

import ui.ButtonNames;
import ui.SmartHomeUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportTab extends Tab {

    private static final String REPORT_GEN_MESSAGE = "Latest report: Today at ";

    private JScrollPane reportPane;
    private JTextArea reportText;
    private JLabel reportMessage;
    private String currTime;
    private SimpleDateFormat timeFormat;
    private Date currDate;

    //REQUIRES: SmartHomeUI controller that holds this tab
    //EFFECTS: creates report tab with buttons and application status functionality
    public ReportTab(SmartHomeUI controller){
        super(controller);

        currDate = Calendar.getInstance().getTime();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        updateCurrTime();

        placeReportButton();

        JPanel reportBlock = new JPanel(new GridLayout(2, 1));
        reportBlock.setSize(SmartHomeUI.WIDTH - (SmartHomeUI.WIDTH / 5),
                SmartHomeUI.HEIGHT - (SmartHomeUI.HEIGHT / 5));
        reportMessage = new JLabel("");
        reportPane = new JScrollPane(new JTextArea(6, 40));
        reportText = new JTextArea("", 6, 40);
        reportText.setVisible(true);

        reportBlock.add(reportMessage);
        reportBlock.add(reportPane);

        add(reportBlock);
    }

    //MODIFIES: this
    //EFFECTS: adds a generate report button that prints app status when clicked
    private void placeReportButton(){
        JButton b1 = new JButton(ButtonNames.GENERATE_REPORT.getValue());
        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                getController().getSmartHome().update();
                if(buttonPressed.equals(ButtonNames.GENERATE_REPORT.getValue())){
                    updateCurrTime();
                    String message = REPORT_GEN_MESSAGE + currTime;
                    reportMessage.setText(message);
                    reportText.setText(getController().getSmartHome().appsRunningStatus());
                    reportPane.setViewportView(reportText);
                }
            }
        });

        this.add(buttonRow);
    }

    //MODIFIES: this
    //EFFECTS: updates current time in format HH:mm:ss
    private void updateCurrTime(){
        currDate = Calendar.getInstance().getTime();
        currTime = timeFormat.format(currDate);
    }

}
