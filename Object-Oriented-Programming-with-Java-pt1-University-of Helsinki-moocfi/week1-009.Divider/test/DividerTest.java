
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("9")
public class DividerTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testDivider() {
        dividerTest(3, 2);
    }

    @Test
    public void testNegative() {
        dividerTest(3, -2);
    }

    @Test
    public void testWithInfinitelyManyDecimals() {
        dividerTest(10, 3);
    }

    private void dividerTest(int ekaLuku, int tokaLuku) {
        ReflectionUtils.newInstanceOfClass(Divider.class);
        io.setSysIn(ekaLuku + "\n" + tokaLuku + "\n");
        Divider.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask anything!", out.trim().length() > 0);

        assertTrue("You should output \":\" when prompting user for input. You printed: " + out,
                out.contains(":"));


        assertTrue("You should output text 'Division: X / Y = Z' for some integers X, Y and Z. You printed: " + out,
                out.contains("ivision:"));


        String jakoMjono = out.substring(out.indexOf("ivision:") + "ivision:".length());

        Matcher m = Pattern.compile("(?s)\\s*?([-.0-9]+)\\s*/\\s*([-.0-9]+)\\s*=\\s*([-.0-9]+)\\s*").matcher(jakoMjono);

        assertTrue("Your output should be of the form 'X / Y = Z' for some integers X, Y and Z. Now it was: " + jakoMjono,
                m.matches());


        String sa = m.group(1).trim();
        String sb = m.group(2).trim();
        String sc = m.group(3).trim();

        double a, b, c;

        try {
            a = Double.parseDouble(sa);
        } catch (NumberFormatException e) {
            fail("String \"" + sa + "\" that you printed is not a proper value");
            return; // tyhmä java
        }

        try {
            b = Double.parseDouble(sb);
        } catch (NumberFormatException e) {
            fail("String \"" + sb + "\" that you printed is not a proper value");
            return; // tyhmä java
        }

        try {
            c = Double.parseDouble(sc);
        } catch (NumberFormatException e) {
            fail("String \"" + sc + "\" that you printed is not a proper value");
            return; // tyhmä java
        }

        if (ekaLuku != (int)a || tokaLuku != (int)b) {
          fail("String \"" + jakoMjono + "\" contains wrong formula when user inputted values " + ekaLuku + " and " + tokaLuku);
        }

        double tulos = 1.0 * a / b;
        assertEquals(a + " / " + b + " should be "
                + tulos + ", you proposed the answer to be " + c + "\n"
                + "Did you remember to convert the values to double type?", tulos, c, 0.001);
    }
}
