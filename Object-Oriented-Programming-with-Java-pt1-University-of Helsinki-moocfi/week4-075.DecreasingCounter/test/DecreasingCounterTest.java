
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class DecreasingCounterTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Points("75.1")
    @Test
    public void valueDecreases() {
        DecreasingCounter counter = new DecreasingCounter(10);
        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Value of the counter c = new DecreacingCounter(10); does not get printed correctly. Remember that method printValue"
                + "should not be altered!", out.contains("value: 10"));
        int vanhaTuloste = out.length();
        counter.decrease();
        counter.printValue();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("after method decrease() is called, the value of counter should be one less that at start \n"
                + "Check the following code\n "
                + "counter = new DecreasingCounter(10); counter.decrease(); counter.printValue();", out.contains("value: 9"));
        vanhaTuloste = out.length();
        counter.decrease();
        counter.printValue();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("after method decrease() is called, the value of counter should be two less that at start  \n"
                + "Check the following code\n "
                + "counter = new DecreasingCounter(10); counter.decrease(); counter.decrease(); counter.printValue();", out.contains("value: 8"));
    }

    @Points("75.2")
    @Test
    public void arvoEiVaheneAlleNollan() {
        DecreasingCounter counter = new DecreasingCounter(2);
        counter.decrease();
        counter.decrease();
        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Counter value should drop by one for each decrease() call. \nCheck the following code\n "
                + "counter = new DecreasingCounter(2); counter.decrease(); counter.decrease(); counter.printValue();", out.contains("value: 0"));

        int vanhaTuloste = out.length();
        counter.decrease();
        counter.printValue();
        
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Counter value should not drop below zero. \nCheck the following code\n "
                + "counter = new DecreasingCounter(2); counter.decrease(); counter.decrease(); counter.decrease(); counter.printValue();", out.contains("value: 0"));


        vanhaTuloste = out.length();
        counter.decrease();
        counter.printValue();
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Counter value should not drop below zero.  \nCheck the following code\n "
                + "counter = new DecreasingCounter(2); counter.decrease();  counter.decrease(); counter.decrease(); counter.decrease(); counter.printValue();", out.contains("value: 0"));
    }

    @Points("75.3")
    @Test
    public void hasMethodReset() throws Throwable {
        String klassName = "DecreasingCounter";

        String metodi = "reset";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("Class " + klassName + " should be added method: public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nCode that caused the fault: counter = new DecreasingCounter(2); counter.reset();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }
    
    @Points("75.3")
    @Test
    public void resetWorks() throws Exception {
        String metodinNimi = "reset";

        DecreasingCounter counter = new DecreasingCounter(2);
        Method metodi = ReflectionUtils.requireMethod(DecreasingCounter.class, metodinNimi);
        metodi.invoke(counter);

        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Method reset() should set counter to value zero. \nCheck the following code\n "
                + "counter = new DecreasingCounter(2); counter.reset(); counter.printValue();", out.contains("value: 0"));

        int vanhaTuloste = out.length();
        counter.decrease();
        counter.printValue();
        out = io.getSysOut().substring(vanhaTuloste);

        assertTrue("Counter value should not drop below zero \nCheck the following code\n "
                + "counter = new DecreasingCounter(2); counter.reset(); counter.decrease(); counter.printValue();", out.contains("value: 0"));
    }

    @Points("75.4")
    @Test
    public void hasMethodSetInitial() throws Throwable {
        String klassName = "DecreasingCounter";

        String metodi = "setInitial";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().taking(int.class).invoke(4);

        assertTrue("Class " + klassName + " should be added method: public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nCode that caused the fault: counter = new DecreasingCounter(4);  counter.setInitial();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }    
    

    @Points("75.4")
    @Test
    public void setInitialWorks() throws Exception {
        DecreasingCounter counter = new DecreasingCounter(2);

        String metodinNimi = "reset";
        Method metodi = ReflectionUtils.requireMethod(DecreasingCounter.class, metodinNimi);
        metodi.invoke(counter);

        metodinNimi = "setInitial";
        metodi = ReflectionUtils.requireMethod(DecreasingCounter.class, metodinNimi);
        metodi.invoke(counter);

        counter.printValue();
        String out = io.getSysOut();
        assertTrue("Method setInitial() whould return counter to it's initial value\n"
                + "Check the following code\n "
                + "counter = new DecreasingCounter(2); counter.reset(); counter.setInitial(); counter.printValue();", out.contains("value: 2"));

        int vanhaTuloste = out.length();
        counter.decrease();

        metodinNimi = "setInitial";
        metodi = ReflectionUtils.requireMethod(DecreasingCounter.class, metodinNimi);
        metodi.invoke(counter);

        counter.printValue();
        out = io.getSysOut().substring(vanhaTuloste);
        assertTrue("Method setInitial() whould return counter to it's initial value\n"
                + "Check the following code\n "
                + "counter = new DecreasingCounter(2); counter.reset(); counter.setInitial(); counter.decrease(); counter.setInitial(); counter.printValue();", out.contains("value: 2"));

    }

    @Points("75.1 75.2 75.3 75.4")
    @Test
    public void noStatic() {
        Class luokka = DecreasingCounter.class;
        String luokanNimi = luokka.toString().replace("class ", "");

        for (Field f : luokka.getDeclaredFields()) {
            assertFalse("You have added class " + luokanNimi + " a static variable " 
                    + f.toString().replace(luokanNimi + ".", "") + 
                    " you do not need it, so remove it!", f.toString().contains("static"));
        }
    }
}
