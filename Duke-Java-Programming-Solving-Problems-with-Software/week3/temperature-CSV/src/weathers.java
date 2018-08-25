/**
 * @author: salimt
 */


import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.*;

public class weathers {

    public static void main(String[] args) {

        //-----QUIZ QUESTIONS-----//

        testcoldestHourinFile();
        testFileWithColdestTemperature();
        testLowestHumidityInFile();
        testLowestHumidityInManyFiles();
        testAverageTemperatureInFile();
        testAverageTemperatureWithHighHumidityInFile();

    }

    //finds the coldest hour in give day
    public static CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        for(CSVRecord currentRow: parser){
            if(lowestSoFar == null){
                lowestSoFar = currentRow;
            }else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double largest = Double.parseDouble(lowestSoFar.get("TemperatureF"));
                if(currentTemp < largest){
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar;
    }

    //TEST method for coldestHourinFile
    public static void testcoldestHourinFile(){
        FileResource fr = new FileResource();
        CSVRecord largest = coldestHourInFile(fr.getCSVParser());
        try{
            System.out.println("coldest temperature was " + largest.get("TemperatureF")
                                                          + " at " + largest.get("TimeEST"));
        }catch(java.lang.IllegalArgumentException e){
            System.out.println("coldest temperature was " + largest.get("TemperatureF")
                                                          + " at " + largest.get("TimeEDT"));
        }

    }

    //finds the lowest temperature in the given multiple csv files
    public static String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        File fileName = null;

        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);

            if(lowestSoFar == null){
                lowestSoFar = coldestHourInFile(fr.getCSVParser());
            }else{
                double currentLowest = Double.parseDouble(coldestHourInFile(fr.getCSVParser()).get("TemperatureF"));
                if(currentLowest!= -9999 && currentLowest < Double.parseDouble(lowestSoFar.get("TemperatureF"))){
                    lowestSoFar = coldestHourInFile(fr.getCSVParser());
                    fileName = f;
                }
            }
        }
        return fileName.getAbsolutePath();
    }

    //TEST method to find coldestTemperature in the given files
    public static void testFileWithColdestTemperature(){
        String fileWithColdestTemp = fileWithColdestTemperature();
        File f = new File(fileWithColdestTemp);
        FileResource fr = new FileResource(f);

        System.out.println("Coldest day was in file " + f.getName());

        System.out.println("Coldest Temperature is: " + coldestHourInFile(fr.getCSVParser()).get("TemperatureF"));

        System.out.println("All the Temperatures on the coldest day were:");
        for(CSVRecord data: fr.getCSVParser()){
            System.out.println(data.get("DateUTC") + ": " + data.get("TemperatureF"));
        }
    }

    //return the lowest humidity in the given file
    //if same output exists in different location returns the first one
    public static CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        for(CSVRecord currentRow: parser){
            if(lowestSoFar == null){
                lowestSoFar = currentRow;
            }else{
                try{
                    double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
                    double largest = Double.parseDouble(lowestSoFar.get("Humidity"));
                    if(currentTemp < largest){
                        lowestSoFar = currentRow;
                    }
                }catch(NumberFormatException e){
                    continue;
                }

            }
        }
        return lowestSoFar;
    }

    //TEST method to find lowestHumidity in the given file
    public static void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }

    //TEST method to find lowestHumidity in the given files --many
    public static CSVRecord lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;

        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);

            if(lowestSoFar == null){
                lowestSoFar = lowestHumidityInFile(fr.getCSVParser());
            }else{
                double currentLowest = Double.parseDouble(lowestHumidityInFile(fr.getCSVParser()).get("Humidity"));
                if(currentLowest < Double.parseDouble(lowestSoFar.get("Humidity"))){
                    lowestSoFar = lowestHumidityInFile(fr.getCSVParser());
                }
            }
        }
        return lowestSoFar;
    }

    //TEST method to find lowestHumidity in the given files --many
    public static void testLowestHumidityInManyFiles(){
        CSVRecord lowestHumidity = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowestHumidity.get("Humidity") + " at " + lowestHumidity.get("DateUTC"));
    }

    //returns the average temperature of the given day/file
    public static double averageTemperatureInFile(CSVParser parser){
        double sumTemp = 0, countedHours = 0;

        for(CSVRecord data: parser){
            sumTemp += Double.parseDouble(data.get("TemperatureF"));
            countedHours++;
        }
        return sumTemp / countedHours;
    }

    //TEST method to calculate average temperature in the given day/file
    public static void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double result = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + result);
    }

    //method returns a double that represents the average
    //temperature of only those temperatures when the humidity was greater than or equal to value
    public static double averageTemperatureWithHighHumidityInFile(CSVParser parser, Integer value){
        double sumTemp = 0, countedHours = 0;
        for(CSVRecord data: parser){
            if(Double.parseDouble(data.get("Humidity")) >= value){
                sumTemp += Double.parseDouble(data.get("TemperatureF"));
                countedHours++;
            }
        }
        return sumTemp / countedHours;
    }

    //TEST method to calculate averageTemperatureWithHighHumidityInFile
    public static void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double result = averageTemperatureWithHighHumidityInFile(parser, 80);
        if (Double.isNaN(result)) {
            System.out.println("No temperatures with that humidity");
        }else{
            System.out.println(" Average Temp when high Humidity is " + result);
        }

    }
}
