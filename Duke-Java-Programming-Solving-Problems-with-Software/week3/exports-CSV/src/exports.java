/**
 * @author: salimt
 */

import edu.duke.FileResource;
import org.apache.commons.csv.*;

public class exports {
    public static void main(String[] args ) {

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
//        System.out.println(countryInfo(parser, "Nauru"));
//        listExportersTwoProducts(parser, "gold", "diamonds");
//        listExportersTwoProducts(parser, "sugar", "");
//        System.out.println(numberOfExporters(parser, "gold"));      //3
//        bigExporters(parser, "$999,999,999");

        //-----QUIZ QUESTIONS-----//

//        listExportersTwoProducts(parser, "cotton", "flowers");
//        listExportersTwoProducts(parser, "cocoa", "");
        bigExporters(parser, "$999,999,999,999");
    }


    public static String countryInfo(CSVParser parser, String country){

        for(CSVRecord record: parser){
            if(record.get("Country").equals(country)){
                System.out.println(country + ": " + record.get("Exports")
                                           + ": " + record.get("Value (dollars)"));
            }
        }
        return "NOT FOUND";
    }

    public static void listExportersTwoProducts(CSVParser parser, String exporItem1, String exportItem2){

        int counter = 0;
        for(CSVRecord record: parser){
            if(record.get("Exports").contains(exporItem1) && record.get("Exports").contains(exportItem2)){
                System.out.println(record.get("Country"));
                counter++;
            }
        }
        System.out.println("Number of countries that export " + exporItem1 + " and " + exportItem2 + " are " + counter);
    }

    public static int numberOfExporters(CSVParser parser, String exportItem){

        int counter = 0;

        for(CSVRecord record: parser){
            if(record.get("Exports").contains(exportItem)){
                counter++;
            }
        }
        return counter;
    }

    public static void bigExporters(CSVParser parser, String amount){

        String newAmount = amount.replaceAll("[$,]", "");

        for(CSVRecord record: parser){
            String currValue = record.get("Value (dollars)").replaceAll("[$,]", "");
            if(Float.parseFloat(currValue) > Float.parseFloat(newAmount)){
                System.out.println(record.get("Country") + ": " + record.get("Value (dollars)"));
            }
        }
    }

}
