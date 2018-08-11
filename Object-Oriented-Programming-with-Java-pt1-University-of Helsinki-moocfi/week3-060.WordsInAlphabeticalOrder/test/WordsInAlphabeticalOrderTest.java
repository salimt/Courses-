
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("60")
public class WordsInAlphabeticalOrderTest {

    @Rule
    public MockStdio io = new MockStdio();

    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ");
    }

    @Test
    public void test1a() {
        String[] sanat = {"Java", "Ruby"};
        String vastaus = "Java Ruby";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }

    @Test
    public void test1b() {
        String[] sanat = {"Ruby", "Java"};
        String vastaus = "Java Ruby";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }
    
    
    @Test
    public void test2a() {
        String[] sanat = {"Coffee", "Milk", "Tea", "Powerking"};
        String vastaus = "Coffee Milk Powerking Tea";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }
    
    @Test
    public void test2b() {
        String[] sanat = {"Milk", "Tea", "Coffee", "Powerking"};
        String vastaus = "Coffee Milk Powerking Tea";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }
    
    @Test
    public void test2c() {
        String[] sanat = { "Powerking", "Tea",  "Coffee", "Milk"};
        String vastaus = "Coffee Milk Powerking Tea";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }    
    
    @Test
    public void test2d() {
        String[] sanat = { "Tea", "Powerking", "Milk", "Coffee"};
        String vastaus = "Coffee Milk Powerking Tea";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }     
    
    public void test3a() {
        String[] sanat = {"while", "if", "variable", "print", "method", "assignment", "object", "class", "list", "array", "stack", "tree"};
        String vastaus = "array assignment class if list method object  print stack tree variable while";

        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
    }    

    public void test3b() {
        String[] sanat = {"if", "print",  "assignment", "while" , "object", "class", "list", "array", "variable", "stack", "tree","method"};
        String vastaus = "array assignment class if list method object  print stack tree variable while";
        
        io.setSysIn(toS(sanat) + "\n");
        WordsInAlphabeticalOrder.main(new String[0]);
        String out = sanitize(io.getSysOut());

        assertTrue("with input  " + toS2(sanat) + " your program should print " + vastaus, out.contains(vastaus));
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
