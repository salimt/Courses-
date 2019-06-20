/**
 * @author: salimt
 */

package model.exceptions;

public class TooManyBeansException extends BeansAmountException {

    public TooManyBeansException(double beans) {
        super(beans, " is too many beans.");
    }

}
