package roadgraph;

import geography.GeographicPoint;
import util.GraphLoader;

import java.util.List;

/**
 * @author UCSD MOOC Development Team
 * Grader for Module 3.
 */
public class SearchGrader implements Runnable {
    public String feedback;

    public int correct;

    private static final int TESTS = 12;

    /** Format readable feedback */
    public static String printOutput(double score, String feedback) {
        return "Score: " + score + "\nFeedback: " + feedback;
    }

    /** Format test number and description */
    public static String appendFeedback(int num, String test) {
        return "\n** Test #" + num + ": " + test + "...";
    }

    public static void main(String[] args) {
        SearchGrader grader = new SearchGrader();

        // Infinite loop detection
        Thread thread = new Thread(grader);
        thread.start();
        
        // Allow it to run for 10 seconds
        long endTime = System.currentTimeMillis() + 10000;
        boolean infinite = false;
        while(thread.isAlive()) {
            if (System.currentTimeMillis() > endTime) {
                // Stop the thread if it takes too long
                thread.stop();
                infinite = true;
                break;
            }
        }
        if (infinite) {
            System.out.println(printOutput((double)grader.correct / TESTS, grader.feedback + "\nYour program entered an infinite loop."));
        }
    }

    /** Run a test case on an adjacency list and adjacency matrix.
     * @param i The graph number
     * @param file The file to read from
     * @param desc A description of the graph
     * @param start The point to start from
     * @param end The point to end at
     */
    public void runTest(int i, String file, String desc, GeographicPoint start, GeographicPoint end) {
        MapGraph graph = new MapGraph();

        feedback += "\n\n" + desc;

        GraphLoader.loadRoadMap("data/graders/mod2/" + file, graph);
        CorrectAnswer corr = new CorrectAnswer("data/graders/mod2/" + file + ".answer", true);

        judge(i, graph, corr, start, end);
    }

    /** Compare the user's result with the right answer.
     * @param i The graph number
     * @param result The user's graph
     * @param corr The correct answer
     * @param start The point to start from
     * @param end The point to end at
     */
    public void judge(int i, MapGraph result, CorrectAnswer corr, GeographicPoint start, GeographicPoint end) {
        // Correct if same number of vertices
        feedback += appendFeedback(i * 3 - 2, "Testing vertex count");
        if (result.getNumVertices() != corr.vertices) {
            feedback += "FAILED. Expected " + corr.vertices + "; got " + result.getNumVertices() + ".";
        } else {
            feedback += "PASSED.";
            correct++;
        }

        // Correct if same number of edges
        feedback += appendFeedback(i * 3 - 1, "Testing edge count");
        if (result.getNumEdges() != corr.edges) {
            feedback += "FAILED. Expected " + corr.edges + "; got " + result.getNumEdges() + ".";
        } else {
            feedback += "PASSED.";
            correct++;
        }

        // Correct if paths are same size and have same elements
        feedback += appendFeedback(i * 3, "Testing BFS");
        List<GeographicPoint> bfs = result.bfs(start, end);
        if (bfs == null) {
            if (corr.path == null) {
                feedback += "PASSED.";
                correct++;
            } else {
                feedback += "FAILED. Your implementation returned null; expected \n" + printBFSList(corr.path) + ".";
            }
        } else if (corr.path == null) {
            feedback += "FAILED. Your implementation returned \n" + printBFSList(bfs) + "; expected null.";
        } else if (!printBFSList(corr.path).equals(printBFSList(bfs))) {
            feedback += "FAILED. Expected: \n" + printBFSList(corr.path) + "Got: \n" + printBFSList(bfs);
            if (bfs.size() != corr.path.size()) {
                feedback += "Your result has size " + bfs.size() + "; expected " + corr.path.size() + ".";
            } else {
                feedback += "Correct size, but incorrect path.";
            }
        } else {
            feedback += "PASSED.";
            correct++;
        }
    }

    /** Print a BFS path in readable form */
    public String printBFSList(List<GeographicPoint> bfs) {
        String ret = "";
        for (GeographicPoint point : bfs) {
            ret += point + "\n";
        }
        return ret;
    }

    /** Run the grader */
    public void run() {
        feedback = "";

        correct = 0;

        try {
            runTest(1, "map1.txt", "Straight line (0->1->2->3->...)", new GeographicPoint(0, 0), new GeographicPoint(6, 6));

            runTest(2, "map2.txt", "Same as above (searching from 6 to 0)", new GeographicPoint(6, 6), new GeographicPoint(0, 0));

            runTest(3, "map3.txt", "Square graph - Each edge has 2 nodes", new GeographicPoint(0, 0), new GeographicPoint(1, 2));

            runTest(4, "ucsd.map", "UCSD MAP: Intersections around UCSD", new GeographicPoint(32.8756538, -117.2435715), new GeographicPoint(32.8742087, -117.2381344));

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
