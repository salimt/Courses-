import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("36.4")
public class Part4Test {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {
            {1, -1, 1},
            {2, 6, -1, 4},
            {2, 6, 5, 7, -1, 5}, 
            {6, 1, 4, 7, 4, 8, -1, 5}
        };

        for (int i = 0; i < syotteet.length; i++) {
            tarkasta(syotteet[i], "Average");
        }
    }

    @Test
    public void testi2() {
        int[] syotteet = {2, 5, -1, 0};
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringiksi(syotteet));
        callMain(LoopsEndingRemembering.class);
        String out = io.getSysOut().substring(oldOut);

        String virheIlm = "With input " + stringiksiValilla(syotteet)
                + " your program should print \"Average: 3.5\", output was " +
                rivi(out, "rage");
        assertTrue("you do not print anything", out.length() > 0);
        assertTrue(virheIlm, out.contains("3.5"));
    }

    private void tarkasta(int[] syotteet, String mj) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringiksi(syotteet));
        callMain(LoopsEndingRemembering.class);
        String out = io.getSysOut().substring(oldOut);
        int odotettu = tulos(syotteet);

        String virheIlm = "With input " + stringiksiValilla(syotteet)
                + " \" your program should print \"" + mj + ": " + odotettu +
                ".0\", but you printed " + rivi(out, "verage");
        assertTrue("you do not print anything", out.length() > 0);
        assertTrue(virheIlm, out.contains(odotettu+".0"));
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
        for (int i = 0; i < taulukko.length - 1; i++) {
            tuloste += taulukko[i] + "\n";

        }

        return tuloste;
    }

    private String stringiksiValilla(int[] taulukko) {
        String tuloste = "";
        for (int i = 0; i < taulukko.length - 1; i++) {
            tuloste += taulukko[i] + " ";
        }

        return tuloste;
    }

    private int tulos(int[] syotteet) {
        return syotteet[syotteet.length - 1];
    }

    private String rivi(String out, String mj) {
        for (String rivi : out.split("\n")) {
            if (rivi.toLowerCase().contains(mj.toLowerCase())) {
                return rivi;
            }
        }

        fail("Your program should print average of the user input");
        return "";
    }
}
