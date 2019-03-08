
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class EarthQuakeParser {
    public EarthQuakeParser() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> read(String source) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Document document = builder.parse(new File(source));
            //Document document = builder.parse("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom");
            Document document = null;

            if (source.startsWith("http")){
                document = builder.parse(source);
            }
            else {
                document = builder.parse(new File(source));
            }
            //Document document = builder.parse("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom");

            NodeList nodeList = document.getDocumentElement().getChildNodes();

            ArrayList<QuakeEntry> list = new ArrayList<QuakeEntry>();

            for(int k=0; k < nodeList.getLength(); k++){
                Node node = nodeList.item(k);

                if (node.getNodeName().equals("entry")) {
                    Element elem = (Element) node;
                    NodeList t1 = elem.getElementsByTagName("georss:point");
                    NodeList t2 = elem.getElementsByTagName("title");
                    NodeList t3 = elem.getElementsByTagName("georss:elev");
                    double lat = 0.0, lon = 0.0, depth = 0.0;
                    String title = "NO INFORMATION";
                    double mag = 0.0;

                    if (t1 != null) {
                        String s2 = t1.item(0).getChildNodes().item(0).getNodeValue();
                        //System.out.print("point2: "+s2);
                        String[] args = s2.split(" ");
                        lat = Double.parseDouble(args[0]);
                        lon = Double.parseDouble(args[1]);
                    }
                    if (t2 != null){
                        String s2 = t2.item(0).getChildNodes().item(0).getNodeValue();

                        String mags = s2.substring(2,s2.indexOf(" ",2));
                        if (mags.contains("?")) {
                            mag = 0.0;
                            System.err.println("unknown magnitude in data");
                        }
                        else {
                            mag = Double.parseDouble(mags);
                            //System.out.println("mag= "+mag);
                        }
                        int sp = s2.indexOf(" ",5);
                        title = s2.substring(sp+1);
                        if (title.startsWith("-")){
                            int pos = title.indexOf(" ");
                            title = title.substring(pos+1);
                        }
                    }
                    if (t3 != null){
                        String s2 = t3.item(0).getChildNodes().item(0).getNodeValue();
                        depth = Double.parseDouble(s2);
                    }
                    QuakeEntry loc = new QuakeEntry(lat,lon,mag,title,depth);
                    list.add(loc);
                }

            }
            return list;
        }
        catch (ParserConfigurationException pce){
            System.err.println("parser configuration exception");
        }
        catch (SAXException se){
            System.err.println("sax exception");
        }
        catch (IOException ioe){
            System.err.println("ioexception");
        }
        return null;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
        EarthQuakeParser xp = new EarthQuakeParser();
        //String source = "data/2.5_week.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = xp.read(source);
        Collections.sort(list);
        for(QuakeEntry loc : list){
            System.out.println(loc);
        }
        System.out.println("# quakes = "+list.size());

    }
    
}
