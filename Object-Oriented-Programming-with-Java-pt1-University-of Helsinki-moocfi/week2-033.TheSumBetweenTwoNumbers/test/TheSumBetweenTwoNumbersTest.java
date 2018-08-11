
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("33")
public class TheSumBetweenTwoNumbersTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {{1, 2, 3}, {2, 4, 9}, {3, 6, 18}, {4, 7, 22}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i][0], syotteet[i][1], syotteet[i][2]);
        }
    }

    private void tarkista(int eka, int vika, int odotettuVastaus) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(eka + "\n" + vika + "\n");
        callMain(TheSumBetweenTwoNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("You should print something!", out.length() > 0);

        int vastaus = otaLukuLopusta(out);
        
        String virheIlm = "Sum of " + eka + ".." + vika + " should be " + odotettuVastaus + ", you printed \"" + out + "\"";
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

        assertTrue("your output should be of the form \"The sum is 10\"",matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }
}
