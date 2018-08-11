
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("97")
public class MainTest {

    @Test
    public void testPrinting() {
        int[] taulukoidenPituudet = {1, 2, 3, 4, 5, 10, 20, 100};

        Random random = new Random();
        for (int i = 0; i < taulukoidenPituudet.length; i++) {
            int pituus = taulukoidenPituudet[i];
            int[] taulukko = new int[pituus];
            for (int j = 0; j < taulukko.length; j++) {
                taulukko[j] = random.nextInt(150);
            }

            tarkistaTaulukko(taulukko);
        }
    }

    private void tarkistaTaulukko(int[] taulukko) {
        MockInOut mio = new MockInOut("");

        Main.printElegantly(taulukko);

        String output = mio.getOutput().trim();
        if (taulukko.length == 0) {
            if (!output.isEmpty()) {
                fail("When methodprintElegantly() "
                        + "was given an empty array, it printed: " + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("You did not print anything when the parameter was "+Arrays.toString(taulukko));
        }

        String[] numbers = output.split(",", -1);
        if (numbers.length == 0) {
            fail("You did not print anything when the parameter was  "+Arrays.toString(taulukko));
        }
        if (numbers.length != taulukko.length) {
            fail("Output should have " + (taulukko.length-1) + " commas but there were " + (numbers.length-1) + ". Array was: " + Arrays.toString(taulukko));
        }

        int loppu = Math.max(0, output.length()-2);
        assertFalse("the only linefeed of the output should be after the last number! \n"
                + "With input "+Arrays.toString(taulukko)+" you printed\n"+output,
                output.substring(0, loppu).contains("\n") );
                
        
        for (int i = 0; i < numbers.length; i++) {
            String numberString = numbers[i].trim();
            if (numberString.isEmpty()) {
                fail("Tulosteessa täytyy olla jokaisen pilkun jälkeen numero. Tarkista, että tuloste ei lopu pilkkuun. Tulostit: " + output);
            }

            int number;
            try {
                number = Integer.parseInt(numberString);
            } catch (Exception e) {
                fail("The output should only consists of numbers separated with commas! "
                        + "This is not a number: " + numberString + ". Your output: " + output);
                return;
            }

            if (number != taulukko[i]) {
                fail("In array index" + i + " was the number " + taulukko[i] + ", "
                        + "but your output had: " + number);
            }
        }
    }
}
