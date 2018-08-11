
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("53")
public class FirstPartTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void noExceptions() throws Exception {
        io.setSysIn("pekka\n3\n");
        try {
            FirstPart.main(new String[0]);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"Pekka 2\" " + v, e);
        }
    }    
    
    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ").replaceAll(" ", "");
    }

    @Test
    public void test1() {
        io.setSysIn("Java\n3\n");
        FirstPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Java 3 your program should print \"Result: Jav\"", out.contains("Result:Jav"));
        assertFalse("With input Java 3 your program should print \"Result: Jav\"", out.contains("Result:Java"));
    }
 
    @Test
    public void test2() {
        io.setSysIn("Programming\n7\n");
        FirstPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Programming 7 your program should print \"Result: Program\"", out.contains("Result:Program"));
        assertFalse("With input Programming 7 your program should print \"Result: Program\"", out.contains("Result:Programm"));
    }    
    
    
    @Test
    public void test3() {
        io.setSysIn("Web-designer\n10\n");
        FirstPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Web-designer 10 your program should print \"Result: Web-design\"", out.contains("Result:Web-design"));
        assertFalse("With input Web-designer 10 your program should print \"Result: Web-design\"", out.contains("Result:Web-designe"));
    }      
}

