package roadgraph;

import geography.GeographicPoint;
import util.GraphLoader;

import java.util.List;

/**
 * @author UCSD MOOC Development Team
 * Grader for Module 4, Part 2.
 */
public class AStarGrader implements Runnable {
    public String feedback;

    public int correct;

    private static final int TESTS = 4;

    /** Format readable feedback */
    public static String printOutput(double score, String feedback) {
        return "Score: " + score + "\nFeedback: " + feedback;
    }

    /** Format test number and description */
    public static String appendFeedback(int num, String test) {
        return "\n** Test #" + num + ": " + test + "...";
    }

    public static void main(String[] args) {
        AStarGrader grader = new AStarGrader();

        // Infinite loop detection
        Thread thread = new Thread(grader);
        thread.start();
        long endTime = System.currentTimeMillis() + 10000;
        boolean infinite = false;
        while(thread.isAlive()) {
            // Stop after 10 seconds
            if (System.currentTimeMillis() > endTime) {
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

        GraphLoader.loadRoadMap("data/graders/mod3/" + file, graph);
        CorrectAnswer corr = new CorrectAnswer("data/graders/mod3/" + file + ".answer", false);

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
    	// Correct if paths are same length and have the same elements
        feedback += appendFeedback(i, "Running A* from (" + start.getX() + ", " + start.getY() + ") to (" + end.getX() + ", " + end.getY() + ")");
        List<GeographicPoint> path = result.aStarSearch(start, end);
        if (path == null) {
            if (corr.path == null) {
                feedback += "PASSED.";
                correct++;
            } else {
                feedback += "FAILED. Your implementation returned null; expected \n" + printPath(corr.path) + ".";
            }
        } else if (path.size() != corr.path.size() || !corr.path.containsAll(path)) {
            feedback += "FAILED. Expected: \n" + printPath(corr.path) + "Got: \n" + printPath(path);
            if (path.size() != corr.path.size()) {
                feedback += "Your result has size " + path.size() + "; expected " + corr.path.size() + ".";
            } else {
                feedback += "Correct size, but incorrect path.";
            }
        } else {
            feedback += "PASSED.";
            correct++;
        }
    }

    /** Print a search path in readable form */
    public String printPath(List<GeographicPoint> path) {
        String ret = "";
        for (GeographicPoint point : path) {
            ret += point + "\n";
        }
        return ret;
    }

    /** Run the grader */
    public void run() {
        feedback = "";

        correct = 0;

        try {
            runTest(1, "map1.txt", "MAP: Straight line (-3 <- -2 <- -1 <- 0 -> 1 -> 2-> 3 ->...)", new GeographicPoint(0, 0), new GeographicPoint(6, 6));

            runTest(2, "map2.txt", "MAP: Example map from the writeup", new GeographicPoint(7, 3), new GeographicPoint(4, -1));

            runTest(3, "map3.txt", "MAP: Right triangle (with a little detour)", new GeographicPoint(0, 0), new GeographicPoint(0, 4));

            runTest(4, "ucsd.map", "UCSD MAP: Intersections around UCSD", new GeographicPoint(32.8709815, -117.2434254), new GeographicPoint(32.8742087, -117.2381344));

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
