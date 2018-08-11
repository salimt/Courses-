
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

@Points("99")
public class MainTest {

    @Test
    public void checkStars() {
        //int[] taulukoidenPituudet = {0, 1, 2, 3, 4, 5, 10, 20, 100};
        int[] taulukoidenPituudet = {1};
        
        Random random = new Random();
        for (int i = 0; i < taulukoidenPituudet.length; i++) {
            int pituus = taulukoidenPituudet[i];
            int[] taulukko = new int[pituus];
            for (int j = 0; j < taulukko.length; j++) {
                taulukko[j] = 1+random.nextInt(10)+pituus;
            }
            tarkistaTaulukko(taulukko);
        }
    }

    private void tarkistaTaulukko(int[] taulukko) {
        MockInOut mio = new MockInOut("");
        Main.printArrayAsStars(taulukko);

        String output = mio.getOutput().trim();
        if (taulukko.length == 0) {
            if (!output.isEmpty()) {
                fail("When method printArrayAsStars "
                        + "gets an empty array, you should not print anything. "
                        + "the output was: \n" + output);
            }
            return;
        }

        if (output.isEmpty()) {
            fail("You did not print anything with parameter "+Arrays.toString(taulukko));
        }

        String[] tahdet = output.split("\\n");
        if (tahdet.length == 0) {
            fail("You did not print anything with parameter "+Arrays.toString(taulukko));
        }

        if (tahdet.length < taulukko.length) {
            fail("When method called with parameter "+Arrays.toString(taulukko) +", output had only " + tahdet.length + " rows, "
                    + "based on the length of the array there should have been " + taulukko.length+ " rows." );
        }
        
        if (tahdet.length > taulukko.length) {
            fail("When method called with parameter "+Arrays.toString(taulukko) +", output had " + tahdet.length + " rows, "
                    + "based on the length of the array there should have been only " + taulukko.length+ " rows." );
        }
        
        for (int i = 0; i < tahdet.length; i++) {
            String rivi = tahdet[i].trim();
            int maara = taulukko[i];
            if (!rivi.matches("[\\*]+")) {
                fail("When method called with parameter "+Arrays.toString(taulukko) +", you should print only * characters. "
                        + "However, the following line was printed: " + rivi);
            }
            if (rivi.length() != maara) {
                fail("When method called with parameter "+Arrays.toString(taulukko) +", a line had " + rivi.length() + " stars, when it should have had " + maara+ " stars.");
            }
        }
    }
}
