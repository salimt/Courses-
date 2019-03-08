/**
 * @author: salimt
 */

public class DepthFilter implements Filter {

    private double minDepth;
    private double maxDepth;

    public DepthFilter(double min, double max) { minDepth = min; maxDepth = max; }

    public String getName(){ return "Depth"; }

    public boolean satisfies(QuakeEntry qe) { return (qe.getDepth() >= minDepth && qe.getDepth() <= maxDepth); }
}
