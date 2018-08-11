import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("36.3")
public class Part3Test {
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        int[][] syotteet = {
            {1, -1, 1},
            {2, 5, -1, 2},
            {6, 1, 4, 7, 4, 9, -1, 6},
            {7, 2, 4, 7, 4, 9, 5, 1, 5, 7, -1, 10}
        };
 
        for (int i = 0; i < syotteet.length; i++) {
            tarkasta(syotteet[i], "How many numbers", "ow many");
        }
    }


    private void tarkasta(int[] syotteet, String mj,String match) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(stringiksi(syotteet));
        callMain(LoopsEndingRemembering.class);
        String out = io.getSysOut().substring(oldOut);
        int odotettu = tulos(syotteet);

        String virheIlm = "With input "+stringiksiValilla(syotteet)+ 
                " you should print \""+mj+": "+odotettu+"\"";
        assertTrue("You should print something", out.length() > 0);
        assertEquals(virheIlm, odotettu, otaLukuLopusta(rivi(out, match)));
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

        assertTrue("Output should be of the form \"The sum is 3\"", matcher.find());

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
            if (rivi.toLowerCase().contains(mj.toLowerCase())) {
                return rivi;
            }
        }
        
        fail("Output should be of the form \"How many numbers: 3\"\n");
        return "";    
    }
}
