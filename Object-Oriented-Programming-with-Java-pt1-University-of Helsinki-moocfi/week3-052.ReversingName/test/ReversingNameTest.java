
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("52")
public class ReversingNameTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void noExceptions() throws Exception {
        io.setSysIn("pekka\n");
        try {
            ReversingName.main(new String[0]);
        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"";
        
            throw new Exception("With input \"Pekka\" " + v, e);
        }
    }

    @Test
    public void test1() {
        testaa("Pekka", "akkeP");
    }

    @Test
    public void test2() {
        testaa("Katariina", "aniirataK");
    }

    @Test
    public void test3() {
        testaa("saippuakauppias", "saippuakauppias");
    }

    @Test
    public void test4() {
        testaa("m", "m");
    }

    @Test
    public void test5() {
        testaa("Oa", "aO");
    }

    @Test
    public void test6() {
        testaa("jIk", "kIj");
    }

    @Test
    public void test7() {
        testaa("apfjviaweojmfviaowfjisadfklnrnwaieraisdf",
                "fdsiareiawnrnlkfdasijfwoaivfmjoewaivjfpa");
    }

    @Test
    public void test8() {
        testaa("ujoPoju", "ujoPoju");
    }

    @Test
    public void test9() {
        testaa("aattonaJanottaa", "aattonaJanottaa");
    }

    private void testaa(String alkup, String oletettu) {
        io.setSysIn(alkup + "\n");
        ReversingName.main(new String[0]);
        assertTrue(getViesti(alkup, oletettu),
                io.getSysOut().contains(oletettu));
    }

    private String getViesti(String alkup, String oletettu) {
        return "Check that with input " + alkup
                + " you print " + oletettu;
    }
}
