/**
 * @author: salimt
 */
import java.util.Comparator;

public class TitleAndDepthComparator implements Comparator <QuakeEntry> {
    public int compare(QuakeEntry q1, QuakeEntry q2){
        int titleComp = q1.getInfo().compareTo(q2.getInfo());
        if(titleComp!=0){ return titleComp; }
        return -Double.compare(q1.getDepth(), q2.getDepth());
    }
}

