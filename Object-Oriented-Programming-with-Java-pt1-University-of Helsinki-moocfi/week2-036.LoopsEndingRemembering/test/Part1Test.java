import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("36.1")
public class Part1Test {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {{1, -1}, {2, 5, -1}};

       
        for (int i = 0; i < syotteet.length; i++) {
            int oldOut = io.getSysOut().length();
            io.setSysIn(stringiksi(syotteet[i]));
            callMain(LoopsEndingRemembering.class);
            String out = io.getSysOut().substring(oldOut);

            String virheIlm = "When user input it -1, should your program output \"Type numbers:\" and "
                    + "\"Thank you and see you later!\""  ;
            assertTrue("You do not print anything", out.length() > 0);
            assertTrue(virheIlm, out.contains("ype n"));
            assertTrue(virheIlm, out.contains("hank"));
        }

    }


    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (NoSuchElementException e) {
            fail("remember to exit the loop when user enters -1");
        } catch (Throwable e) {
            fail("Something unexpected happened, more info: +e");
        }
    }

    private String stringiksi(int[] taulukko) {
        String tuloste = "";
        for (int luku : taulukko) {
            tuloste += luku + "\n";
        }

        return tuloste;
    }
}
