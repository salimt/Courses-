import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("44")
public class LeastTest {
    @Test
    public void testNormal() {
        testaa(2, 7, 2);
    }

    @Test
    public void testNegative() {
        testaa(-5, 4, -5);
    }

    @Test
    public void testLarge() {
        testaa(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Test
    public void yetAnotherTest() {
        testaa(7, 2, 2);
    }

    private void testaa(int luku1, int luku2, int odotus) {
        assertEquals(getVirhe(luku1, luku2), odotus, Least.least(luku1, luku2) );
    }

    private String getVirhe(int luku1, int luku2) {
        return "Wrong answer when number1=" + luku1 + " and number 2=" + luku2;
    }
}
