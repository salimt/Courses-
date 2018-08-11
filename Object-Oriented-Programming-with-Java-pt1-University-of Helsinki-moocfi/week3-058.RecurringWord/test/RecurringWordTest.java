
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("58")
public class RecurringWordTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        String[] sanat = {"Java", "Java"};
        testaa(sanat);
    }

    @Test
    public void test2() {
        String[] sanat = {"programming", "mathematics", "programming"};
        testaa(sanat);
    }

    @Test
    public void test3() {
        String[] sanat = {"Kent Beck", "Alan Turing", "Arto Vihavainen", "Ken Thompson", "Bill Gates", "Kent Beck"};
        testaa(sanat);
    }

    @Test
    public void test4() {      
        String[] sanat = {"while", "if", "tree","method", "print", "variable", "object", "class", "list",
            "bean", "array", "forest", "tree"};
        testaa(sanat);
    }

    private void testaa(String[] sanat) {
        String toisto = sanat[sanat.length - 1];

        io.setSysIn(toS(sanat));
        try {
            RecurringWord.main(new String[0]);
        } catch (NoSuchElementException e) {
            fail("with input " + toS2(sanat) + " your program should write \"You gave twice the word " + toisto + "\"");
        }

        String out = io.getSysOut();
        assertTrue("with input " + toS2(sanat) + " pitäisi tulostaa \"You gave twice the word " + toisto + "\"", out.contains(toisto));
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
            s += sana + ", ";
        }

        return s.substring(0, s.length() - 2);
    }
}
