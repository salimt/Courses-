
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Random;
import org.powermock.modules.junit4.rule.PowerMockRule;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.easymock.EasyMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.powermock.api.easymock.PowerMock.*;
import static org.easymock.EasyMock.*;

@Points("80")
@PrepareForTest({Dice.class, Main.class})
public class DiceTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void allNumbersProvided() {
        int sarmat = new Random().nextInt(15) + 2;
        Dice n = new Dice(sarmat);

        int luku = n.roll();
        assertTrue("When Dice n = new Dice(" + sarmat + "); and calling h.roll(), "
                + "the returned value was " + luku + ", result should have been in range 1..." + sarmat, luku > 0 && luku <= sarmat);
        int i = 0;
        while (true) {
            int uusi = n.roll();
            if (uusi != luku) {
                break;
            }
            assertTrue("When Dice n = new Dice(" + sarmat + "); and calling h.roll(), "
                    + "the returned value was " + luku + ", result should have been in range  1..." + sarmat, luku > 0 && luku <= sarmat);

            i++;
            if (i == 20) {
                fail("Your dice returns always the same value. Check the following code:\n"
                        + "Dice d = new Dice(6); \n"
                        + "System.out.println(d.roll());\n"
                        + "System.out.println(d.roll());\n"
                        + "System.out.println(d.roll());\n"
                        + "System.out.println(d.roll());\n"
                        + "System.out.println(d.roll());\n"
                        + "System.out.println(d.roll());\n"
                        );
            }
        }
        int[] luvut = new int[sarmat+1];
        for (int j = 0; j < 1000; j++) {
            luku = n.roll();
            assertTrue("When Dicen = new Dice(" + sarmat + "); and calling h.roll(), the returned "
                    + "value was " + luku + ", result should have been in range  1..." + sarmat, luku > 0 && luku <= sarmat);
            luvut[ luku]++;
        }

        for (int j = 1; j < luvut.length; j++) {
            assertTrue("Does the dice work correctly? When Dice n = new Dice(" + sarmat + "); "
                    + "with 1000 rolls the we got number " + j + " only " + luvut[j] + " times.", luvut[j] > 10);
        }
    }
}
