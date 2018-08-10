package model.cardio;

public abstract class CardioMachine {

    private int minutes;
    private int level;
    protected boolean hasIntervalSetting;

    // REQUIRES: minutes > 0, level >= 1
    public CardioMachine(int minutes, int level){
        this.minutes = minutes;
        this.level = level;
    }

    // getters
    public int getMinutesRemaining() { return minutes; }
    public int getLevel() { return level; }
    public boolean hasIntervalSetting() { return hasIntervalSetting; }

    // setters
    public void setMinutes(int minutes) { this.minutes = minutes; }

    // MODIFIES: this
    // EFFECTS: increases level by 1
    public void levelUp() {
        level++;
    }

    // MODIFIES: this
    // EFFECTS: decreases level by 1
    public void levelDown(){
        if(level > 1)
            level--;
    }


}