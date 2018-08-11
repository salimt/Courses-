import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("32")
public class TheSumOfSetOfNumbersTest {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {{3,6}, {4,10}, {5,15}, {10,55}};

        for (int i = 0; i < syotteet.length; i++) {
            tarkista(syotteet[i][0], syotteet[i][1]);
            
        }                
    }

    private void tarkista(int vika, int odotettuVastaus) {
        int eka = 1;
        int oldOut = io.getSysOut().length();
        io.setSysIn(vika + "\n");
        callMain(TheSumOfSetOfNumbers.class);
        String out = io.getSysOut().substring(oldOut);

        int vastaus = otaLukuLopusta(out);
        
        String virheIlm = "sum " + eka + ".." + vika + " should be " +
                odotettuVastaus + ", but you printed \"" + out + "\"";
        assertTrue("you should print something!", out.length() > 0);
        assertEquals(virheIlm, odotettuVastaus, vastaus);
        
        assertFalse("sum should be a positive number, now you printed "+out,out.contains("-"+odotettuVastaus));
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

        assertTrue("your output should be of the form \"Sum is 10\"",matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }
}
