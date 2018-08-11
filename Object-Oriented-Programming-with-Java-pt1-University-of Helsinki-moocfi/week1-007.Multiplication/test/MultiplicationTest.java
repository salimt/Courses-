import org.junit.Test;
import org.junit.Rule;
import java.util.regex.*;

import static org.junit.Assert.*;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;

@Points("7")
public class MultiplicationTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        Multiplication.main(new String[0]);
        String out = io.getSysOut();

        assertTrue("You did not print anything!",out.trim().length()>0);

        Matcher m = Pattern.compile("(?s).*?(\\d+)\\s*\\*\\s*(\\d+)\\s*=\\s*(\\d+).*").matcher(out);

        assertTrue("Your output should be of the form 'X * Y = Z' for some integers X, Y ja Z. Now it was: "+out,
                   m.matches());

        String sa = m.group(1).trim();
        String sb = m.group(2).trim();
        String sc = m.group(3).trim();

        int a,b,c;

        try {
            a = Integer.parseInt(sa);
        } catch (NumberFormatException e) {
            fail("String \""+sa+"\" that you printed is not an integer");
            return; // tyhmä java
        }

        try {
            b = Integer.parseInt(sb);
        } catch (NumberFormatException e) {
            fail("String \""+sb+"\" that you printed is not an integer");
            return; // tyhmä java
        }

        try {
            c = Integer.parseInt(sc);
        } catch (NumberFormatException e) {
            fail("String \""+sc+"\" that you printed is not an integer");
            return; // tyhmä java
        }

        assertEquals("Your program claims that "+a+" * "+b+" = "+c+" , but that is not true!",a*b,c);
    }
}
