
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("55")
public class WordInsideWordTest {

    @Rule
    public MockStdio io = new MockStdio();

    public String sanitize(String s) {
        return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll("\n", " ").replaceAll("  ", " ");
    }

    
    @Test
    public void noExceptions() throws Exception {
        io.setSysIn("pekka\nkk\n");
        try {
            WordInsideWord.main(new String[0]);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"Pekka kk\" " + v, e);
        }
    }   

    @Test
    public void test1a() {
        String s1 = "glitter";
        String s2 = "lit";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");

        String odotettu = loytyy(s1, s2);
        String odottamaton = eiLoydy(s1, s2);
        String odotettuM = loytyyM(s1, s2);
        String odottamatonM = eiLoydyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM, out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM, out.contains(odottamaton));
    }

    @Test
    public void test1b() {
        String s1 = "glitter";
        String s2 = "liter";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");

        String odotettu = eiLoydy(s1, s2);
        String odottamaton = loytyy(s1, s2);
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odottamaton));
    }

    @Test
    public void test2a() {
        String s1 = "information";
        String s2 = "form";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");
        String odotettu = loytyy(s1, s2).toLowerCase();
        String odottamaton = eiLoydy(s1, s2);
        String odotettuM = loytyyM(s1, s2);
        String odottamatonM = eiLoydyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odottamaton));
    }

    @Test
    public void test2b() {
        String s1 = "information";
        String s2 = "foam";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");
        String odotettu = eiLoydy(s1, s2).toLowerCase();;
        String odottamaton = loytyy(s1, s2).toLowerCase();;
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odottamaton));
    }

    @Test
    public void test2c() {
        String s1 = "information";
        String s2 = "formal";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");
        String odotettu = eiLoydy(s1, s2).toLowerCase();;
        String odottamaton = loytyy(s1, s2).toLowerCase();;
        String odotettuM = eiLoydyM(s1, s2);
        String odottamatonM = loytyyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odottamaton));
    }

    @Test
    public void test2d() {
        String s1 = "information";
        String s2 = "info";
        io.setSysIn(s1 + "\n" + s2 + "\n");
        WordInsideWord.main(new String[0]);
        String out = io.getSysOut().toLowerCase().replaceAll("'", "").replaceAll("word", "").replaceAll("  ", " ");
        String odotettu = loytyy(s1, s2).toLowerCase();;
        String odottamaton = eiLoydy(s1, s2).toLowerCase();;
        String odotettuM = loytyyM(s1, s2);
        String odottamatonM = eiLoydyM(s1, s2);

        assertTrue("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odotettu));
        assertFalse("With input " + s1 + " " + s2 + " your program should print " + odotettuM + "", out.contains(odottamaton));
    }

    private String loytyyM(String s1, String s2) {
        return "The word '" + s2 + "' is found in the word '" + s1 + "'";
    }

    private String eiLoydyM(String s1, String s2) {
        return "The word '" + s2 + "' is not found in the word '" + s1 + "'";
    }

    private String loytyy(String s1, String s2) {
        return s2 + " is found in the " + s1 ;
    }

    private String eiLoydy(String s1, String s2) {
        return s2 + " is not found in the " + s1;
    }
}
