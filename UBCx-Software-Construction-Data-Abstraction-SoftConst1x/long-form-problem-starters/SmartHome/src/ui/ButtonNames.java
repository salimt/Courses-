package ui;

public enum ButtonNames {
    ARRIVE ("Arrive Home"),
    LEAVE ("Leave Home"),
    GO_TO_REPORT ("Current Report"),
    GENERATE_REPORT ("Appliance Status"),
    ON ("On"),
    OFF ("Off");

    private final String name;

    ButtonNames(String name){
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue(){
        return name;
    }
}
