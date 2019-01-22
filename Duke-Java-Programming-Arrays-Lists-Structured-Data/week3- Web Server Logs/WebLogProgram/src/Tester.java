/**
 * @author: salimt
 */

import java.util.*;

public class Tester {
    public Tester() {}

    public static void main(String[] args) {
        Tester tt = new Tester();
        System.out.println("##### Testing of Log Entry");
        tt.testLogAnalyzer();
        System.out.println("\n##### Testing of Log Analyzer");
        tt.testLogEntry();
        System.out.println("\n##### Testing of Unique IP");
        tt.testUniqueIP();
        System.out.println("\n##### Testing of Status Codes Higher than Given");
        tt.testHigherThanNum();
        System.out.println("\n##### Testing of Number of Unique IPs on the Given Day");
        tt.testUniqueIPVisitsOnDay();
        System.out.println("\n##### Testing of Total number of Unique IPs That Are Given Range");
        tt.testCountUniqueIPsInRange();
        System.out.println("\n##### Testing of Number of Visits IPs made");
        tt.testCountVisitsPerIP();
        System.out.println("\n##### Testing of Highest Visit by Single IP:");
        tt.testMostNumberVisitsByIP();
        System.out.println("\n##### Testing of Highest Num of Visits Made by IPs:");
        tt.testIPsMostVisits();
        System.out.println("\n##### Testing of Visits Categorized by Days:");
        tt.testIPsForDays();
        System.out.println("\n##### Testing of The Most Visited Day:");
        tt.testDayWithMostIPVisits();
        System.out.println("\n##### Testing of The Most Visiting IPs on the given day");
        tt.testIPsWithMostVisitsOnDay();

    }

    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }

    public void testUniqueIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("Total unique IPs: " + la.countUniqueIPs());
    }

    public void testHigherThanNum(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        la.printAllHigherThanNum(400);
        la.countUniqueIPs();
    }

    public void testUniqueIPVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("Total number of unique IPs given day: " + la.uniqueIPVisitsOnDay("Sep 27"));
    }

    public void testCountUniqueIPsInRange(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("Total number of unique IPs given range of status codes: " + la.countUniqueIPsInRange(400,499));
    }

    public void testCountVisitsPerIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String,Integer> visits = la.countVisitsPerIP();
        System.out.println("Number of Visits IPs made: " + visits);
        System.out.println("Number of Unique IPs: " + visits.size());
    }

    public void testMostNumberVisitsByIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("Highest Visit by Single IP: " + la.mostNumberVisitsByIP(la.countVisitsPerIP()));
    }

    public void testIPsMostVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("Highest Num of Visits Made by Various IPs: " + la.iPsMostVisits(la.countVisitsPerIP()));
    }

    public void testIPsForDays(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String,ArrayList<String>> visits = la.iPsForDays();
        System.out.println("Visits categorized by Dates: " + visits);
    }

    public void testDayWithMostIPVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("The most visited day: " + la.dayWithMostIPVisits(la.iPsForDays()));
    }

    public void testIPsWithMostVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("The Most Visiting IPs on the given day: " + la.iPsWithMostVisitsOnDay(la.iPsForDays(),"Sep 29"));
    }
}
