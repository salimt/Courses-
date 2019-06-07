package basicgraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.GraphLoader;

/**
 * @author UCSD MOOC Development Team
 * Grader for Module 2, Part 1.
 */
public class DegreeGrader {
    private String feedback;  // Feedback from the grader

    private int correct;      // Tests correctly passed

    private static final int TESTS = 12;  // Number of tests

    
    /**
     * Turn a list into a readable and printable string
     * @param lst  The list to process
     * @return  The list items formatted as a printable string
     */
    public static String printList(List<Integer> lst) {
        String res = "";
        for (int i : lst) {
            res += i + " ";
        }
        // Some lists might be empty, so we can't run substring
        if (res.length() > 0)
            return res.substring(0, res.length() - 1); // last character is ' '
        else
            return res;
    }

    /**
     * Format readable feedback
     * @param score  The score received
     * @param feedback  The feedback message
     * @return  A string where the feedback are score a formatted nicely
     */
    public static String printOutput(double score, String feedback) {
        return "Score: " + score + "\n Feedback: " + feedback;
    }

    /**
     * Format test number and description
     * @param num  The test number
     * @param test The test description
     * @return A String with the test number and description neatly formatted.
     */
    public static String appendFeedback(int num, String test) {
        return "\n** Test #" + num + ": " + test + "...";
    }

    /** Run the grader
     * 
     * @param args Doesn't use command line parameters
     */
    public static void main(String[] args) {
        DegreeGrader grader = new DegreeGrader();
        grader.run();
    }

    /** Run a test case on an adjacency list and adjacency matrix.
     * @param i The graph number
     * @param desc A description of the graph
     * @param start The node to start from
     * @param corr A list containing the correct answer
     */
    public void runTest(int i, String desc) {
        GraphAdjList lst = new GraphAdjList();
        GraphAdjMatrix mat = new GraphAdjMatrix();

        String file = "data/graders/mod1/graph" + i + ".txt";
        List<Integer> corr = readCorrect(file + ".degrees");
        
        feedback += "\n\nGRAPH: " + desc;
        feedback += appendFeedback(i * 2 - 1, "Testing adjacency list"); 

        // Load the graph, get the user's output, and compare with right answer
        GraphLoader.loadGraph(file, lst);
        List<Integer> result = lst.degreeSequence();
        judge(result, corr);
 
        feedback += appendFeedback(i * 2, "Testing adjacency matrix");
        GraphLoader.loadGraph(file, mat);
        result = mat.degreeSequence();
        judge(result, corr);

    }

    /** Run a road map/airplane route test case.
     * @param i The graph number
     * @param file The file to read the correct answer from
     * @param desc A description of the graph
     * @param start The node to start from
     * @param corr A list containing the correct answer
     * @param type The type of graph to use
     */
    public void runSpecialTest(int i, String file, String desc, String type) {
        GraphAdjList lst = new GraphAdjList();
        GraphAdjMatrix mat = new GraphAdjMatrix();

        file = "data/graders/mod1/" + file;
        List<Integer> corr = readCorrect(file + ".degrees");
        
        feedback += "\n\n" + desc;
        feedback += appendFeedback(i * 2 - 1, "Testing adjacency list");

        // Different method calls for different graph types
        if (type.equals("road")) {
            GraphLoader.loadRoadMap(file, lst);
            GraphLoader.loadRoadMap(file, mat);
        } else if (type.equals("air")) {
            GraphLoader.loadRoutes(file, lst);
            GraphLoader.loadRoutes(file, mat);
        }
        List<Integer> result = lst.degreeSequence();
        judge(result, corr);

        feedback += appendFeedback(i * 2, "Testing adjacency matrix");
        result = mat.degreeSequence();
        judge(result, corr);

    }

    /** Compare the user's result with the right answer.
     * @param result The list with the user's result
     * @param corr The list with the correct answer
     */
    public void judge(List<Integer> result, List<Integer> corr) {
        // Correct answer if both lists contain the same elements
    	if(result==null) {
    		feedback += "FAILED. Result is NULL";
    	}
    	else if (!printList(result).equals(printList(corr))) {
            feedback += "FAILED. Expected " + printList(corr) + ", got " + printList(result) + ". ";
        } else {
            feedback += "PASSED.";
            correct++;
        }
    }

    /** Read a correct answer from a file.
     * @param file The file to read from
     * @return A list containing the correct answer
     */
    public List<Integer> readCorrect(String file) {
        List<Integer> ret = new ArrayList<Integer>();
        try {
            Scanner s = new Scanner(new File(file));
            while(s.hasNextInt()) { 
                ret.add(s.nextInt());
            }
        } catch (Exception e) {
            feedback += "\nCould not open answer file! Please submit a bug report.";
        }
        return ret;
    }

    /** Run the grader. */
    public void run() {
        feedback = "";

        correct = 0;

        try {
            runTest(1, "Straight line (0->1->2->3->...)");

            runTest(2, "Undirected straight line (0<->1<->2<->3<->...)");

            runTest(3, "Star graph - 0 is connected in both directions to all nodes except itself (starting at 0)");

            runTest(4, "Star graph - Each 'arm' consists of two undirected edges leading away from 0 (starting at 0)");

            runSpecialTest(5, "ucsd.map", "UCSD MAP: Intersections around UCSD", "road");
            
            runSpecialTest(6, "routesUA.dat", "AIRLINE MAP: Routes of airplanes around the world", "air");

            if (correct == TESTS)
                feedback = "All tests passed. Great job!" + feedback;
            else
                feedback = "Some tests failed. Check your code for errors, then try again:" + feedback;

        } catch (Exception e) {
            feedback += "\nError during runtime: " + e;
            e.printStackTrace();
        }
            
        System.out.println(printOutput((double)correct / TESTS, feedback));
    }
}
