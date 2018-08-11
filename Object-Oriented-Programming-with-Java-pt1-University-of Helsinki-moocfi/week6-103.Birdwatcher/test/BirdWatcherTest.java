import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("103.1 103.2 103.3")
public class BirdWatcherTest {

    @Rule
    public MockStdio io = new MockStdio();

    String testaa(String in) {
        
        String s;
        try {
            io.setSysIn(in);
            Main.main(new String[0]);
            s = io.getSysOut();
        } catch (NoSuchElementException e) {
            fail("Error in reqding the input. Try your program with input\n"+in);
            return null;
        } catch (Throwable t) {
            fail("Something went wrong. Try your program with input\n"+in+"\n\nMore info:\n"+t);
            return null;
        }
        return s;

    }

    @Test
    public void test1() {
        testaa("Quit\n");
    }

    @Test
    public void test2() {
        testaa("Statistics\nQuit\n");
    }

    @Test
    public void test3() {
        testaa("Show\nKieppi\nQuit\n");
    }

    @Test
    public void test4() {
        testaa("Observation\nKeppi\nQuit\n");
    }

    @Test
    public void test5() {
        String in = "Add\nXXX\nYYY\nShow\nXXX\nQuit\n";
        String out = testaa(in);
        String viesti = "Try your program with input\n"+in;

        assertTrue("You did not print the bird name with command show. "+viesti,
                   out.contains("XXX"));
        assertTrue("You did not print the bird's latin name with command show.. "+viesti,
                   out.contains("YYY"));
        assertTrue("You did not print the number of observations with the Show-command. "+viesti,
                   out.contains("0"));
    }

    @Test
    public void test6() {
        String in = "Add\nXXX\nYYY\nObservation\nXXX\nObservation\nXXX\nShow\nXXX\nQuit\n";
        String out = testaa(in);
        String viesti = "Try your program with input\n"+in;

        assertTrue("You did not print the bird name with command show. "+viesti,
                   out.contains("XXX"));
        assertTrue("You did not print the bird's latin name with command show.. "+viesti,
                   out.contains("YYY"));
        assertTrue("You did not print the number of observations with the Show-command. "+viesti,
                   out.contains("2"));

    }

    public void lintu(String viesti, String out, String a, String b, int lkm) {
        String[] lines = out.split("\n");
        boolean ok = false;
        for (String line : lines) {
            if (line.contains(a) && line.contains(b) && line.contains(""+lkm)) {
                ok = true; 
            }
        }
        assertTrue("You did not print amount of observatios of the bird "+a+" ("+b+") correctly. "+viesti,
                   ok);
    }

    @Test
    public void test7() {
        String in = "Add\nXXX\nYYY\nObservation\nXXX\n"+
            "Add\nWWW\nZZZ\n"+
            "Observation\nWWW\n"+
            "Show\nXXX\n"+
            "Observation\nXXX\nObservation\nWWW\n"+
            "Show\nWWW\n"+
            "Quit\n";
        String out = testaa(in);
        String viesti = "Try your program with input\n"+in;

        lintu(viesti,out,"XXX","YYY",1);
        lintu(viesti,out,"WWW","ZZZ",2);
    }

    @Test
    public void test8() {
        String in = "Add\nXX\nYY\n"+
            "Add\nWW\nZZ\n"+
            "Observation\nWW\n"+
            "Observation\nXX\nObservation\nWW\n"+
            "Observation\nXX\nObservation\nWW\n"+
            "Statistics\n"+
            "Quit\n";
        String out = testaa(in);
        String viesti = "Try your program with input\n"+in;

        lintu(viesti,out,"XX","YY",2);
        lintu(viesti,out,"WW","ZZ",3);
    }

    @Test
    public void test9() {
        testaa("Olut\nQuit\n");
        testaa("See\nQuit\n");
        testaa("Observazione\nQuit\n");
    }
}
