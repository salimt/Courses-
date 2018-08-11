
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("47")
public class LengthOfNameTest {

    @Rule
    public MockStdio io = new MockStdio();
    Method metodi;
    String metodinNimi = "calculateCharacters";

    @Test
    public void methodExists() {
        String virhe = "you should have method public static int " + metodinNimi + "(String text) in your program";

        try {
            metodi = ReflectionUtils.requireMethod(LengthOfName.class, metodinNimi, String.class);
        } catch (Throwable e) {
            fail(virhe);
        }

        assertTrue(virhe, metodi.toString().contains("static"));
        assertTrue(virhe, int.class == metodi.getReturnType());
        assertTrue(virhe, metodi.toString().contains("public"));
        //assertTrue(virhe, metodi.isAccessible());2
    }

    @Test
    public void test1() throws Throwable {
        String syote = "test";
        int tulos = kutsuWrap(syote, "command calculateCharacters(\""+syote+"\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", syote.length(), tulos);
    }

    @Test
    public void test2() throws Throwable {
        String syote = "programming";
        int tulos = kutsuWrap(syote, "command calculateCharacters(\""+syote+"\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", syote.length(), tulos);
    }

    @Test
    public void test3() throws Throwable {
        String syote = "Edsger Dijkstra";
        int tulos = kutsuWrap(syote, "command calculateCharacters(\""+syote+"\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", syote.length(), tulos);
    }

    @Test
    public void mainWorks() {
        io.setSysIn("Pekka\n");
        LengthOfName.main(new String[0]);
        assertTrue("With user input \"Pekka\" program should print 5", io.getSysOut().contains("5"));
    }

    private int kutsuWrap(String syote, String viesti) throws Throwable {
        try {
            metodi = ReflectionUtils.requireMethod(LengthOfName.class, metodinNimi, String.class);
            return ReflectionUtils.invokeMethod(int.class, metodi, null, syote);
        } catch (Throwable t){
            throw new Exception(viesti, t);
        }
    }

}
