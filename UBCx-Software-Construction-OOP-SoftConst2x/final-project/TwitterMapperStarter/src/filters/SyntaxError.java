package filters;

/**
 * The exception thrown when parsing a string fails.
 */
public class SyntaxError extends Exception {
    public SyntaxError(String s) {
        super(s);
    }
    public SyntaxError() {
        super();
    }
}
