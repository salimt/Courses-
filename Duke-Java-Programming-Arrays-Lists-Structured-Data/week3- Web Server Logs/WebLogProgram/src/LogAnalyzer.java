/**
 * @author: salimt
 */

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {

    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        this.records = new ArrayList<LogEntry>();
    }

     public void readFile(String filename) {
        FileResource file = new FileResource(filename);
        for(String line: file.lines()){
            this.records.add(WebLogParser.parseEntry(line));
        }
     }

     public int countUniqueIPs(){
        ArrayList<String> uniqIPs = new ArrayList <String>();
        for(LogEntry le: records){
            String ip = le.getIpAddress();
            if(!uniqIPs.contains(ip)){
                uniqIPs.add(ip);
            }
        }return uniqIPs.size();
     }

     public void printAllHigherThanNum(int num){
        ArrayList<Integer> codes = new ArrayList<Integer>();
        for(LogEntry le: records) {
            int statusCode = le.getStatusCode();
            if (statusCode > num && !codes.contains(statusCode)) {
                codes.add(statusCode);
            }
        }System.out.println(codes + " " + codes.size());
     }

     public ArrayList<String> uniqueIPVisitsOnDay(String date){
        ArrayList<String> IPs = new ArrayList <String>();
        for(LogEntry le: records){
            if(le.getAccessTime().toString().substring(4,10).equals(date) && !IPs.contains(le.getIpAddress())){
                IPs.add(le.getIpAddress());
            }
        }
         System.out.println(IPs.size());
         return IPs;
     }

     public int countUniqueIPsInRange(int low, int high){
        int numOfIPs = 0;
        ArrayList<String> IPs = new ArrayList <String>();
        for(LogEntry le: records){
            if(le.getStatusCode()>=low && le.getStatusCode()<=high && !IPs.contains(le.getIpAddress())){
                IPs.add(le.getIpAddress());
                numOfIPs++;
            }
        }return numOfIPs;
     }

     public HashMap <String,Integer> countVisitsPerIP(){
        HashMap<String,Integer> counts = new HashMap <String,Integer>();
        for(LogEntry le: records){
            String IP = le.getIpAddress();
            if(!counts.containsKey(IP)){
                counts.put(IP, 1);
            }else{
                counts.put(IP, counts.get(IP)+1);
            }
        }return counts;
     }

     public int mostNumberVisitsByIP(HashMap<String,Integer> counts){
        int highest = 0;
         for (Map.Entry<String, Integer> entry : counts.entrySet()){
             if(entry.getValue()>=highest){
                 highest = entry.getValue();
             }
         }return highest;
     }

     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> counts){
         ArrayList<String> IPs = new ArrayList<String>();
         int highestVisitNum = mostNumberVisitsByIP(counts);
         for (Map.Entry<String, Integer> entry : counts.entrySet()){
             if(entry.getValue()==highestVisitNum){
                 IPs.add(entry.getKey());
             }
         }return IPs;
     }

     public HashMap<String, ArrayList<String>> iPsForDays(){
        HashMap<String, ArrayList<String>> visitByDays = new HashMap <String, ArrayList<String>>();
        for(LogEntry le: records){
            String date = le.getAccessTime().toString().substring(4,10);
            visitByDays.put(date,uniqueIPVisitsOnDay(date));
        }return visitByDays;
     }

     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> counts){
         int nums = 0;
         String highestDay = "";
         for (Map.Entry<String, ArrayList<String>> entry : counts.entrySet()){
             if(entry.getValue().size() >= nums){
                 highestDay = entry.getKey();
                 nums = entry.getValue().size();
             }
         }return highestDay;
     }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> counts, String date){
        HashMap <String,Integer> visits = new HashMap <String,Integer>();

        for(LogEntry le: records){
            String IP = le.getIpAddress();
            if(le.getAccessTime().toString().substring(4,10).equals(date)){
                if(!visits.containsKey(IP)){
                    visits.put(IP, 1);
                }else{ visits.put(IP, visits.get(IP)+1); }
            }
        }return iPsMostVisits(visits);
    }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     
}
