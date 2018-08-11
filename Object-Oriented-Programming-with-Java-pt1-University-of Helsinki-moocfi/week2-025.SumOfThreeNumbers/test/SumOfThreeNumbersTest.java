
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

@Points("25")
public class SumOfThreeNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {{3, 6, 12}, {-1, 0, 1}, {9, 9, 9}};

        for (int[] syote : syotteet) {
            tarkista(syote);
        }
    }

    private void tarkista(int[] syoteLuvut) {
        int oldOut = io.getSysOut().length();
        String syote = "";
        int summa = 0;
        for (int luku : syoteLuvut) {
            summa += luku;
            syote += luku + "\n";
        }

        io.setSysIn(syote);
        callMain(SumOfThreeNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        int vastaus = otaLukuLopusta(out);

        syote = syote.replaceAll("\n", " ").trim();
        syote = syote.replaceAll(" ", " + ").trim();
        String virheIlm = "sum " + syote + " should be " + summa + ", you printed \"" + vastaus + "\"";
        assertTrue("you should print something", out.length() > 0);
        assertEquals(virheIlm, summa, vastaus);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("use Integer.parseInt( reader.nextline() ) to read the numbers");
        } catch (Throwable e) {
            fail("something unexpected happened "+e);
        }
    }

    private static int otaLukuLopusta(String inputStr) {
        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("Your output should be of the form \"Sum: 10\"", matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }
}
