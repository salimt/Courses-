/**
 * @author: salimt
 */

public class DistanceFilter implements Filter {

    private double maxDist;
    private Location loc;

    public DistanceFilter(double dist, Location lc) { maxDist = dist; loc = lc; }

    public String getName(){ return "Distance"; }

    public boolean satisfies(QuakeEntry qe) { return qe.getLocation().distanceTo(loc) <= maxDist; }
}
