
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

@Points("71")
public class SmartCombiningTest {

    Method m;

    @Before
    public void haeMetodi() {
        try {
            m = ReflectionUtils.requireMethod(SmartCombining.class, "smartCombine", ArrayList.class, ArrayList.class);
        } catch (Throwable t) {
            fail("define method public static void smartCombine(ArrayList<Integer> list1, ArrayList<Integer> list2)! \nMore info: " + t);
        }
        assertTrue("method smartCombine should be static!", m.toString().contains("static"));
        assertTrue("method smartCombine should be void", m.toString().contains("void"));

    }

    public void tarkistaLista(ArrayList<Integer> origList1, ArrayList<Integer> origList2) {

        ArrayList<Integer> list1 = new ArrayList<Integer>(origList1);
        ArrayList<Integer> list2 = new ArrayList<Integer>(origList2);
        Object[] params = {list1, list2};

        try {
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, params);
        } catch (Throwable ex) {
            Logger.getLogger(SmartCombining.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Integer> yhdiste = new ArrayList<Integer>(list1);

        ArrayList<Integer> yKopy = new ArrayList<Integer>(yhdiste);
        Collections.sort(yKopy);
        for (int i=0; i<yKopy.size()-1; i++) {
            if (yKopy.get(i) == yKopy.get(i+1))
                 fail("Combination of lists " + origList1 + " and " + origList2 + " has twice the number " + yKopy.get(i) + ".");
        }

        for (int luku : list1) {
            if (!yhdiste.contains(luku)) {

                fail("Combination of lists " + origList1 + " and " + origList2 + " should contain the number " + luku + ".");
            }
            yhdiste.remove(Integer.valueOf(luku));
            while( list2.contains(luku)) {
                list2.remove(Integer.valueOf(luku));
            }
        }

        for (int luku : list2) {
            if (!yhdiste.contains(luku)) {

                fail("Combination of lists  " + origList1 + " and " + origList2 + " should contain the number " + luku + ".");
            }
            yhdiste.remove(Integer.valueOf(luku));
        }

        if (!yhdiste.isEmpty()) {
            fail("ListCombination of lists " + origList1 + " and " + origList2 + " should not contain following " + yhdiste + ".");
        }

    }

    public void tarkistaLista2(ArrayList<Integer> lista1, ArrayList<Integer> lista2) {
        ArrayList<Integer> origLista1 = new ArrayList<Integer>(lista1);
        ArrayList<Integer> origLista2 = new ArrayList<Integer>(lista2);
        Object[] params = {lista1, lista2};

        try {
            ReflectionUtils.invokeMethod(Void.TYPE, m, this, params);
        } catch (Throwable ex) {
            Logger.getLogger(SmartCombiningTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Integer> yhdiste = new ArrayList<Integer>(lista1);
        lista1 = new ArrayList<Integer>(origLista1);

        //Tarkistetaan että lista sisältää kaikki alkiot
        for (Integer i : lista1) {
            if (!yhdiste.contains(i)) {
                fail("Listojen " + origLista1 + " ja " + origLista2 + " joukkoyhdiste ei sisältänyt lista1:n alkiota " + i);
            }
        }
        for (Integer i : lista2) {
            if (!yhdiste.contains(i)) {
                fail("Listojen " + origLista1 + " ja " + origLista2 + " joukkoyhdiste ei sisältänyt lista2:n alkiota " + i);
            }
        }
        //Tarkistetaan ettei lista sisällä duplikaatteja
        HashSet<Integer> apusetti = new HashSet<Integer>();
        for (Integer i : yhdiste) {
            if (!apusetti.add(i)) {
                fail("Listojen " + origLista1 + " ja " + origLista2 + " joukkoyhdiste sisälsi alkion " + i + " useamman kuin yhden kerran.");
            }
        }
    }

    @Test
    public void smallLists1Test() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void smallLists2Test() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);
        lista2.add(11);

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void biggerListsTest() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(5);
        lista1.add(1);
        lista1.add(2);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();
        lista2.add(5);
        lista2.add(1);
        lista2.add(2);
        lista2.add(1);
        lista2.add(40);

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void differentSizeListsTest() {
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
        lista2.add(11);
        lista2.add(12);

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void secondListEmptyTest() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();
        lista1.add(10);
        lista1.add(11);
        lista1.add(12);
        lista1.add(13);

        ArrayList<Integer> lista2 = new ArrayList<Integer>();

        tarkistaLista(lista1, lista2);
    }

    @Test
    public void bothListsEmptyTest() {
        ArrayList<Integer> lista1 = new ArrayList<Integer>();

        ArrayList<Integer> lista2 = new ArrayList<Integer>();

        tarkistaLista(lista1, lista2);
    }
}
