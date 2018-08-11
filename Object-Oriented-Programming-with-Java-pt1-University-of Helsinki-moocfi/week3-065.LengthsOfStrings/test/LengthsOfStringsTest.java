import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;

@Points("65")
public class LengthsOfStringsTest {
    
    public ArrayList a(Object... i ) {
        ArrayList al = new ArrayList();
        for (Object j : i) {
            al.add(j);
        }
        return al;
    }
    
    public void test(ArrayList in, ArrayList out) {
        assertEquals("The lengths of strings in list"+in+" not correct.",out,LengthsOfStrings.lengths(in));
    }

    @Test
    public void testaaYhdenKokoinenLista() {
        test(a("Moi"),a(3));
        test(a("X"),a(1));
        test(a("Benvenuto!"),a(10));
    }

    @Test
    public void testaaIsompiLista() {
        test(a("X","Y","Z"),a(1,1,1));
        test(a("A","BB","CCC"),a(1,2,3));
        test(a("AAAA","CC","DDDDDDDDDD"),a(4,2,10));
    }

}
