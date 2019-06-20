package model;

/**
 * An abstract light somewhere in the theatre.
 */
public abstract class Light {

    private boolean turnedOn;

    public void turnOff() {
        setTurnedOn(false);
    }

    public void turnOn() {
        setTurnedOn(true);
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        this.turnedOn = turnedOn;
    }
}
