import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.lang.reflect.Method;

@Points("61")
public class NumberOfItemsTest {
    
    Method m;

    @Before
    public void haeMetodi() {
        String v = "You have not implemented the method public static int countItems(ArrayList<String> list) !";
        try {
            m = ReflectionUtils.requireMethod(NumberOfItems.class, "countItems", ArrayList.class);

        } catch (Throwable t) {
            fail(v+" More info:\n"+t);
        }
        assertTrue(v, int.class == m.getReturnType());
    }

    public ArrayList a(Object... i ) {
        ArrayList al = new ArrayList();
        for (Object j : i) {
            al.add(j);
        }
        return al;
    }

    public void test(int len, ArrayList in) {
        String s = in.toString();
        try {
            assertEquals("Counting the number of items in list "+s+" went wrong!",
                         len,
                         (int) ReflectionUtils.invokeMethod(Integer.TYPE, m, null, in));
        } catch (Throwable t) {
            fail("Something went wrong when countItems was called to list "+s+". \nMore info: "+t);
        }
    }

    @Test
    public void test1() {
        test(1,a("XXX"));
        test(1,a("s"));
    }

    @Test
    public void test2() {
        test(0,a());
    }

    @Test
    public void test3() {
        test(3,a("X","Y","Z"));
        test(8,a("a","b","c","d","e","f","g","h"));
    }
}
