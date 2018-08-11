
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef;
import fi.helsinki.cs.tmc.edutestutils.Reflex.MethodRef3;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

@Points("73")
public class ProductTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Product";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect("Product");
    }

    @Test
    public void classPublic() {
        assertTrue("class Product should be public, define it as follows\npublic class Product {...\n}", klass.isPublic());
    }

    @Test
    public void stConstructor() throws Throwable {
        Reflex.MethodRef3<Object, Object, String, Double, Integer> cc = klass.constructor().taking(String.class, double.class, int.class).withNiceError();
        assertTrue("Add  class " + klassName + " a constructor"
                + ": public " + klassName + "(String nameAtStart, double priceAtStart, int amountAtStart)", cc.isPublic());
        cc.invoke("Banana", 1.1, 13);
    }

    @Test
    public void noExtraVariables(){
        saniteettitarkastus();
    }
    
    @Test
    public void methodExists() throws Throwable {
        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect("Product");
        Object tuote = tuoteClass.constructor().taking(String.class, double.class, int.class).invoke("Banaani", 1.1, 13);

        try {
            tuoteClass.method(tuote, "printProduct")
                    .returningVoid()
                    .takingNoParams().invoke();
        } catch (AssertionError ae) {
            fail("Fail: " + ae + "\n add class Product method public void printProduct()");
        }

        assertTrue("Method printProduct should be public, i.e. defined as public void printProduct()", tuoteClass.method(tuote, "printProduct")
                .returningVoid()
                .takingNoParams().isPublic());
    }

    @Test
    public void testPrint1() throws Throwable {
        MockInOut mio = new MockInOut("");

        Object tuote = klass.constructor().taking(String.class, double.class, int.class).invoke("Apple", 0.1, 4);
        klass.method(tuote, "printProduct").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        mio.close();

        assertTrue("You did not print the product name in the method printProduct!", out.contains("Apple"));
        assertTrue("You did not print the product price in the method printProduct!", out.contains("0.1"));
        assertTrue("You did not print the product amount in the method printProduct!", out.contains("4"));
    }

    @Test
    public void testaaprint2() throws Throwable {

        MockInOut mio = new MockInOut("");

        Object tuote = klass.constructor().taking(String.class, double.class, int.class).invoke("Egg", 9000.0, 1);
        klass.method(tuote, "printProduct").returningVoid().takingNoParams().invoke();

        String out = mio.getOutput();

        assertTrue("You did not print the product name in the method printProduct!", out.contains("Egg"));
        assertTrue("You did not print the product price in the method printProduct!", out.contains("9000"));
        assertTrue("You did not print the product amount in the method printProduct!", out.contains("1"));
    }
    
    String luokanNimi = "Product";

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the instance variables should be private, please change " + kentta(field.toString()), field.toString().contains("private"));
        }

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + luokanNimi + ""
                    + " does not need other object variables than those for name, price and amount, "
                    + "remove others", var < 4);
        }
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "").replace("java.lang.", "");
    }
}
