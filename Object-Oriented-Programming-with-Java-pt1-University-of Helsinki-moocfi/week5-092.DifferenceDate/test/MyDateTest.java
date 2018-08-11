
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class MyDateTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "MyDate";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("92.1")
    @Test
    public void test1() {
        saniteettitarkastus("MyDate", 3, "new object variables, those are not needed ");
    }

    public void test2() throws Throwable {
        String method = "differenceInYears";

        MyDate olio = new MyDate(1, 1, 2011);
        MyDate olio2 = new MyDate(1, 1, 2009);

        assertTrue("Add class " + klassName + " method public int " + method + "(MyDate verrattava) ", klass.method(olio, method)
                .returning(int.class).taking(MyDate.class).isPublic());

        String v = "\nCode that caused the problemMyDate p = new MyDate(1, 1, 2011); MyDate p2 = new MyDate(1, 1, 2009); "
                + "p.differenceInYears(p2);";

        klass.method(olio, method)
                .returning(int.class).taking(MyDate.class).withNiceError(v).invoke(olio2);

    }

    @Points("92.1")
    @Test
    public void test3() {
        MyDate kolm = new MyDate(3, 7, 2011);
        MyDate toka = new MyDate(2, 6, 2010);
        MyDate eka = new MyDate(1, 5, 2000);

        tarkasta(toka, eka, 10);
        tarkasta(toka, eka, 10);
        tarkasta(kolm, toka, 1);
        tarkasta(kolm, kolm, 0);
    }

    /*
     *
     */
    @Points("92.2")
    @Test
    public void test4() {
        saniteettitarkastus("MyDate", 3, "new object variables, those are not needed ");

    }
    Random arpa = new Random();

    @Points("92.2")
    @Test
    public void test5() {
        MyDate kolm = new MyDate(3, 7, 2011);
        MyDate toka = new MyDate(2, 6, 2010);
        MyDate eka = new MyDate(1, 5, 2000);

        tarkasta(toka, eka, 10);
        tarkasta(toka, eka, 10);
        tarkasta(kolm, toka, 1);
    }
    
    @Points("92.2")
    @Test
    public void test6() {
        MyDate toka = new MyDate(10, 2, 2012);
        MyDate eka = new MyDate(9, 5, 2011);

        tarkasta(toka, eka, 0);

        toka = new MyDate(9, 2, 2012);
        eka = new MyDate(10, 5, 2011);

        tarkasta(toka, eka, 0);

        for (int i = 0; i < 4; i++) {
            int ensin = 1990 + arpa.nextInt(10);
            int vuodenPaasta = 1 + arpa.nextInt(10);

            toka = new MyDate(10, 2, ensin + vuodenPaasta);
            eka = new MyDate(9, 5, ensin);
            tarkasta(toka, eka, vuodenPaasta - 1);
        }
    }

    @Points("92.2")
    @Test
    public void test7() {
        MyDate toka = new MyDate(9, 3, 2012);
        MyDate eka = new MyDate(10, 3, 2011);

        tarkasta(toka, eka, 0);

        toka = new MyDate(10, 3, 2012);
        eka = new MyDate(10, 3, 2011);

        tarkasta(toka, eka, 1);

        for (int i = 0; i < 4; i++) {
            int ensin = 1990 + arpa.nextInt(10);
            int vuodenPaasta = 1 + arpa.nextInt(10);

            toka = new MyDate(10, 2, ensin + vuodenPaasta);
            eka = new MyDate(9, 5, ensin);
            tarkasta(toka, eka, vuodenPaasta - 1);
        }


        for (int i = 0; i < 4; i++) {
            int ensin = 1990 + arpa.nextInt(10);
            int vuodenPaasta = 1 + arpa.nextInt(10);

            int d = arpa.nextInt(20) + 1;
            toka = new MyDate(d, 5, ensin + vuodenPaasta);
            eka = new MyDate(10, 5, ensin);
            int kompensaatio = d < 10 ? 1 : 0;
            tarkasta(toka, eka, vuodenPaasta - kompensaatio);
        }

    }

    /*
     *
     */
    @Points("92.3")
    @Test
    public void test8() {
        saniteettitarkastus("MyDate", 3, "new object variables, those are not needed ");
    }

    @Points("92.3")
    @Test
    public void test9() {
        MyDate toka = new MyDate(9, 3, 2012);
        MyDate eka = new MyDate(10, 3, 2011);

        tarkasta2(toka, eka, 0);

        toka = new MyDate(10, 3, 2012);
        eka = new MyDate(10, 3, 2011);

        tarkasta2(toka, eka, 1);

        for (int i = 0; i < 4; i++) {
            int ensin = 1990 + arpa.nextInt(10);
            int vuodenPaasta = 1 + arpa.nextInt(10);

            toka = new MyDate(10, 2, ensin + vuodenPaasta);
            eka = new MyDate(9, 5, ensin);
            tarkasta2(toka, eka, vuodenPaasta - 1);
        }


        for (int i = 0; i < 4; i++) {
            int ensin = 1990 + arpa.nextInt(10);
            int vuodenPaasta = 1 + arpa.nextInt(10);

            int d = arpa.nextInt(20) + 1;
            toka = new MyDate(d, 5, ensin + vuodenPaasta);
            eka = new MyDate(10, 5, ensin);
            int kompensaatio = d < 10 ? 1 : 0;
            tarkasta2(toka, eka, vuodenPaasta - kompensaatio);
        }

    }
    /*
     *
     */

    private int erotus(Object pvm1, Object pvm2) {
        String methodNimi = "differenceInYears";
        try {
            Method method = ReflectionUtils.requireMethod(MyDate.class, methodNimi, MyDate.class);
            return ReflectionUtils.invokeMethod(int.class, method, pvm1, pvm2);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("Add class MyDate method public int differenceInYears(MyDate compared)");
        }
        return -999999;
    }


    private void saniteettitarkastus(String luokanNimi, int muuttujia, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString()), field.toString().contains("private"));
        }


        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Class " + klassName + " only needs " + m + ", remove the rest", var <= muuttujia);
        }

    }     
    
    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }

    private void tarkasta(MyDate toka, MyDate eka, int odotus) {
        assertEquals("Difference of dates " + toka + " and " + eka + " not right", odotus, erotus(toka, eka));
    }

    private void tarkasta2(MyDate toka, MyDate eka, int erotus) {
        tarkasta(toka, eka, erotus);
        tarkasta(eka, toka, erotus);
    }
}
