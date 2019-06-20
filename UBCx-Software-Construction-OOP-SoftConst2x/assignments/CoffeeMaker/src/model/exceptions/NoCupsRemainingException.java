/**
 * @author: salimt
 */

package model.exceptions;

public class NoCupsRemainingException extends Exception {

    public NoCupsRemainingException() {
        super("There are no cups available!");
    }
}
