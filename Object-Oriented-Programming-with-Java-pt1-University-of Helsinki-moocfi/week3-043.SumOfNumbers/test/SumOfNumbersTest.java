
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;

@Points("43")
public class SumOfNumbersTest {

    @Test
    public void test1() {
        assertEquals("Does not work with input 1,2,2,1", 6, SumOfNumbers.sum(1, 2, 2, 1));
    }

    @Test
    public void test2() {
        assertEquals("Does not work with input 1,2,-1,1", 3, SumOfNumbers.sum(1, 2, -1, 1));
    }

    @Test
    public void test3() {
        testaa("Does not work with input 1,2,3,4", 10, new int[]{1, 2, 3, 4});
    }
    
    @Test
    public void test4(){
        testaa("Does not work with input 0,0,0,2147483647",Integer.MAX_VALUE,new int[]{0,0,0,Integer.MAX_VALUE});
    }
    

    private void testaa(String viesti, int oletettu, int[] luvut) {
        assertEquals(viesti, oletettu, SumOfNumbers.sum(luvut[0], luvut[1], luvut[2], luvut[3]));
    }
}
