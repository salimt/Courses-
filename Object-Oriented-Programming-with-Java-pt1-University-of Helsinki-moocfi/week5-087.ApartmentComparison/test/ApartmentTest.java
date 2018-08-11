
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ApartmentTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Apartment";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("87.1")
    @Test
    public void test1() throws Throwable {
        String method = "larger";

        Apartment olio = new Apartment(1, 20, 1000);
        Apartment olio2 = new Apartment(2, 30, 2000);

        assertTrue("add class " + klassName + " method public boolean " + method + "(Apartment otherApartment) ", klass.method(olio, method)
                .returning(boolean.class).taking(Apartment.class).isPublic());

        String v = "\nCode that caused the problemApartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.larger(b)";

        klass.method(olio, method)
                .returning(boolean.class).taking(Apartment.class).withNiceError(v).invoke(olio2);

    }

    @Points("87.1")
    @Test
    public void test2() throws Throwable {
        String method = "larger";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);

        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.larger(a2)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.larger(a1)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.larger(a3)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.larger(a2)",
                false, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.larger(a3)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.larger(a1)",
                true, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
    }

    @Points("87.2")
    @Test
    public void test3() throws Throwable {
        String method = "priceDifference";

        Apartment olio = new Apartment(1, 20, 1000);
        Apartment olio2 = new Apartment(2, 30, 2000);

        assertTrue("add class " + klassName + " method public int " + method + "(Apartment otherApartment) ", klass.method(olio, method)
                .returning(int.class).taking(Apartment.class).isPublic());

        String v = "\nCode that caused the problemApartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.priceDifference(b)";

        klass.method(olio, method)
                .returning(int.class).taking(Apartment.class).withNiceError(v).invoke(olio2);
    }

    @Points("87.2")
    @Test
    public void test4() throws Throwable {
        String method = "priceDifference";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);
        
        int res = klass.method(a1, method).returning(int.class).taking(Apartment.class).invoke(a2);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.priceDifference(a2)", 40000, res);
        
        res = klass.method(a2, method).returning(int.class).taking(Apartment.class).invoke(a1);        
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.priceDifference(a1)",40000, res);
        res = klass.method(a2, method).returning(int.class).taking(Apartment.class).invoke(a3);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.priceDifference(a3)",22500, res);
        res = klass.method(a3, method).returning(int.class).taking(Apartment.class).invoke(a2);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.priceDifference(a2)", 22500, res);
        res = klass.method(a1, method).returning(int.class).taking(Apartment.class).invoke(a3);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.priceDifference(a3)",17500, res);
        res = klass.method(a3, method).returning(int.class).taking(Apartment.class).invoke(a1);
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.priceDifference(a1)", 17500, res);
    }

    @Points("87.3")
    @Test
    public void test5() throws Throwable {
        String method = "moreExpensiveThan";

        Apartment olio = new Apartment(1, 20, 1000);
        Apartment olio2 = new Apartment(2, 30, 2000);

        assertTrue("add class " + klassName + " method public boolean " + method + "(Apartment otherApartment) ", klass.method(olio, method)
                .returning(boolean.class).taking(Apartment.class).isPublic());

        String v = "\nCode that caused the problemApartment a = new Apartment(1, 20, 1000); Apartment b = new Apartment(2, 30, 100); "
                + "a.moreExpensiveThan(b)";

        klass.method(olio, method)
                .returning(boolean.class).taking(Apartment.class).withNiceError(v).invoke(olio2);
    }

    @Points("87.3")
    @Test
    public void test6() throws Throwable {
        String method = "moreExpensiveThan";

        Apartment a1 = new Apartment(1, 20, 1000);
        Apartment a2 = new Apartment(2, 30, 2000);
        Apartment a3 = new Apartment(2, 25, 1500);

        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.moreExpensiveThan(a2)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.moreExpensiveThan(a1)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a2.moreExpensiveThan(a3)",
                true, klass.method(a2, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.moreExpensiveThan(a2)",
                false, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a2));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a1.moreExpensiveThan(a3)",
                false, klass.method(a1, method).returning(boolean.class).taking(Apartment.class).invoke(a3));
        assertEquals("Apartment a1 = new Apartment(1,20,1000); Apartment a2 = new Apartment(2,30,2000); Apartment a3 = new Apartment(2, 23, 1500);\n"
                + "a3.moreExpensiveThan(a1)",
                true, klass.method(a3, method).returning(boolean.class).taking(Apartment.class).invoke(a1));
    }
}
