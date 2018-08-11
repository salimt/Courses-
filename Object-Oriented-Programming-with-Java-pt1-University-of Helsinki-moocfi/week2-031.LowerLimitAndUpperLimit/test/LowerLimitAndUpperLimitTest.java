
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.*;
import static org.junit.Assert.*;

@Points("31")
public class LowerLimitAndUpperLimitTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        int[][] lukuparit = {{1, 1}, {12, 8}, {50, 100}, {-2, 2}};
        for (int[] lukupari : lukuparit) {
            testaa(lukupari);
        }
    }

    private void testaa(int[] lukupari) {
        io.setSysIn(lukupari[0] + "\n" + lukupari[1] + "\n");
        int len = io.getSysOut().length();

        ReflectionUtils.newInstanceOfClass(LowerLimitAndUpperLimit.class);
        LowerLimitAndUpperLimit.main(new String[0]);
        String tulostus = io.getSysOut().substring(len);

        tulostus = tulostus.replaceAll("[^-\\d]+", " ").trim();
        String[] rivit = tulostus.split("\\s+");
        int rivejaTulostuksessa = (rivit.length == 1 && rivit[0].isEmpty()) ? 0 : rivit.length;

        int rivienLkm;
        if (lukupari[1] < lukupari[0]) {
            rivienLkm = 0;
        } else {
            rivienLkm = lukupari[1] - lukupari[0] + 1;
        }

        if (rivienLkm != rivejaTulostuksessa) {
            String numeroLkm = (rivienLkm == 1) ? "number" : "numbers";
            fail("With input " + lukupari[0] + ", " + lukupari[1] + " oupput shold have " + rivienLkm + " " + numeroLkm + ", now there was " + rivejaTulostuksessa);

        }

        if (rivienLkm == 0) {
            return;
        }

        int ekaLuku = Integer.parseInt(rivit[0]);
        if (ekaLuku != lukupari[0]) {
            fail("With input " + lukupari[0] + ", " + lukupari[1] + " first printed number should be " + lukupari[0] + ", now it was " + ekaLuku);
        }

        int vikaLuku = otaLukuLopusta(tulostus);
        if (vikaLuku != lukupari[1]) {
            fail("With input " + lukupari[0] + ", " + lukupari[1] + " last printed number should be " + lukupari[1] + ", now it was " + vikaLuku);
        }
    }

    private static int otaLukuLopusta(String inputStr) {
        String patternStr = "(?s).*?(-?\\d+)\\s*$";
        Matcher matcher = Pattern.compile(patternStr).matcher(inputStr);
        assertTrue("You should print numbers only!", matcher.find());

        int luku = Integer.parseInt(matcher.group(1));
        return luku;
    }
}
