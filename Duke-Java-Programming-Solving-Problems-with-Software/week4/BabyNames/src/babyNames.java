/**
 * Final Project - Analyzing baby names data from 1880 to 2014
 * @author: salimt
 */

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class babyNames {
    public static void main(String[] args) {

//        testTotalBirths();
//        testGetRank();
//        testGetName();
//        testWhatIsNameInYear();
//        testYearOfHighestRank();
//        testGetAverageRank();
//        testGetTotalBirthsRankedHigher();

        //----QUIZ Questions----\\
//        System.out.println(getRank(1960, "Emily", "F"));                          //251
//        System.out.println(getRank(1971, "Frank", "M"));                          //54
//        System.out.println(getName(1980, 350, "F"));                              //Mia
//        System.out.println(getName(1982, 450, "M"));                              //Forrest
//        whatIsNameInYear("Susan", 1972, 2014, "F");                               //Addison
//        whatIsNameInYear("Owen", 1974, 2014, "M");                                //Leonel
//        System.out.println(yearOfHighestRank("Genevieve", "F"));                  //1914
//        System.out.println(yearOfHighestRank("Mich", "M"));                       //1960
//        System.out.println(getAverageRank("Susan", "F"));                         //173.51
//        System.out.println(getAverageRank("Robert", "M"));                        //10.75
//        System.out.println(getTotalBirthsRankedHigher(1990, "Emily", "F"));       //323200
//        System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M" ));       //1498074

    }

    //returns total birth statistics of the given year/file
    public static void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalBoys = 0, totalGirls = 0;
        int boyNames = 0, girlNames = 0;

        for(CSVRecord record: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(record.get(2));         //gets the number of babies born with the name
            totalBirths += numBorn;
            if(record.get(1).equals("M")){
                totalBoys += numBorn;
                boyNames++;
            }else{
                totalGirls += numBorn;
                girlNames++;
            }
        }
        System.out.println("Total births: " + totalBirths + " Total names: " + boyNames+girlNames);
        System.out.println("Total boys: " + totalBoys + " Total boy names: " + boyNames);
        System.out.println("Total girls: " + totalGirls + " Total girl names: " + girlNames);
    }

    //TEST method for totalBirths
    public static void testTotalBirths(){
        FileResource fr = new FileResource();
        totalBirths(fr);
    }

    //method returns the rank of the name in the file for the given gender,
    //where rank 1 is the name with the largest number of births. If the name is not in the file, then -1 is returned
    public static int getRank(Integer year, String name, String gender){
        FileResource fr = new FileResource("yob" + year + ".csv");
        int rank = 0;

        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){ rank++; }
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)){
                return rank;
            }
        }
        return -1;
    }

    //TEST method for getRank
    public static void testGetRank(){
        System.out.println(getRank(2012, "Mason", "M"));    //2
        System.out.println(getRank(2012, "Mason", "F"));    //-1
        System.out.println(getRank(2012, "Ava", "F"));    //5
    }

    //method returns the name of the person in the file at this rank, for the given gender,
    //where rank 1 is the name with the largest number of births. If the rank does not exist in the file,
    //then “NO NAME” is returned.
    public static String getName(Integer year, Integer rank, String gender){
        FileResource fr = new FileResource("yob" + year + ".csv");
        int nameRank = 0;

        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){ nameRank++; }

            if(nameRank == rank && rec.get(1).equals(gender)){
                return rec.get(0);
            }
        }
        return "NO NAME";
    }

    //TEST method for getName
    public static void testGetName(){
        System.out.println(getName(2012, 2, "M"));    //Mason
        System.out.println(getName(2012, 5, "M"));    //NO NAME
        System.out.println(getName(2012, 5, "F"));    //Ava
    }

    //method determines what name would have been named if they were born in a
    //different year, based on the same popularity. That is, you should determine the rank of name
    //in the year they were born, and then print the name born in newYear that is at the same rank and same gender.
    public static void whatIsNameInYear(String name, Integer year, Integer newYear, String gender){
        int oldRank = getRank(year, name, gender);
        String newName = getName(newYear, oldRank, gender);

        System.out.println(name + "born in " + year + " would be " + newName + " if he/she was born in " + newYear);
    }

    //TEST method for whatIsNameInYear
    public static void testWhatIsNameInYear(){
        whatIsNameInYear("Isabella", 2012, 2014, "F");      //Sophia
        whatIsNameInYear("Noah", 2013, 2012, "M");          //Jacob
    }

    //method selects a range of files to process and returns an integer, the year with the
    //highest rank for the name and gender.
    //If the name and gender are not in any of the selected files, it should return -1
    public static int yearOfHighestRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int highestRank = 0;
        int highestYear = -1;

        for(File f: dr.selectedFiles()){
            int currYear = Integer.parseInt(f.getName().replaceAll("[^\\d]", ""));
            int currRank = getRank(currYear, name, gender);

            if(highestRank == 0 && currRank != -1){
                highestRank = currRank;
                highestYear = currYear;
            }
            if(currRank < highestRank && currRank != -1){
                highestRank = currRank;
                highestYear = currYear;
            }
        }
        return highestYear;
    }

    //TEST method for yearOfHighestRank
    public static void testYearOfHighestRank(){
        System.out.println(yearOfHighestRank("Isabella", "F"));     //2012
        System.out.println(yearOfHighestRank("Lee", "M"));          //-1
        System.out.println(yearOfHighestRank("Emma", "F"));         //2014
        System.out.println(yearOfHighestRank("Emma", "M"));         //-1
    }

    //method selects a range of files to process and returns a double representing the average rank
    //of the name and gender over the selected files. It should return -1.0 if the name is not ranked
    //in any of the selected files
    public static double getAverageRank(String name, String gender){
        DirectoryResource dr = new DirectoryResource();
        int totalRank = 0, countedYears = 0;

        for(File f: dr.selectedFiles()){
            int currYear = Integer.parseInt(f.getName().replaceAll("[^\\d]", ""));
            int currRank = getRank(currYear, name, gender);

            if(currRank != -1){
                totalRank += currRank;
                countedYears++;
            }
        }
        double result = (double)totalRank/countedYears;
        if(result > 0){ return result; }
        return -1;
    }

    //TEST method for getAverageRank
    public static void testGetAverageRank(){
        System.out.println(getAverageRank("Sophia", "F"));      //1.66
        System.out.println(getAverageRank("Mason", "M"));       //3.0
        System.out.println(getAverageRank("Jacob", "M"));       //2.66
        System.out.println(getAverageRank("Lee", "M"));         //-1
        System.out.println(getAverageRank("Jacob", "F"));       //-1
    }

    //method returns an integer, the total number of births of those names with the same gender
    //and same year who are ranked higher than name
    public static int getTotalBirthsRankedHigher(Integer year, String name, String gender){
        FileResource fr = new FileResource("yob" + year + ".csv");
        int births = 0;
        int personsRank = getRank(year, name, gender);         //given persons rank in the given year

        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                if(personsRank  > getRank(year, rec.get(0), gender)){
                    births += Integer.parseInt(rec.get(2));
                }
            }
        }
        return births;
    }

    //TEST method for getTotalBirthsRankedHigher
    public static void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(2012, "Ethan", "M"));     //15
        System.out.println(getTotalBirthsRankedHigher(2012, "Emma", "F"));      //10
        System.out.println(getTotalBirthsRankedHigher(2012, "Isabella", "F"));  //19
        System.out.println(getTotalBirthsRankedHigher(2012, "Sophia", "F"));    //0
        System.out.println(getTotalBirthsRankedHigher(2012, "Lee", "M"));       //0
    }

}
