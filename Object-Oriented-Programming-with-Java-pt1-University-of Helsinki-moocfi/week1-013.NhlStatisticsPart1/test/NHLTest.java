
import nhlstats.NHLStatistics;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

@Points("13")
public class NHLTest {

    @Test
    public void nhlOsa1() throws Exception {
        testMode();
        Main.main(null);

        assertCalled(0, "sortByGoals", "start by sorting players based on goals\n this can be done with command NHLStatistics.sortByGoals();");
        assertCalled(1, "top", "after sorting players by goals, print players with command top");
        assertCalled(2, "sortByPenalties", "after printing players sorted by goals, sort the players by penalties ");
        assertCalled(3, "top", "print players with command top");
        assertCalled(4, "search", "after printing players sorted by penalties, search statistics of Sidney Crosby");
        assertCalled(5, "team PHI", "print statistics of team PHI");
        assertCalled(6, "sortByPoints", "after printing statistics of PHI sort players by points");
        assertCalled(7, "team ANA", "at the end print statistics of ANA");
    }

    private void assertCalled(int n, String method, String msg) {
        List<String> mt = getMethodCalls();
        assertTrue(msg, mt.size() > n);
        assertTrue(msg, mt.get(n).contains(method));
    }

    private List<String> getMethodCalls() {
        try {
            Method m = NHLStatistics.class.getDeclaredMethod("calledMethods");
            m.setAccessible(true);
            return (List<String>) m.invoke(null);
        } catch (Throwable e) {
        }
        return null;
    }

    private void testMode() {
        try {
            Method m = NHLStatistics.class.getDeclaredMethod("testMode");
            m.setAccessible(true);
            m.invoke(null);
        } catch (Throwable e) {
            throw new Error("Somethig went wrong!", e);
        }
    }
}
