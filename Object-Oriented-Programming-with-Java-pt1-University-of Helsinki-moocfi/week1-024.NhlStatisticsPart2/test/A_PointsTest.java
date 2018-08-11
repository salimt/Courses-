
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.List;
import nhlstats.NHLStatistics;
import org.junit.*;
import static org.junit.Assert.*;

@Points("24")
public class A_PointsTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testMode();
        String input = "points\nquit\n";
        io.setSysIn(input);       
        NhlStatisticsPart2.main(null);
        input = input.replaceAll("\n", " ");
        String viesti =  "With commands \""+input+"\" the top ten players sorted by points should be printed.";
        
        assertCalled(0, "sortByPoints", viesti);
        assertCalled(1, "top", "");               
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
        }
    }

}
