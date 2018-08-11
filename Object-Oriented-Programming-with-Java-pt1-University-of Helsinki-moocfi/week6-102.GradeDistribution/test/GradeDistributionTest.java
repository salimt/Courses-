import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("102.1 102.2 102.3")
public class GradeDistributionTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test1() {
        int[][] luvut = {{34}, {11, 55, 52}, {}};
        for (int i = 0; i < luvut.length; i++) {
            testaaSyote(luvut[i]);
        }
    }

    @Test
    public void test2() {
        int[][] luvut = {{34}, {11, 55, 52}};
        for (int i = 0; i < luvut.length; i++) {
            testaaTuloste(luvut[i]);
        }
    }

    @Test
    public void testi1() {
        int[] luvut = {34};
        int[] jakauma = {0, 1, 0, 0, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testi2() {
        int[] luvut = {36};
        int[] jakauma = {0, 0, 1, 0, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testi3() {
        int[] luvut = {44};
        int[] jakauma = {0, 0, 0, 1, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testi4() {
        int[] luvut = {46};
        int[] jakauma = {0, 0, 0, 0, 1, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testi5() {
        int[] luvut = {54};
        int[] jakauma = {0, 0, 0, 0, 0, 1};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }


    @Test
    public void testiHylsy() {
        int[] luvut = {4};
        int[] jakauma = {1, 0, 0, 0, 0, 0};
        double pros = 0;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testiMonta1() {
        int[] luvut = {34, 39};
        int[] jakauma = {0, 1, 1, 0, 0, 0};
        double pros = 100;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testiMonta2() {
        int[] luvut = {3, 59};
        int[] jakauma = {1, 0, 0, 0, 0, 1};
        double pros = 50;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testiMonta3() {
        int[] luvut = {44, 12, 58, 29, 60};
        int[] jakauma = {2, 0, 0, 1, 0, 2};
        double pros = 60;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testiMonta4() {
        int[] luvut = {44, 12, 58, 34, 46};
        int[] jakauma = {1, 1, 0, 1, 1, 1};
        double pros = 80;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void testiMonta5() {
        int[] luvut = {34, 41, 53, 36, 55, 27, 43, 35, 40, 11};
        int[] jakauma = {2, 1, 2, 3, 0, 2};
        double pros = 80;
        testaa(luvut, jakauma, pros);
    }

    @Test
    public void vaaraSyote() {
        int[] luvut = {42, 61, 15, -2};
        int[] jakauma = {1, 0, 0, 1, 0, 0};
        double pros = 50;
        try {
            testaa(luvut, jakauma, pros);
        } catch (Throwable e) {
            fail("Remember that scores outside the range 0-60 should be skipped\n"
                    + "with input "+Arrays.toString(luvut)+" your code caused\n" +e);
        }
    }

    /*
     * helpers
     */
    private void testaa(int[] luvut, int[] jakauma, double pros) {
        io.setSysIn(syote(luvut) + "-1\n");
        Main.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");

        String pros2 = (""+pros).replace('.', ',');
        
        String rivi = haeRivi(rivit, "centage");
        assertTrue("with input " + toS(luvut) + " the acceptance percentage should be " + pros + ", now the output was: \"" + rivi + "\"", 
                rivi.contains("" + pros) || rivi.contains(pros2));

        assertFalse("ensure that you print a line with the text  \"Acceptance percentage:\"", rivi == null);
        for (int i = 0; i < 6; i++) {
            rivi = haeRivi(rivit, i + ":");
            tarkastaArvosana(i, jakauma[i], rivi, luvut);
        }
    }

    private void tarkastaArvosana(int i, int odotettu, String rivi, int[] luvut) {
        if (odotettu == 0) {
            assertFalse("with input " + toS(luvut) + " the line with grade " + i + " should not contain asterisks. "
                    + "Now the output was \"" + rivi + "\"", rivi.contains("*"));
            return;
        }

        String tahdet = "";
        for (int j = 0; j < odotettu; j++) {
            tahdet += "*";
        }

        assertTrue("with input " + toS(luvut) + " your program should output the line \""+i+": "+tahdet
                + " now the output was \"" + rivi + "\"", rivi.contains(tahdet));
        assertFalse("with input " + toS(luvut) + " your program should output the line \""+i+": "+tahdet
                + " now the output was \"" + rivi + "\"", rivi.contains(tahdet+"*"));
    }

    private void testaaTuloste(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        Main.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");

        String rivi = haeRivi(rivit, "tribution");
        assertFalse("ensure that you print a line with the text  \"Grade distribution:\"", rivi == null);
        rivi = haeRivi(rivit, "centage");
        assertFalse("ensure that you print a line with the text  \"Acceptance percentage:\"", rivi == null);
        for (int i = 0; i < 6; i++) {
            rivi = haeRivi(rivit, i + ":");
            assertFalse("ensure that you print a line with the text  \"" + i + ":\"", rivi == null);
        }
    }

    private void testaaSyote(int[] luvut) {
        io.setSysIn(syote(luvut) + "-1\n");
        try {
            Main.main(new String[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("You refer outside an array or ArrayList with input " + toS(luvut));
        } catch (NoSuchElementException e) {
            fail("your program should stop with input " + toS(luvut));
        } catch (ArithmeticException e) {
            if (toS(luvut).equals("-1")) {
                fail("your program divides by zero when input is only -1 "
                        + "i.e. no scores are given."
                        + "\nThe problem is likely in the way acceptance percentage is calculated "
                        + "in the scoreless case.");
            } else {
                fail("Problem with the with input " + toS(luvut)+"\n more info: "+e);
            }
        } catch (Exception e) {
            fail("Something unexpected happened with input " + toS(luvut) + " more info " + e);
        }
    }

    private String syote(int[] luvut) {
        String mj = "";

        for (int luku : luvut) {
            mj += luku + "\n";
        }

        return mj;
    }

    private String toS(int[] luvut) {
        if (luvut.length == 0) {
            return "-1";
        }

        return Arrays.toString(luvut).replace("[", "").replace("]", "") + ", -1";
    }

    private String haeRivi(String[] rivit, String haettava) {
        for (String rivi : rivit) {
            if (rivi.contains(haettava)) {
                return rivi;
            }
        }

        return null;
    }
}
