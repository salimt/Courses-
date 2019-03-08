
/**
 * Find N-closest quakes
 * 
 * @author salimt
 */

import java.util.*;

public class ClosestQuakes {

    public static void main(String[] args) {
        ClosestQuakes cq = new ClosestQuakes();
        cq.findClosestQuakes();
    }

    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList <QuakeEntry>(quakeData);

        for(int i=0; i<howMany; i++){
            int minIndex = 0;
            int counter = 0;
            for(QuakeEntry qe: quakeData){
                if(qe.getLocation().distanceTo(current) < quakeData.get(minIndex).getLocation().distanceTo(current)){
                    minIndex = counter;
                }counter++;
            }ret.add(quakeData.get(minIndex));
            quakeData.remove(minIndex);
        }return ret;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "src/data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+ list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
