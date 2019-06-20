/**
 * @author: salimt
 */

package model.exceptions;

public class NotEnoughBeansException extends BeansAmountException {


    public NotEnoughBeansException(double beans) {
        super(beans, (beans + " is not enough beans."));
    }


}
