
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("12")
public class SumOfTheAgesTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void vainYksiNumero() {
        ReflectionUtils.newInstanceOfClass(SumOfTheAges.class);
        io.setSysIn("arto\n1\npekka\n2\n");
        try {
            SumOfTheAges.main(new String[0]);
        } catch (NumberFormatException e) {
            fail("Check that you read the input in correct order, first the name and then the age.");
        }

        String out = io.getSysOut();
        Pattern pattern = Pattern.compile("[^\\d]*(\\d+).*[^\\d]*(\\d+).*",
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(out);
        if (matcher.matches()) {
            fail("There should be no other values in your output than the sum of the ages. You printed: " + out);
        }
    }

    @Test
    public void test() {
        testIkienSumma("Matti", 2, "Arto", 1);
    }

    @Test
    public void testToinen() {
        testIkienSumma("Teodor", 0, "Bart", 13);
    }

    private void testIkienSumma(String ekanNimi, int ekanIka, String tokanNimi, int tokanIka) {
        ReflectionUtils.newInstanceOfClass(SumOfTheAges.class);
        io.setSysIn(ekanNimi + "\n" + ekanIka + "\n" + tokanNimi + "\n" + tokanIka + "\n");
        try {
            SumOfTheAges.main(new String[0]);
        } catch (NumberFormatException e) {
            fail("Check that you read the input in correct order, first the name and then the age.");
        }

        String out = io.getSysOut();

        assertTrue("You did not ask anyting from the user", out.trim().length() > 0);

        assertTrue("There should be \":\" in your output. Now you printed " + out,
                out.contains(":"));

        String nimet = ekanNimi + " and " + tokanNimi;
        assertTrue("\"There should be \"" + nimet + "\", in your output. Now you printed " + out,
                out.contains(nimet));

        Pattern pattern = Pattern.compile("[^\\d]*(\\d+).*",
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(out);
        if (!matcher.matches()) {
            fail("The sum of ages sould be in output. Now you printed" + out);
            return;
        }

        int summa = Integer.parseInt(matcher.group(1));

        assertTrue("Sum of ages " + ekanIka + " and " + tokanIka + " should be " + (ekanIka + tokanIka) + ", you printed" + out, (ekanIka + tokanIka) == summa);
    }
}
