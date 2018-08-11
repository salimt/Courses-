
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

@Points("49")
public class LastCharacterTest {

    @Rule
    public MockStdio io = new MockStdio();
    public Method metodi;
    public String metodinNimi = "lastCharacter";

    @Test
    public void metehodExists() {
        String virhe = "you should have method public static char " + metodinNimi + "(String text) in your program";

        try {
            metodi = ReflectionUtils.requireMethod(LastCharacter.class, metodinNimi, String.class);

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
        metodi = ReflectionUtils.requireMethod(LastCharacter.class, metodinNimi, String.class);
        char tulos = kutsuWrap(syote, "command lastCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        
        String oletus = syote.charAt(syote.length() - 1) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);
    }

    @Test
    public void test2() throws Throwable {
        String syote = "Mannerheim";
        metodi = ReflectionUtils.requireMethod(LastCharacter.class, metodinNimi, String.class);
        char tulos = kutsuWrap(syote, "command lastCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        
        String oletus = syote.charAt(syote.length() - 1) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);
    }

    @Test
    public void testi3() throws Throwable {
        String syote = "Raikkonen";
        metodi = ReflectionUtils.requireMethod(LastCharacter.class, metodinNimi, String.class);
        char tulos = kutsuWrap(syote, "command lastCharacter(\"" + syote + "\");\n\n"
                + "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                + "\"Caused by\"" );
        
        String oletus = syote.charAt(syote.length() - 1) + "";
        String saatu = tulos + "";
        assertEquals(metodinNimi + " with parameter \"" + syote+"\"", oletus, saatu);
    }

    @Test
    public void mainWorks1() {
        io.setSysIn("Pekka");
        LastCharacter.main(new String[0]);
        io.setSysIn("Pekka");
        LastCharacter.main(new String[0]);
        assertTrue("With input \"Pekka\" your program should print \"Last character: a\"",
                io.getSysOut().trim().endsWith("a"));
    }

    @Test
    public void mainWorks2() {
        io.setSysIn("Paavo Nurmi");
        LastCharacter.main(new String[0]);
        assertTrue("With input \"Paavo Nurmi\" your program should print \"Last character: i\"",
                io.getSysOut().trim().endsWith("i"));
    }

    private char kutsuWrap(String syote, String viesti) throws Throwable {
        try {
            return kutsu(syote);
        } catch (Throwable t) {
            String file = t.getStackTrace()[1].getFileName();
            int line = t.getStackTrace()[1].getLineNumber();

            if (t.getStackTrace()[0].getFileName().contains("Kirjain")) {
                file = t.getStackTrace()[0].getFileName();
                line = t.getStackTrace()[0].getLineNumber();
            }

            throw new Exception(viesti + " caused exception " + t + " at file: " + file + " line: " + line);
        }

    }

    private char kutsu(String syote) throws Throwable {
        metodi = ReflectionUtils.requireMethod(LastCharacter.class, metodinNimi, String.class);
        return ReflectionUtils.invokeMethod(char.class, metodi, null, syote);
    }
}
