
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import org.junit.*;
import static org.junit.Assert.*;

@Points("89")
public class ClockTest {

    String klassName = "Clock";

    @Test
    public void test1(){
        saniteettitarkastus(klassName,3, ""
                + "as instance variables only the three BoundedCounters");
    }      
    
    @Test
    public void test2() throws Throwable {
        Reflex.ClassRef<Object> klass;

        klass = Reflex.reflect(klassName);

        assertTrue("The class " + klassName + " should be public, define it as follows"
                + "\npublic class " + klassName + " {...\n}", klass.isPublic());

        Reflex.MethodRef3<Object, Object, Integer, Integer, Integer> ctor = klass.constructor().taking(int.class, int.class, int.class).withNiceError();
        assertTrue("Add the class " + klassName + " constructor: public " + klassName + "(int hoursAtBeginning, int minutesAtBeginning, int secondsAtBeginning)", ctor.isPublic());
        Object olio = ctor.invoke(23, 59, 55);

        String v = "clock k = new clock(23,29,55); k.tick(); System.out.print(k)";
        klass.method(olio, "tick").returningVoid().takingNoParams().withNiceError(v).invoke();
        klass.method(olio, "toString").returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    public void test3() {
        Clock kello = new Clock(23, 59, 58);
        String tulos;

        String a = "23:59:58";
        int et = 0;

        tulos = "23:59:58";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " "
                + "ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "23:59:59";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "00:00:00";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "00:00:01";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));
    }

    @Test
    public void test4() {
        Clock kello = new Clock(00, 17, 58);
        String tulos;
        String a = "00:17:58";
        int et = 0;


        tulos = "00:17:58";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "00:17:59";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "00:18:00";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "00:18:01";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));
    }

    @Test
    public void test5() {
        Clock kello = new Clock(11, 59, 59);
        String tulos;
        String a = "11:59:59";
        int et = 0;

        tulos = "11:59:59";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "12:00:00";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "12:00:01";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));

        kello.tick();
        et++;

        tulos = "12:00:02";
        assertTrue("When the clock was at the beginning, after  " + a + ", " + et + " ticks the expected time is " + tulos
                + ", but the toString was: " + kello, kello.toString().contains(tulos));
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
        return toString.replace(klassName + ".", "").replace("java.lang.", "");
    }
}
