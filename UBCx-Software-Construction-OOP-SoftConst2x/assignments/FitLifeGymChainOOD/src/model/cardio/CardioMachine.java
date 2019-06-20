package model.cardio;

public abstract class CardioMachine {

    private int minutes;
    private int level;
    protected boolean hasIntervalSetting;

    //REQUIRES: minutes, level > 0
    //EFFECTS: turns on this cardio machine for time mins at level
    public void turnOn(int minutes, int level){
        this.minutes = minutes;
        this.level = level;
    }

    //EFFECTS: returns true if this machine does intervals
    public boolean hasIntervalSetting(){
        return hasIntervalSetting;
    }

    //EFFECTS: returns the number of minutes remaining
    public int getMinutesRemaining(){
        return minutes;
    }

    //REQUIRES: minutes > 0
    //MODIFIES: this
    //EFFECTS: sets minutes to given minutes
    public void setMinutes(int minutes){
        this.minutes = minutes;
    }

    //EFFECTS: returns level
    public int getLevel(){
        return level;
    }

    //MODIFIES: this
    //EFFECTS: increases level by 1
    public void levelUp(){
        level++;
    }

    //MODIFIES: this
    //EFFECTS: decreases level by 1
    public void levelDown(){
        if(level > 1)
            level--;
    }


}
