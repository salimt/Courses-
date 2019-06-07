package basicgraph;

import util.GraphLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UCSD MOOC Development Team
 * Grader for Module 2, Part 2
 */
public class GraphGrader {
    private String feedback;

    private int correct;

    private static final int TESTS = 16;

    /**
     * Turn a list into a readable and printable string
     * @param lst  The list to process
     * @return  The list items formatted as a printable string
     */
    public static String printList(List<Integer> lst) {
        String res = "";
        for (int i : lst) {
            res += i + "-";
        }
        // Last character will be a '-'
        return res.substring(0, res.length() - 1);
    }

    /**
     * Format readable feedback
     * @param score  The score received
     * @param feedback  The feedback message
     * @return  A string where the feedback are score a formatted nicely
     */
    public static String printOutput(double score, String feedback) {
        return "Score: " + score + "\nFeedback: " + feedback;
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
        GraphGrader grader = new GraphGrader();
        grader.run();
    }

    /** Run a test case on an adjacency list and adjacency matrix.
     * @param i The graph number
     * @param desc A description of the graph
     * @param start The node to start from
     * @param corr A list containing the correct answer
     */
    public void runTest(int i, String desc, int start, List<Integer> corr) {
        GraphAdjList lst = new GraphAdjList();
        GraphAdjMatrix mat = new GraphAdjMatrix();
        
        feedback += "\n\nGRAPH: " + desc;
        feedback += appendFeedback(i * 2 - 1, "Testing adjacency list"); 

        // Load the graph, get the user's answer, and compare with right answer
        GraphLoader.loadGraph("data/graders/mod1/graph" + i + ".txt", lst);
        List<Integer> result = lst.getDistance2(start);
        judge(result, corr);
 
        feedback += appendFeedback(i * 2, "Testing adjacency matrix");
        GraphLoader.loadGraph("data/graders/mod1/graph" + i + ".txt", mat);
        result = mat.getDistance2(start);
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
    public void runSpecialTest(int i, String file, String desc, int start, List<Integer> corr, String type) {
        GraphAdjList lst = new GraphAdjList();
        GraphAdjMatrix mat = new GraphAdjMatrix();

        String prefix = "data/graders/mod1/";

        feedback += "\n\n" + desc;
        feedback += appendFeedback(i * 2 - 1, "Testing adjacency list");

        // Different method calls for different graph types
        if (type.equals("road")) {
            GraphLoader.loadRoadMap(prefix + file, lst);
            GraphLoader.loadRoadMap(prefix + file, mat);
        } else if (type.equals("air")) {
            GraphLoader.loadRoutes(prefix + file, lst);
            GraphLoader.loadRoutes(prefix + file, mat);
        }

        List<Integer> result = lst.getDistance2(start);
        judge(result, corr);

        feedback += appendFeedback(i * 2, "Testing adjacency matrix");
        result = mat.getDistance2(start);
        judge(result, corr);
    }

    /** Compare the user's result with the right answer.
     * @param result The list with the user's result
     * @param corr The list with the correct answer
     */
    public void judge(List<Integer> result, List<Integer> corr) {
        // Correct answer if both lists contain the same elements
    	if (result == null) {
    		feedback += "FAILED. Result returned was NULL. ";
    	}
    	else if (result.size() != corr.size() || !result.containsAll(corr)) {
            feedback += "FAILED. Expected " + printList(corr) + ", got " + printList(result) + ". ";
            if (result.size() > corr.size()) {
                feedback += "Make sure you aren't including vertices of distance 1. ";
            }
            if (result.size() < corr.size()) { 
                feedback += "Make sure you're exploring all possible paths. ";
            }
        } else {
            feedback += "PASSED.";
            correct++;
        }
    }

    /** Read a correct answer from a file.
     * @param file The file to read from
     * @return A list containing the correct answer
     */
    public ArrayList<Integer> readCorrect(String file) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/graders/mod1/" + file));
            String next;
            while ((next = br.readLine()) != null) {
                ret.add(Integer.parseInt(next));
            }
        } catch (Exception e) {
            // shouldn't happen
            feedback += "\nCould not open answer file! Please submit a bug report.";
        }
        return ret;
    }

    /** Run the grader */
    public void run() {
        feedback = "";
        correct = 0;
        ArrayList<Integer> correctAns;

        try {
            correctAns = new ArrayList<Integer>();
            correctAns.add(7);
            runTest(1, "Straight line (0->1->2->3->...)", 5, correctAns);

            correctAns = new ArrayList<Integer>();
            correctAns.add(4);
            correctAns.add(6);
            correctAns.add(6);
            correctAns.add(8);
            runTest(2, "Undirected straight line (0<->1<->2<->3<->...)", 6, correctAns);

            correctAns = new ArrayList<Integer>();
            for (int i = 0; i < 9; i++) {
                correctAns.add(0);
            }
            runTest(3, "Star graph - 0 is connected in both directions to all nodes except itself (starting at 0)", 0, correctAns);

            correctAns = new ArrayList<Integer>();
            for (int i = 1; i < 10; i++)
                correctAns.add(i);
            runTest(4, "Star graph (starting at 5)", 5, correctAns);
            
            correctAns = new ArrayList<Integer>();
            for (int i = 6; i < 11; i++)
                correctAns.add(i);
            runTest(5, "Star graph - Each 'arm' consists of two undirected edges leading away from 0 (starting at 0)", 0, correctAns);

            correctAns = new ArrayList<Integer>();
            runTest(6, "Same graph as before (starting at 5)", 5, correctAns);

            correctAns = readCorrect("ucsd.map.twoaway");
            runSpecialTest(7, "ucsd.map", "UCSD MAP: Intersections around UCSD", 3, correctAns, "road");

            correctAns = readCorrect("routesUA.dat.twoaway");
            runSpecialTest(8, "routesUA.dat", "AIRLINE MAP: Airplane routes around the world", 6, correctAns, "air");

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
