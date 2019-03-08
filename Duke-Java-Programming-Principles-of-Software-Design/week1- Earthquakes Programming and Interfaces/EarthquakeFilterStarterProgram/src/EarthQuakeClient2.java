import java.util.ArrayList;

public class EarthQuakeClient2 {

    public static void main(String[] args) {
        EarthQuakeClient2 eqc = new EarthQuakeClient2();
        //eqc.quakesWithFilter();
        //eqc.testMatchAllFilter();
        eqc.testMatchAllFilter2();
    }

    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    }/*
    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "src/data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        ArrayList<QuakeEntry> mfFiltered  = filter(list, new MagnitudeFilter(4.0, 5.0));
        ArrayList<QuakeEntry> dfFiltered  = filter(mfFiltered, new DepthFilter(-35000.0, -12000.0));
        for (QuakeEntry qe: dfFiltered) { System.out.println(qe); }
    }*/

    public void quakesWithFilter(){
        int counter = 0;/*
        for(QuakeEntry qe: filter(filter(new EarthQuakeParser().read("src/data/nov20quakedata.atom"),
                                            new PhraseFilter("end", "a")),
                                                    new DistanceFilter(1000000,
                                                            new Location(39.7392, -104.9903)))){ */
        for(QuakeEntry qe: filter(filter(new EarthQuakeParser().read("src/data/nov20quakedata.atom"),
                    new MagnitudeFilter(3.5, 4.5)),
                    new DepthFilter(-55000, -20000))){

            System.out.println(qe);
            counter++;
        }System.out.println(counter);
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

    public void testMatchAllFilter(){
        ArrayList<QuakeEntry> list  = new EarthQuakeParser().read("src/data/nov20quakedata.atom");
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();

        maf.addFilter(new MagnitudeFilter(1.0, 4.0));
        maf.addFilter(new PhraseFilter("any", "o"));
        maf.addFilter(new DepthFilter(-180000.0, -30000.0));

        int counter = 0;
        for(QuakeEntry qe: filter(list,maf)){
            System.out.println(qe); counter++; } System.out.println(counter);
        //filter(filter(filter(list, new MagnitudeFilter(0.0, 2.0)), new DepthFilter(-100000.0, -10000.0)), new PhraseFilter("any", "a"));
    }

    public void testMatchAllFilter2(){
        ArrayList<QuakeEntry> list  = new EarthQuakeParser().read("src/data/nov20quakedata.atom");
        System.out.println("read data for "+list.size()+" quakes");
        MatchAllFilter maf = new MatchAllFilter();

        maf.addFilter(new MagnitudeFilter(0.0, 5.0));
        maf.addFilter(new PhraseFilter("any", "e"));
        maf.addFilter(new DistanceFilter(3000000, new Location(55.7308, 9.1153)));

        int counter = 0;
        for(QuakeEntry qe: filter(list,maf)){
            System.out.println(qe); counter++; } System.out.println(counter);

        System.out.println("Filters used are: " + maf.getName());
    }

}
