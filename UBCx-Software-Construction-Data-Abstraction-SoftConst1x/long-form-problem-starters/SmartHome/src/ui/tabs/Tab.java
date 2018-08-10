package ui.tabs;

import ui.SmartHomeUI;

import javax.swing.*;
import java.awt.*;

public abstract class Tab extends JPanel {

    private SmartHomeUI controller;

    //REQUIRES: SmartHomeUI controller that holds this tab
    public Tab(SmartHomeUI controller){
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b){
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public SmartHomeUI getController(){
        return controller;
    }

}
