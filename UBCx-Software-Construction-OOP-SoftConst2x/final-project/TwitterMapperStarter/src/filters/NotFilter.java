package filters;

import twitter4j.Status;

import java.util.List;

/**
 * A filter that represents the logical not of its child filter
 *
 * @author salimt
 */
public class NotFilter implements Filter {
    private final Filter child;

    public NotFilter(Filter child) {
        this.child = child;
    }

    /**
     * A not filter matches when its child doesn't, and vice versa
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return !child.matches(s);
    }

    @Override
    public List<String> terms() {
        return child.terms();
    }

    public String toString() {
        return "not " + child.toString();
    }
}
