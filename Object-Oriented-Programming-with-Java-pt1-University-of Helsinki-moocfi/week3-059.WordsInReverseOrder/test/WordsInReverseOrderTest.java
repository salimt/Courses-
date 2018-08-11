import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("59")
public class WordsInReverseOrderTest {
    @Rule
    public MockStdio io = new MockStdio();

    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ");
    }

    @Test
    public void test1() {
        String[] sanat = {"Java", "Ruby"};
        String vastaus = "Ruby Java";

        io.setSysIn(toS(sanat) + "\n");
        WordsInReverseOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("With input " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }
    
    @Test
    public void test2() {
        String[] sanat = {"Coffee", "Milk", "Tea", "Powerking"};
        String vastaus = "Powerking Tea Milk Coffee";

        io.setSysIn(toS(sanat) + "\n");
        WordsInReverseOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("With input " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }
    
    @Test
    public void test3() {
        String[] sanat = {"while", "if", "variable", "print", "method", "assignment", "object", "class", "list", "array", "stack", "tree"};
        String vastaus = "tree stack array list class object assignment method print variable if while";

        io.setSysIn(toS(sanat) + "\n");
        WordsInReverseOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("With input " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }    

    private static String toS(String[] sanat) {
        String s = "";

        for (String sana : sanat) {
            s += sana + "\n";
        }

        return s;
    }

    private static String toS2(String[] sanat) {
        String s = "";

        for (String sana : sanat) {
            s += sana + " ";
        }

        return s + "\"\"";
    }
}
