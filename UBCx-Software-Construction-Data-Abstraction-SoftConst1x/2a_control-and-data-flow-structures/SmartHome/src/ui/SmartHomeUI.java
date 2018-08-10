package ui;

import model.SmartHome;
import model.appliances.Appliance;
import model.appliances.Fireplace;
import model.appliances.HeatingAC;
import model.appliances.Oven;
import model.appliances.Refrigerator;
import ui.tabs.HomeTab;
import ui.tabs.ReportTab;
import ui.tabs.SettingsTab;

import javax.swing.*;

public class SmartHomeUI extends JFrame {
    public static final int HOME_TAB_INDEX = 0;
    public static final int SETTINGS_TAB_INDEX = 1;
    public static final int REPORT_TAB_INDEX = 2;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private JTabbedPane sidebar;
    private SmartHome smartHome;

    public static void main(String[] args) {
        new SmartHomeUI();
    }

    //MODIFIES: this
    //EFFECTS: creates SmartHomeUI, loads SmartHome appliances, displays sidebar and tabs
    private SmartHomeUI() {
        super("SmartHome Console");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        smartHome = new SmartHome();
        loadAppliances();

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    //EFFECTS: returns SmartHome object controlled by this UI
    public SmartHome getSmartHome(){
        return smartHome;
    }

    //MODIFIES: this
    //EFFECTS: installs several appliances and sets no one home
    private void loadAppliances(){
        Appliance fridge = new Refrigerator(5);
        Appliance oven = new Oven(0);
        Appliance ac = new HeatingAC(18);
        Appliance fireplace = new Fireplace(0);

        smartHome.install(fridge);
        smartHome.install(oven);
        smartHome.install(ac);
        smartHome.install(fireplace);

        ac.setRunsWhileAway(true);
        fridge.setRunsWhileAway(true);

        smartHome.leaveHome();
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs() {
        JPanel homeTab = new HomeTab(this);
        JPanel settingsTab = new SettingsTab(this);
        JPanel reportTab = new ReportTab(this);

        sidebar.add(homeTab, HOME_TAB_INDEX);
        sidebar.setTitleAt(HOME_TAB_INDEX, "Home");
        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
        sidebar.add(reportTab, REPORT_TAB_INDEX);
        sidebar.setTitleAt(REPORT_TAB_INDEX, "Report");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane(){
        return sidebar;
    }

}