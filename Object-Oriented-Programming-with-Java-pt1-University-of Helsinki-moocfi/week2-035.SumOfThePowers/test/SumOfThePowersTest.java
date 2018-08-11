
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("35")
public class SumOfThePowersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        int[][] syotteet = {{2, 7}, {5, 63}, {7, 255}, {9, 1023}, {12, 8191}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i][0], syotteet[i][1]);
        }
    }

    public void tarkista(int luku, int odotettuVastaus) {
        io.disable();
        io.enable();
        
        io.setSysIn(luku + "\n");
        callMain(SumOfThePowers.class);
        String out = io.getSysOut();

        assertTrue("You do not print anything", out.length() > 0);

        int vastaus = otaLukuLopusta(out);

        String virheIlm = "With input " + luku + " the result should be " + odotettuVastaus + " but you printed \"" + out + "\"";
        assertEquals(virheIlm, odotettuVastaus, vastaus);
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("remember to read user input with Integer.parseInt( reader.nextLine() ); \ncall it only once!");
        } catch (Throwable e) {
            fail("something unexpected happened, more info "+e);
        }
    }
   

    private static int otaLukuLopusta(String inputStr) {
        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("your output should be of the form \"The result is 10\"",matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }
}
