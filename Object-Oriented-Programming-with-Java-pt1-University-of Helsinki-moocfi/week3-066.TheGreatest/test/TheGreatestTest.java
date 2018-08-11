import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

@Points("66")
public class TheGreatestTest {

    public ArrayList<Integer> a(int... i) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int j : i) {
            al.add(j);
        }
        return al;
    }

    @Test
    public void test1() {
        testGreatest(1, 1);
        testGreatest(2, 2);
        testGreatest(3, 3);
    }

    @Test
    public void test2() {
        testGreatest(3, 1, 2, 3);
        testGreatest(3, 3, 2, 1);
        testGreatest(2, 2, 2, 2, 2, 2, 2, 2);
        testGreatest(3, -1, 1, -2, 2, -3, 3);
        testGreatest(-1, -2, -1, -3);
        testGreatest(-9000, -9000, -9001);
    }

    private void testGreatest(int greatest, int... numbers) {
        try {
            assertEquals("Greatest of the list " + Arrays.toString(numbers) + " not correct.", greatest,
                    TheGreatest.greatest(a(numbers)));
        } catch (Exception e) {
            fail("Error when calculating greatest of list " + Arrays.toString(numbers) + ".\n " + e.toString());
        }
    }
}
