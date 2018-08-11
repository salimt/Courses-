import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Points("82")
public class LotteryNumbersTest {
    
    public ArrayList<Integer> test() {
        LotteryNumbers lottoRivi;
        ArrayList<Integer> numerot;

        try {
            lottoRivi = new LotteryNumbers();
            numerot = lottoRivi.numbers();
        } catch (Throwable t) {
            fail("Something went wrong when creating a new LotteryNumbers object! More info: " + t);
            return null; // tyhmä java
        }

        assertEquals("The size of number list should be seven!", 7, numerot.size());

        Set<Integer> jaljellaOlevatNumerot = new HashSet<Integer>();
        for (int i = 1; i <= 39; i++) {
            jaljellaOlevatNumerot.add(i);
        }

        Set<Integer> arvotutNumerot = new HashSet<Integer>();
        for (int i : numerot) {
            assertTrue("A lottery number should belong to range 1-39. However, you returned number: " + i,
                    (i >= 1 && i <= 39));
            assertTrue("The method containsNumber() returns false, despite the drawn number was among the list of drawn numbers: " + i,
                    lottoRivi.containsNumber(i));
            assertTrue("The numbers returned contain multiple times a number: " + i,
                    arvotutNumerot.add(i));
            jaljellaOlevatNumerot.remove(i);
        }

        for (int i : jaljellaOlevatNumerot) {
            assertFalse("The method containsNumber() returns true, despite the drawn number was not among the list of drawn numbers: " + i,
                    lottoRivi.containsNumber(i));
        }

        return numerot;
    }

    @Test
    public void testOne() {
        test();
    }

    @Test
    public void testDrawNumbersCallRemovesOldNumbersAndDrawsNew() {
        LotteryNumbers lottoRivi;
        ArrayList<Integer> numerot;
        try {
            lottoRivi = new LotteryNumbers();
            numerot = lottoRivi.numbers();
        } catch (Throwable t) {
            fail("Something went wrong when creating a new LotteryNumbers object! More info: " + t);
            return;
        }
        String numerotString = numerot.toString();
        assertEquals("The size of number list should be seven!", 7, numerot.size());
        lottoRivi.drawNumbers();
        assertEquals("The size of number list should be seven after drawNumbers call!", 7, lottoRivi.numbers().size());
        assertFalse("New numbers should have been drawn with drawNumbers call! Numbers were " + numerotString,numerotString.equals(lottoRivi.numbers().toString()));
    }

    @Test
    public void testMany() {
        int[] arr = new int[40];
        for (int i = 0; i < 200; i++) {
            for (int x : test()) {
                arr[x]++;
            }
        }

        int montako=0;
        for (int i = 1; i <= 39; i++) {
            if (arr[i]>0) {
                montako++;
            }
        }

        assertEquals("200 lottery draws produced only " + montako +
                " different values! Not very random!", 39, montako);
    }
}
