
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

@Points("62")
public class RemoveLastTest {

    public ArrayList a(Object... i) {
        ArrayList al = new ArrayList();
        for (Object j : i) {
            al.add(j);
        }
        return al;
    }

    public void test(ArrayList in, ArrayList out) throws Exception {
        String s = in.toString();
        try {
            RemoveLast.removeLast(in);

        } catch (Exception e) {
            String v = "press button \"show backtrace\" and you find the cause of the problem by scrollong down to the "
                    + "\"Caused by\"";

            throw new Exception("when method removeLast called with parameter " + in + v, e);

        }
        assertEquals("Removing the last of list " + s + " dod not succeed",
                out,
                in);
    }

    @Test
    public void test1() throws Exception {
        test(a("XXX"), a());
        test(a("s"), a());
    }

    @Test
    public void test2() throws Exception {
        test(a("X", "Y", "Z"), a("X", "Y"));
        test(a("a", "b", "c", "d", "e", "f", "g", "h"),
                a("a", "b", "c", "d", "e", "f", "g"));
    }
}
