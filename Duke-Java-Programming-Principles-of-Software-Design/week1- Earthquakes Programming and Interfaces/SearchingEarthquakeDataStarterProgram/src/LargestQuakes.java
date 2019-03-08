/**
 * @author: salimt
 */

import java.util.ArrayList;

public class LargestQuakes {

    public static void main(String[] args) {
        LargestQuakes lq = new LargestQuakes();
        lq.findLargestQuakes();
    }

    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "src/data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList <QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+ list.size());

        ArrayList<QuakeEntry> filteredList = getLargest(list, 50);
        for(QuakeEntry qe: filteredList){
            System.out.println(qe);
        }
    }

    public int indexOfLargest(ArrayList<QuakeEntry> data){
        double largestMag = 0;
        int index = 0;
        for(int i=0; i<data.size(); i++){
            if(data.get(i).getMagnitude() > largestMag){ largestMag = data.get(i).getMagnitude(); index = i;}
        }return index;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany){
        ArrayList<QuakeEntry> list = new ArrayList <>();
        ArrayList<QuakeEntry> copy = new ArrayList <>(quakeData);
        for(int i=0; i<howMany; i++){
            int largest = indexOfLargest(copy);
            list.add(copy.get(largest));
            copy.remove(copy.get(largest));
        }return list;
    }
}
