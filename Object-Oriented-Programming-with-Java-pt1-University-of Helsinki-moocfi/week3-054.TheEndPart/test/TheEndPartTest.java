import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("54")
public class TheEndPartTest {
    @Rule
    public MockStdio io = new MockStdio();

    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ");
    }
    
    @Test
    public void noExceptions() throws Exception {
        io.setSysIn("pekka\n3\n");
        try {
            TheEndPart.main(new String[0]);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"Pekka 3\" " + v, e);
        }
    }   

    @Test
    public void test1() {
        io.setSysIn("Java\n3\n");
        TheEndPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Java 3 your program should print \"Result: ava\"", out.contains("Result:ava"));
        //assertFalse("With input Java 3 your program should print \"Result: ava\"", out.contains("Result:ava"));
    }
 
    @Test
    public void test2() {
        io.setSysIn("Programming\n4\n");
        TheEndPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Programming 4 your program should print \"Result: ming\"", out.contains("Result:ming"));
        //assertFalse("With input Programming 4 your program should print \"Result: ming\"", out.contains("Result:ming"));
    }    
    
    
    @Test
    public void test3() {
        io.setSysIn("Web-designer\n6\n");
        TheEndPart.main(new String[0]);
        String out = io.getSysOut().replaceAll(" ", "");

        assertTrue("your program should print \"Result: \"", out.contains("Result:"));

        assertTrue("With input Web-designer 6 your program should print \"Result: signer\"", out.contains("Result:signer"));
        //assertFalse("With input Web-designer 6 your program should print \"Result: signer\"", out.contains("Result:signer"));
    }      
}

