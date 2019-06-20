/**
 * @author: salimt
 */

package model.exceptions;

public class StaleCoffeeException extends Exception {
    private int lastBrew;

    public StaleCoffeeException(int lastBrew) {
        super("It has been a long time since last brew: " + lastBrew);
        this.lastBrew = lastBrew;
    }

    //getter
    public int getLastBrew() {
        return lastBrew;
    }
}
