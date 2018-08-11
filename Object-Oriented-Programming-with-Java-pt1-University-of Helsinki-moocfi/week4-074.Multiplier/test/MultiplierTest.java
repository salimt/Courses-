
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("74")
public class MultiplierTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Multiplier";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classPublic() {
        assertTrue("class " + klassName + " should be public, define it as follows\npublic class Product {...\n}", klass.isPublic());
    }

    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, Integer> ctor = klass.constructor().taking(int.class).withNiceError();
        assertTrue("Add  class " + klassName + " a constructor: public " + klassName + "(int number)", ctor.isPublic());
        ctor.invoke(4);
    }

    @Test
    public void hasMethod() throws Throwable {
        String metodi = "multiply";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("Add class " + klassName + " a method public int " + metodi + "(int otherNumber) ", tuoteClass.method(olio, metodi)
                .returning(int.class).taking(int.class).isPublic());

        String v = "\nFault caused by the code Multiplier k = new Multiplier(4); k.multiply(2);";

        tuoteClass.method(olio, metodi)
                .returning(int.class).taking(int.class).withNiceError(v).invoke(2);

    }

    @Test
    public void testMultiplyMethod() throws Throwable {
        String metodi = "multiply";

        Object o1 = klass.constructor().taking(int.class).invoke(4);
        Object o2 = klass.constructor().taking(int.class).invoke(1);
        Object o3 = klass.constructor().taking(int.class).invoke(7);

        int out = klass.method(o1, metodi).returning(int.class).taking(int.class).invoke(2);
        int out2 = klass.method(o2, metodi).returning(int.class).taking(int.class).invoke(3);
        int out3 = klass.method(o3, metodi).returning(int.class).taking(int.class).invoke(8);

        assertEquals("You returned a wrong value when the method multiply(2) was called to object Multiplier(4)", 8, out);
        assertEquals("You returned a wrong value when the method multiply(3) was called to object Multiplier(1)", 3, out2);
        assertEquals("You returned a wrong value when the method multiply(8) was called to object Multiplier(7)", 56, out3);
    }

    @Test
    public void noExtraVariables() {
        saniteettitarkastus(1, "saving the value multiplied");
    }

    private void saniteettitarkastus(int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

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
            String s = n>1?"s":"";

            assertTrue("The class " + klassName + " needs instance variable"+s+" only for " + m + ", remove the rest", var <= n);
        }
    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
