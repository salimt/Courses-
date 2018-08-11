
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

@Points("48")
public class FirstCharacterTest {

    @Rule
    public MockStdio io = new MockStdio();
    public Method metodi;
    public String metodinNimi = "firstCharacter";

    @Test
    public void methodExists() {
        String virhe = "you should have method public static char " + metodinNimi + "(String text) in your program";

        try {
            metodi = ReflectionUtils.requireMethod(
                    FirstCharacter.class, metodinNimi, String.class);

        } catch (Throwable e) {
            fail(virhe);
        }

        assertTrue(virhe, metodi.toString().contains("static"));
        assertTrue(virhe, char.class == metodi.getReturnType());
        assertTrue(virhe, metodi.toString().contains("public"));
    }

    @Test
    public void test1() throws Throwable {
        String syote = "test";
        metodi = ReflectionUtils.requireMethod(FirstCharacter.class, metodinNimi, String.class);

        char tulos = kutsuWrap(syote, "command firstCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );

        String oletus = syote.charAt(0) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);
    }

    @Test
    public void testi2() throws Throwable {
        String syote = "ohjelmointi";
        metodi = ReflectionUtils.requireMethod(FirstCharacter.class, metodinNimi, String.class);

        char tulos = kutsuWrap(syote, "command firstCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );

        String oletus = syote.charAt(0) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);        
        

    }

    @Test
    public void testi3() throws Throwable {
        String syote = "mooc-verkkokurssi";
        metodi = ReflectionUtils.requireMethod(FirstCharacter.class, metodinNimi, String.class);
        char tulos = kutsuWrap(syote, "command firstCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );

        String oletus = syote.charAt(0) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);
        
    }

    @Test
    public void mainWorks() {
        io.setSysIn("Pekka");
        FirstCharacter.main(new String[0]);
        assertTrue("With input \"Pekka\" your program should print \"First character: P\"",
                io.getSysOut().trim().endsWith("P"));

    }

    @Test
    public void mainWorks2() {
        io.setSysIn("Martti Ahtisaari");
        FirstCharacter.main(new String[0]);
        assertTrue("With input \"Martti Ahtisaari\" your program should print \"First character: P\"",
                io.getSysOut().trim().endsWith("M"));

    }

    private char kutsuWrap(String syote, String viesti) throws Throwable {
        try {
            metodi = ReflectionUtils.requireMethod(FirstCharacter.class, metodinNimi, String.class);
            return ReflectionUtils.invokeMethod(char.class, metodi, null, syote);
        } catch (Throwable t) {
            throw new Exception(viesti, t);
        }

    }
}
