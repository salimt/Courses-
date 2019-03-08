/**
 * @author: salimt
 */

import java.util.Comparator;
public class TitleLastAndMagnitudeComparator implements Comparator <QuakeEntry> {


    public int compare(QuakeEntry q1, QuakeEntry q2){
        String lastWord1 = q1.getInfo().substring(q1.getInfo().lastIndexOf(", ") + 1).trim();
        String lastWord2 = q2.getInfo().substring(q2.getInfo().lastIndexOf(", ") + 1).trim();
        int titleComp = lastWord1.compareTo(lastWord2);
        if(titleComp!=0){ return titleComp; }
        return Double.compare(q1.getMagnitude(), q2.getMagnitude());
    }
}