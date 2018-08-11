
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoneyTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Money";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("95.1 95.2 95.3")
    @Test
    public void test1() {
        saniteettitarkastus("Money", 2, " object variables for amount of euros and cents");
    }

    @Points("95.1")
    @Test
    public void test2() throws Throwable {
        String method = "plus";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Money r1 = new Money(1, 0);

        assertTrue("In the class " + klassName + ", add the method public Money " + method + "(Money added) ",
                klass.method(r1, method).returning(Money.class).taking(Money.class).isPublic());

        Money r2 = new Money(2, 0);

        String v = "Money r1 = new Money(1, 0);\n"
                + "Money r2 = new Money(2, 0);\n"
                + "Money r3 = r1.plus(r2);\n";

        Money r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertFalse("When code " + v + " executed, the returned object was null", r3 == null);

        assertEquals(v + " r3.euros();\n", 3, r3.euros());
        assertEquals(v + " r3.cents();\n", 0, r3.cents());
        assertEquals(v + " System.out.println(r3)", "3.00e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "1.00e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

        r1 = new Money(7, 50);
        r2 = new Money(1, 60);

        v = "Money r1 = new Money(7, 50);\n"
                + "Money r2 = new Money(1, 60);\n"
                + "Money r3 = r1.plus(r2);\n";

        r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertEquals(v + " System.out.println(r3)", "9.10e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "7.50e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "1.60e", r2.toString());

    }

    @Points("95.2")
    @Test
    public void test3() throws Throwable {
        String method = "less";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Money r1 = new Money(1, 0);

        assertTrue("In the class " + klassName + ", add the method public boolean " + method + "(Money compared) ",
                klass.method(r1, method).returning(boolean.class).taking(Money.class).isPublic());

        Money r2 = new Money(1, 5);
        Money r3 = new Money(-3, 5);
        Money r4 = new Money(2, 0);

        String v = "When\n"
                + "r1 = new Money(1, 0);\n"
                + "r2 = new Money(1, 50);\n"
                + "r3 = new Money(-3,5);\n"
                + "r4 = new Money(2,0);\n";

        check(r1, r2, true, v + "r1.less(r2);\n", klass);
        check(r2, r1, false, v + "r2.less(r1);\n", klass);

        check(r1, r3, false, v + "r1.less(r3);\n", klass);
        check(r3, r1, true, v + "r3.less(r1);\n", klass);

        check(r2, r3, false, v + "r2.less(r3);\n", klass);
        check(r3, r2, true, v + "r3.less(r2);\n", klass);

        check(r2, r4, true, v + "r2.less(r4);\n", klass);
        check(r4, r2, false, v + "r4.less(r2);\n", klass);

        check(r3, r4, true, v + "r3.less(r4);\n", klass);
        check(r4, r3, false, v + "r4.less(r3);\n", klass);
    }

    private void check(Money e, Money t, boolean res, String v, ClassRef<Object> klass) throws Throwable {

        assertEquals(v, res, klass.method(e, "less").returning(boolean.class).taking(Money.class).invoke(t));
    }

    @Points("95.3")
    @Test
    public void test4() throws Throwable {
        String method = "minus";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Money r1 = new Money(10, 0);

        assertTrue("In the class " + klassName + ", add the method public Money " + method + "(Money decreased) ", klass.method(r1, method)
                .returning(Money.class).taking(Money.class).isPublic());

        Money r2 = new Money(2, 0);

        String v = "Money r1 = new Money(10, 0);\n"
                + "Money r2 = new Money(2, 0);\n"
                + "Money r3 = r1.minus(r2);\n";

        Money r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertFalse("When executed " + v + " executed, the returned object was null", r3 == null);

        assertEquals(v + " r3.euros();\n", 8, r3.euros());
        assertEquals(v + " r3.cents();\n", 0, r3.cents());
        assertEquals(v + " System.out.println(r3)", "8.00e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "10.00e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

        r2 = new Money(7, 40);

        v = "Money r1 = new Money(10, 0);\n"
                + "Money r2 = new Money(7, 40);\n"
                + "Money r3 = r1.minus(r2);\n";

        r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertEquals(v + " r3.euros();\n", 2, r3.euros());
        assertEquals(v + " r3.cents();\n", 60, r3.cents());
        assertEquals(v + " System.out.println(r3)", "2.60e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "10.00e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "7.40e", r2.toString());

        r1 = new Money(1, 00);
        r2 = new Money(2, 00);

        v = "Remember that money should not have negative value. Check the code \n"
                + "Money r1 = new Money(1, 0);\n"
                + "Money r2 = new Money(2, 0);\n"
                + "Money r3 = r1.minus(r2);\n";

        r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertEquals(v + "r3.euros();\n", 0, r3.euros());
        assertEquals(v + "r3.cents();\n", 0, r3.cents());
        assertEquals(v + "System.out.println(r3);\n", "0.00e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "1.00e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());


        r1 = new Money(1, 50);
        r2 = new Money(2, 00);

        v = "Note, the value of money should not be negavite: check code \nMoney r1 = new Money(1, 50);\n"
                + "Money r2 = new Money(2, 0);\n"
                + "Money r3 = r1.minus(r2);\n";

        r3 = klass.method(r1, method).returning(Money.class).taking(Money.class).
                withNiceError("Code that caused the problem" + v).invoke(r2);

        assertEquals(v + "r3.euros();\n", 0, r3.euros());
        assertEquals(v + "r3.cents();\n", 0, r3.cents());
        assertEquals(v + "System.out.println(r3);\n", "0.00e", r3.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r1)", "1.50e", r1.toString());
        assertEquals("The original objects should not change:\n" + v + " System.out.println(r2)", "2.00e", r2.toString());

    }

     private void saniteettitarkastus(String luokanNimi, int muuttujia, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString(), luokanNimi), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString(), luokanNimi), field.toString().contains("private"));
        }


        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Class " + luokanNimi + " only needs " + m + ", remove the rest", var <= muuttujia);
        }

    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "");
    }
}
