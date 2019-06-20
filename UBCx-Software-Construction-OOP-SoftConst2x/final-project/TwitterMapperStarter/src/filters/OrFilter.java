/**
 * @author: salimt
 */

package filters;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A filter that represents the logical or of its child filter
 *
 * @author salimt
 */

public class OrFilter implements Filter{
    private final Filter child;
    private final Filter child2;

    public OrFilter(Filter child, Filter child2) { this.child = child; this.child2 = child2; }

    /**
     * An or filter matches when one of its children do
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return child.matches(s) || child2.matches(s);
    }

    @Override
    public List <String> terms() {
        final List result = new ArrayList(child.terms());
        result.addAll(child2.terms());
        return result;
    }

    public String toString() {
        return "(" + child.toString() + " or " + child2.toString() + ")";
    }
}
