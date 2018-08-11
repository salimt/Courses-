
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("50")
public class FirstCharactersTest {

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
            FirstCharacters.main(new String[0]);
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
        FirstCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1. character: P";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: e";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: k";
        testaa(sana, odotettu, tulos);

        odotettu = "4. character: k";
        assertFalse("With input \"" + sana + "\" you should not print line \""
                + odotettu + "\"", tulos.toLowerCase().replace(" ", "").contains(odotettu.toLowerCase().replace(" ", "")));
    }

    @Test
    public void test2() {
        String sana = "Liisa";

        io.setSysIn(sana);
        FirstCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1. character: L";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: i";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: i";
        testaa(sana, odotettu, tulos);
    }

    @Test
    public void test3() {
        String sana = "Esko";

        io.setSysIn(sana);
        FirstCharacters.main(new String[0]);
        String tulos = io.getSysOut();

        String odotettu = "1.character: E";
        testaa(sana, odotettu, tulos);
        odotettu = "2. character: s";
        testaa(sana, odotettu, tulos);
        odotettu = "3. character: k";
        testaa(sana, odotettu, tulos);

    }

    @Test
    public void testi4() {
        String sana = "Me";

        io.setSysIn(sana);

        try {
            FirstCharacters.main(new String[0]);
        } catch (Exception e) {
            fail("Remember that name can be less than 3 characters long!");
        }

        String tulos = io.getSysOut();

        String odottamaton = "haracter";
        assertFalse("With input \"" + sana + "\" you should not print anything", tulos.contains(odottamaton));
    }
}
