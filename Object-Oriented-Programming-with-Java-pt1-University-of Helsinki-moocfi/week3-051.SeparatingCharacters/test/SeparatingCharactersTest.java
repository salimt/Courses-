
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("51")
public class SeparatingCharactersTest {

    @Rule
    public MockStdio io = new MockStdio();

    public void testaa(String sana, String odotettu, String tulos) {
        assertTrue("With input \"" + sana + "\" you should print line \""
                + odotettu + "\"", tulos.toLowerCase().replace(" ", "").contains(odotettu.toLowerCase().replace(" ", "")));
    }

    @Test
    public void noExceptions() throws Exception {
        io.setSysIn("pekka\n");
        try {
            SeparatingCharacters.main(new String[0]);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"Pekka\" " + v, e);
        }
    }

    @Test
    public void test1() {
        String sana = "Pekka";

        io.setSysIn(sana);
        SeparatingCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1. character: P";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: e";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: k";
        testaa(sana, odotettu, tulos);
        odotettu = "4. character: k";
        testaa(sana, odotettu, tulos);
        odotettu = "5. character: a";
        testaa(sana, odotettu, tulos);        
    }

    @Test
    public void test2() {
        String sana = "Liisa";

        io.setSysIn(sana);
        SeparatingCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1. character: L";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: i";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: i";
        testaa(sana, odotettu, tulos);
        odotettu = "4. character: s";
        testaa(sana, odotettu, tulos);
        odotettu = "5. character: a";
        testaa(sana, odotettu, tulos);
    }

    @Test
    public void test3() {
        String sana = "Esko";

        io.setSysIn(sana);
        SeparatingCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1.character: E";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: s";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: k";
        testaa(sana, odotettu, tulos);
        odotettu = "4. character: o";
        testaa(sana, odotettu, tulos);      
    }    
    
    @Test
    public void testi4() {
        String sana = "Jo";

        io.setSysIn(sana);
        SeparatingCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1.character: J";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: o";
    }
}
