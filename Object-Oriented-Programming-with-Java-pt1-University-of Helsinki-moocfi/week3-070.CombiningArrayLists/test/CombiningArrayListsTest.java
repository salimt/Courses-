
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

@Points("70")
public class CombiningArrayListsTest {

    Method m;

    @Before
    public void haeMetodi() {
        try {
            m = ReflectionUtils.requireMethod(CombiningArrayLists.class, "combine", ArrayList.class, ArrayList.class);
        } catch (Throwable t) {
            fail("define method public static void combine(ArrayList<Integer> list1, ArrayList<Integer> list2)! More Info: " + t);
        }
        assertTrue("method combine should be static!", m.toString().contains("static"));
        assertTrue("method combine should be void", m.toString().contains("void"));

    }

    public void tarkistaLista(ArrayList<Integer> lista1, ArrayList<Integer> lista2) {

        //Debuggausta varten
        ArrayList<Integer> origLista1 = new ArrayList<Integer>(lista1);
        ArrayList<Integer> origLista2 = new ArrayList<Integer>(lista2);
        Object[] params = {lista1, lista2};
        try {
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, params);
        } catch (Throwable ex) {
            Logger.getLogger(CombiningArrayListsTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Integer> yhdiste = new ArrayList<Integer>(lista1);
        lista1 = new ArrayList<Integer>(origLista1);
  
        for (int luku : origLista1) {
            if ( !yhdiste.contains(luku)) {
                
                fail("Combination of lists " + origLista1 + " and " + origLista2 + " should contain number " + luku + ".");
            }
            yhdiste.remove(Integer.valueOf(luku));
            lista1.remove(Integer.valueOf(luku));
        }
        
        for (int luku : origLista2) {
            if ( !yhdiste.contains(luku)) {
                
                fail("Combination of lists " + origLista1 + " and " + origLista2 + " should contain number " + luku + ".");
            }
            yhdiste.remove(Integer.valueOf(luku));
            lista2.remove(Integer.valueOf(luku));
        }        
        
        if ( !yhdiste.isEmpty() ) {
            fail("Combination of " + origLista1 + " and " + origLista2 + " should not contain following " + yhdiste + ".");
        }
        
    }

    @Test
    public void test1() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);        

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void test2() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);
        lista2.add(6);
        lista2.add(7);

        tarkistaLista(lista1, lista2);
    }    
    
    @Test
    public void test3() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);
        lista1.add(12);
        lista1.add(13);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);
        lista2.add(6);
        lista2.add(7);
        lista2.add(8);
        lista2.add(9);
        lista2.add(10);
        lista2.add(7);
        lista2.add(8);

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void test4() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);
        lista1.add(12);
        lista1.add(13);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void test5() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(5);
        lista1.add(1);
        lista1.add(2);
        lista1.add(1);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);
        lista2.add(1);
        lista2.add(2);
        lista2.add(1);

        tarkistaLista(lista1, lista2);
    }    
    
    @Test
    public void test6() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();

        ArrayList<Integer> lista2 = new ArrayList<Integer>();

        tarkistaLista(lista1, lista2);
    }
    
    
}
