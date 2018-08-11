import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("36.5")
public class Part5bTest {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] syotteet = {
            {1, -1, 1},
            {2, 5, -1, 1},
            {6, 1, 4, 7, 4, 9, -1, 3},
            {7, 2, 4, 7, 4, 9, 5, 1, 5, 7, -1, 7}
        };
 
        for (int i = 0; i < syotteet.length; i++) {
            tarkasta(syotteet[i], "Odd");
        }
    }

    private void tarkasta(int[] syotteet, String mj) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringiksi(syotteet));
        callMain(LoopsEndingRemembering.class);
        String out = io.getSysOut().substring(oldOut);
        int odotettu = tulos(syotteet);

        String virheIlm = "With input "+stringiksiValilla(syotteet)+ 
                " you should print \""+mj+": "+odotettu+"\"";
        assertTrue("You should print something", out.length() > 0);
        assertEquals(virheIlm, odotettu, otaLukuLopusta(rivi(out, mj)));
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

    private static int otaLukuLopusta(String inputStr) {
        String patternStr = "(?s).*?(\\d+)\\s*$";

        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);

        assertTrue("Output should contain line the form \"Odd numbers: 3\"", matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }


    private String stringiksi(int[] taulukko) {
        String tuloste = "";
        for (int i = 0; i < taulukko.length-1; i++) {
            tuloste += taulukko[i] + "\n";
            
        }        

        return tuloste;
    }
    
    private String stringiksiValilla(int[] taulukko) {
        String tuloste = "";
        for (int i = 0; i < taulukko.length-1; i++) {
            tuloste += taulukko[i] + " ";            
        }  

        return tuloste;
    }

    private int tulos(int[] syotteet) {
        return syotteet[syotteet.length-1];
    }

    private String rivi(String out, String mj) {
        for (String rivi : out.split("\n")) {
            if ( rivi.toLowerCase().contains(mj.toLowerCase())) return rivi;
        }
        
        fail("You should print the count of odd numbers"
                + " in the following form \"Odd numbers: 3\"\n");
        return "";    
    }
}
