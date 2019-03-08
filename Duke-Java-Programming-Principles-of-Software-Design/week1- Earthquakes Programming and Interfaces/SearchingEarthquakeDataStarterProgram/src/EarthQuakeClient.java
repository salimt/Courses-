/**
 *
 * @author salimt
 */

import java.util.ArrayList;

public class EarthQuakeClient {

    public static void main(String[] args) {
        EarthQuakeClient eqc = new EarthQuakeClient();
        //eqc.bigQuakes();
        //eqc.closeToMe();
        //eqc.quakesOfDepth();
        eqc.quakesByPhrase();
    }

    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            if(qe.getMagnitude() > magMin){ answer.add(qe); }
        }return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax, Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            if(qe.getLocation().distanceTo(from) < distMax){
                answer.add(qe);
            }
        }return answer;
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
                                                   double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            if(minDepth < qe.getDepth() && qe.getDepth() < maxDepth){answer.add(qe); }
        }return answer;
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
                                               String where, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            String title =  qe.toString().substring(qe.toString().indexOf("title =")+8);
            if(phrase.equals("start")){
                if(title.startsWith(where)){ answer.add(qe); }
            }else if(phrase.equals("end")){
                if(title.endsWith(where)){ answer.add(qe); }
            }else{ if(title.contains(where)){ answer.add(qe); }}

        }return answer;
    }


    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "src/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        ArrayList<QuakeEntry> filteredList = filterByMagnitude(list, 5.0);
        for(QuakeEntry qe: filteredList){
            System.out.println(qe);
        }
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "src/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);

        ArrayList<QuakeEntry> filteredList = filterByDistanceFrom(list, 1000000, city);
        for(QuakeEntry qe: filteredList){
            //System.out.println(qe);
            System.out.println(qe.getLocation().distanceTo(city) + qe.toString().substring(qe.toString().indexOf("title =")+7));
        }
    }

    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "src/data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        double minDepth = -4000.0;
        double maxDepth = -2000.0;
        ArrayList<QuakeEntry> filteredList = filterByDepth(list, minDepth, maxDepth);
        System.out.println("Find quakes with depth between " + minDepth + " and " + maxDepth);
        for(QuakeEntry qe: filteredList){
            System.out.println(qe);
        }System.out.println("Found " + filteredList.size() + " quakes that match criteria");
    }

    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "src/data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        String where = "Can";
        String phrase = "any";
        ArrayList<QuakeEntry> filteredList = filterByPhrase(list, where, phrase);
        for(QuakeEntry qe: filteredList){
            System.out.println(qe);
        }System.out.println("Found " + filteredList.size() + " that match " + where + " at " + phrase);
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe.getInfo());
        }
    }
    
}
