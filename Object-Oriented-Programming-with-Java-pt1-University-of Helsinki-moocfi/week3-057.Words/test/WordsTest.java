import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("57")
public class WordsTest {
    @Rule
    public MockStdio io = new MockStdio();
    
    @Test
    public void test1() {
        String[] sanat = {"Java"};
        io.setSysIn(toS(sanat) + "\n");
        Words.main(new String[0]);
        String out = io.getSysOut();
        for (String sana : sanat) {
            assertTrue("With input "+toS2(sanat)+" your program should print "+sana,out.contains(sana));
        }    
    }
    
    @Test
    public void test2() {
        String[] sanat = {"programming", "mathematics"};
        io.setSysIn(toS(sanat) + "\n");
        Words.main(new String[0]);
        String out = io.getSysOut();
        for (String sana : sanat) {
            assertTrue("With input "+toS2(sanat)+" your program should print "+sana,out.contains(sana));
        }    
    }    
    
    @Test
    public void test3() {
        String[] sanat = {"Kent Beck", "Alan Turing", "Arto Vihavainen", "Ken Thompson", "Bill Gates" };
        io.setSysIn(toS(sanat) + "\n");
        Words.main(new String[0]);
        String out = io.getSysOut();
        for (String sana : sanat) {
            assertTrue("With input "+toS2(sanat)+" your program should print "+sana,out.contains(sana));
        }    
    }  
    
    @Test
    public void test4() {
        String[] sanat = {"while", "if", "method", "print", "variable", "object", "class", "list", 
            "bean", "array", "forest", "tree"};
        io.setSysIn(toS(sanat) + "\n");
        Words.main(new String[0]);
        String out = io.getSysOut();
        for (String sana : sanat) {
            assertTrue("With input "+toS2(sanat)+" your program should print "+sana,out.contains(sana));
        }    
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
        
        return s+"\"\"";
    }    
}
