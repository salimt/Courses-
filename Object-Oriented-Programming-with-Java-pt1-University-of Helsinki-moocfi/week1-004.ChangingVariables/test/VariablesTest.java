import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import java.util.regex.*;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;

@Points("4")
public class VariablesTest {

    String out;

    @Rule
    public MockStdio io = new MockStdio();

    @Before
    public void sieppaaTulostus() {
        Variables.main(new String[0]);
        out = io.getSysOut();
    }

    String ekaRegex(String a, String b) {
        return "(?s).*"+a+"\\s*"+b+"\\s.*";
    }

    String tokaRegex(String a, String b) {
        return "(?s).*In a nutshell:.*\\s*"+b+"\\s.*";
    }

    void testaa(String a, String b) {
        assertTrue("Check that "+a+" prints right",
                   Pattern.matches(ekaRegex(a,b),out));
        assertTrue("Check that "+a+" prints right also in the nutshell",
                   Pattern.matches(tokaRegex(a,b),out));
    }

    @Test
    public void testaaKanat() {
        testaa("Chickens:","9000");
    }

    @Test
    public void testaaPekoni() {
        testaa("Bacon \\(kg\\):","0\\.1");
    }

    @Test
    public void testaaTraktori() {
        testaa("A tractor:","Zetor");
    }

}
