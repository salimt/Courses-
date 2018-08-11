
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("56")
public class ReversingTextTest {
    
    @Test
    public void noExceptions() throws Exception {
        String kaannettava = "java";
        try {
            ReversingText.reverse(kaannettava);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"java\" " + v, e);
        }
    }
    
    @Test
    public void noExceptions2() throws Exception {
        String kaannettava = "programming";
        try {
            ReversingText.reverse(kaannettava);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"programming\" " + v, e);
        }
    }    
    
    @Test
    public void test1() {
        String kaannettava = "java";
        String odotettu = "avaj";
        String vastaus = ReversingText.reverse(kaannettava);

        assertEquals("check what method reverse returns with parameter \"" + kaannettava + "\" ", odotettu, vastaus);
    }

    @Test
    public void test2() {
        String kaannettava = "programming";
        String odotettu = "gnimmargorp";
        String vastaus = ReversingText.reverse(kaannettava);

        assertEquals("check what method reverse returns with parameter \"" + kaannettava + "\" ", odotettu, vastaus);
    }

    @Test
    public void test3() {
        String kaannettava = "web-designer";
        String odotettu = "rengised-bew";
        String vastaus = ReversingText.reverse(kaannettava);

        assertEquals("check what method reverse returns with parameter \"" + kaannettava + "\" ", odotettu, vastaus);
    }

}
