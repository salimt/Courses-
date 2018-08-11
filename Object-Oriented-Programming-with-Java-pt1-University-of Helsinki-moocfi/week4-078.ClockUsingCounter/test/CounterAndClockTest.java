
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

public class CounterAndClockTest {

    @Rule
    public MockStdio io = new MockStdio();
    String luokanNimi = "BoundedCounter";
    Class counterLuokka;

    @Points("78.1")
    @Test
    public void noExceptionInMain() {
        Method m = null;
        try {
            String[] args = new String[0];
            m = ReflectionUtils.requireMethod(Main.class, "main", args.getClass());
        } catch (Throwable e) {
            e.printStackTrace();
            fail("main missing " + e.getMessage());
        }

        String virhe = "";
        if (m.toString().indexOf("thr") > 0) {
            virhe = m.toString().substring(+m.toString().indexOf("thr"));
        }

        assertFalse("Main-method should not throw an exception! Please remove from the definition: " + virhe, m.toString().contains("throws"));
    }
    Reflex.ClassRef<Object> klass;
    String klassName = "BoundedCounter";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    public void classPublic() {
        assertTrue("Class " + klassName + " should be public, define it as follows\npublic class Product {...\n}", klass.isPublic());
    }

    @Points("78.1")
    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, Integer> ctor = klass.constructor().taking(int.class).withNiceError();
        assertTrue("Add class " + klassName + " a constructor: public " + klassName + "(int upperLimit)", ctor.isPublic());
        ctor.invoke(4);
    }

    @Points("78.1")
    @Test
    public void hasMethodNext() throws Throwable {
        String metodi = "next";

        //Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object olio = newLaskuri(4);

        assertTrue("Add class " + klassName + " method public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nError caused by code BoundedCounter c = new (BoundedCounter4); "
                + "c.next()";

        klass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Points("78.1")
    @Test
    public void toStringRightAtStart() throws Throwable {
        int raja = 10;
        Object counter = newLaskuri(raja);
        String vastaus = toString(counter);

        String odotettu = "0";
        assertFalse("Add class " + luokanNimi + "methodi public String toString() as assignment asks", vastaus.contains("@"));
        assertTrue("created counter = new BoundedCounter(" + raja + "); "
                + "method toString does not work correctly, it produces " + vastaus, vastaus.contains(odotettu));
    }

    @Points("78.1")
    @Test
    public void counterValueIncreases() throws Throwable {
        int raja = 3;
        Object counter = newLaskuri(raja);

        next(counter);

        String vastaus = toString(counter);
        String odotettu = "1";

        assertTrue("When method next() is called, counter value should increase. Check code\n"
                + "counter = new BoundedCounter(" + raja + "); counter.next(); System.out.println(counter);", vastaus.contains(odotettu));

        next(counter);

        vastaus = toString(counter);
        odotettu = "2";

        assertTrue("When method next() is called, counter value should increase. Check code\n"
                + "counter = new BoundedCounter(" + raja + "); counter.next(); counter.next(); "
                + "System.out.println(counter);", vastaus.contains(odotettu));
    }

    @Points("78.1")
    @Test
    public void counterResetsAndGrowsAgain() throws Throwable {
        int raja = 2;
        Object counter = newLaskuri(raja);

        next(counter);
        next(counter);
        next(counter);

        String vastaus = toString(counter);
        String odotettu = "0";

        assertTrue("Counter should reset to zero when it grows beyond the limit"
                + ". Check the code: \n"
                + "counter = new BoundedCounter(" + raja + "); counter.next(); counter.next(); counter.next(); System.out.println(counter);", vastaus.contains(odotettu));

        next(counter);

        vastaus = toString(counter);
        odotettu = "1";

        assertTrue("Counter should start to grow again after it has become zero, Check following\n: "
                + "counter = new BoundedCounter(" + raja + "); counter.next(); counter.next(); counter.next(); counter.next(); System.out.println(counter);", vastaus.contains(odotettu));
    }

    @Points("78.1")
    @Test
    public void noExtraVariables1() {
        saniteettitarkastus();
    }

    /*
     * osa 2
     */
    @Points("78.2")
    @Test
    public void initialZeroAlussa() throws Throwable {
        int raja = 10;
        Object counter = newLaskuri(raja);
        String vastaus = toString(counter);

        String odotettu = "00";

        assertTrue("You should have a initial zero in the string representation, see assignment 78.2!\n "
                + "The code counter = new BoundedCounter(10); System.out.println(counter); prints " + vastaus, vastaus.contains(odotettu));
    }

    @Points("78.2")
    @Test
    public void initialZeroStays() throws Throwable {
        int raja = 3;
        Object counter = newLaskuri(raja);

        next(counter);
        String vastaus = toString(counter);
        String odotettu = "01";


        assertTrue("You should have a initial zero in the string representation, see assignment 78.2! "
                + "See what happens with: \n"
                + "counter = new BoundedCounter(" + raja + "); counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu));

        next(counter);
        vastaus = toString(counter);
        odotettu = "02";

        assertTrue("You should have a initial zero in the string representation, see assignment 78.2! "
                + "See what happens with: \n"
                + "counter = new BoundedCounter(" + raja + "); counter.next(); counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu));

        next(counter);
        vastaus = toString(counter);
        odotettu = "03";

        assertTrue("You should have a initial zero in the string representation, see assignment 78.2! "
                + "See what happens with: "
                + "counter = new BoundedCounter(" + raja + "); counter.next();  counter.next(); counter.next(); System.out.println(counter);" + vastaus, vastaus.contains(odotettu));

        next(counter);
        vastaus = toString(counter);
        odotettu = "00";

        assertTrue("You should have a initial zero in the string representation, see assignment 78.2! "
                + "See what happens with: "
                + "counter = new BoundedCounter(" + raja + "); counter.next();  counter.next(); counter.next(); counter.next(); System.out.println(counter);" + vastaus, vastaus.contains(odotettu));

    }

    @Points("78.2")
    @Test
    public void initialZeroOnlyWhenLessThanTen() throws Throwable {
        int raja = 12;
        Object counter = newLaskuri(raja);

        for (int i = 0; i < 10; i++) {
            next(counter);
        }

        String vastaus = toString(counter);
        String odotettu = "10";

        assertTrue("You should have a initial zero in the string representation only when counter value less than zero.  "
                + "See what happens with: "
                + "counter = new BoundedCounter(" + raja + "); //10 times the command counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu) && !vastaus.contains("0" + odotettu));

        next(counter);
        vastaus = toString(counter);
        odotettu = "11";

        assertTrue("You should have a initial zero in the string representation only when counter value less than zero.  "
                + "See what happens with: "
                + "counter = new BoundedCounter(" + raja + "); //11 times the command counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu) && !vastaus.contains("0" + odotettu));

        next(counter);
        vastaus = toString(counter);
        odotettu = "12";

        assertTrue("You should have a initial zero in the string representation only when counter value less than zero.  "
                + "See what happens with: "
                + "counter = new BoundedCounter(" + raja + "); //12 times the command counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu) && !vastaus.contains("0" + odotettu));


        next(counter);
        vastaus = toString(counter);
        odotettu = "00";

        assertTrue("The initial zero should be again in place after counter resets to zero. Check code\n "
                + "counter = new BoundedCounter(" + raja + "); //13 times the command counter.next();  System.out.println(counter);" + vastaus, vastaus.contains(odotettu) && !vastaus.contains("0" + odotettu));
    }

    @Points("78.2")
    @Test
    public void noExtraVariables2() {
        saniteettitarkastus();
    }

    /*
     * osa 3
     */
    @Points("78.3")
    @Test
    public void hasMethodGetValue() throws Throwable {
        String metodi = "getValue";

        Object olio = newLaskuri(4);

        assertTrue("Add class " + klassName + " the method public int " + metodi + "() ", klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nError caused by code BoundedCounter c = new BoundedCounter(4); "
                + "c.arvo()";

        klass.method(olio, metodi)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Points("78.3")
    @Test
    public void getValueAndToStringAgree() throws Throwable {
        int raja = 12;
        Object counter = newLaskuri(raja);

        for (int i = 0; i < 20; i++) {
            String vastausToString = toString(counter);
            String vastausArvo = etunollaa(arvo(counter));

            next(counter);

            assertTrue("Is getValue correctly implemented? check code\n"
                    + "counter = new BoundedCounter(" + raja + "); //" + i + " times counter.next();  System.out.println(counter.arvo());", vastausToString.contains("" + vastausArvo));
        }
    }

    @Points("78.3")
    @Test
    public void clockAdvances() {
        io.setSysIn("0\n0\n0\n");
        //Paaohjelma.main(new String[0]);
        main();
        String[] rivit = io.getSysOut().split("\n");
        int eka = etsiEka(rivit);

        assertFalse("Implement clock in main() as the assignment 78.3 defines. Remove all the extra code. "
                + "At first you should print 00:00", eka == -1);

        assertTrue("Implement clock in main() as the assignment 78.3 defines. Remove all the extra code. "
                + "At first you should print 00:00", rivit[eka].contains("00:00"));

        tarkistaEteneminen(rivit, eka);
    }

    /*
     * ei liikaa oliomuuttujia
     */
    @Points("78.1 78.2 78.3 78.4")
    @Test
    public void noExtraVariables() {
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
            assertTrue("The class " + klassName + " needs instance variables only for the value and the upper bound, remove the rest", var <= 3);

        }
    }

    @Points("78.3")
    @Test
    public void noExtraVariables3() {
        saniteettitarkastus();
    }

    /*
     * osa 4
     */
    @Points("78.4")
    @Test
    public void hasMethodSetValue() throws Throwable {
        String metodi = "setValue";

        Object olio = newLaskuri(4);

        assertTrue("Add class " + klassName + " method public void " + metodi + "(int newValue) ", klass.method(olio, metodi)
                .returningVoid().taking(int.class).isPublic());

        String v = "\nError caused by code BoundedCounter c = new BoundedCounter(4); "
                + "c.setValue(2)";

        klass.method(olio, metodi)
                .returningVoid().taking(int.class).withNiceError(v).invoke(2);
    }

    @Points("78.4")
    @Test
    public void valueIsSet() throws Throwable {
        int raja = 4;
        Object counter = newLaskuri(raja);

        int odotettu = 3;
        setValue(counter, odotettu);


        int arvo = arvo(counter);
        assertEquals("does method setValue(int newValue) work as expected?, Check the code: \n"
                + "counter = new BoundedCounter(" + raja + "); counter.setValue(3); System.out.println(counter);", arvo, odotettu);

        next(counter);
        odotettu = 4;
        arvo = arvo(counter);
        assertEquals("counter should advance normally after a call to setValue. Check the code\n "
                + "counter = new BoundedCounter(" + raja + "); counter.setValue(3); counter.next(); System.out.println(counter);", arvo, odotettu);

        next(counter);
        odotettu = 0;
        arvo = arvo(counter);
        assertEquals("counter pitäisi edetä ja nollautua normaalisti uuden arvon asettamisen jälkeen,Check the code "
                + "counter = new BoundedCounter(" + raja + "); counter.setValue(3); counter.next(); System.out.println(counter);", arvo, odotettu);

    }

    @Points("78.4")
    @Test
    public void wrongValueIsNotSet() throws Throwable {
        int raja = 4;
        Object counter = newLaskuri(raja);

        int odotettu = 0;
        setValue(counter, -1);

        int arvo = arvo(counter);
        assertEquals("Setting a negative value should have no effect. Check the code\n"
                + "counter = new BoundedCounter(" + raja + "); counter.setValue(-1); System.out.println(counter);", odotettu, arvo);

        setValue(counter, raja + 1);
        arvo = arvo(counter);
        assertEquals("Setting a too large number value should have no effect. Check the code\n"
                + "counter = new BoundedCounter(" + raja + "); counter.setValue(5); counter.next(); System.out.println(counter);", odotettu, arvo);
    }

    @Points("78.4")
    @Test
    public void exactClockAdvances() {
        io.setSysIn("50\n59\n23\n");
        //Paaohjelma.main(new String[0]);
        main();
        String[] rivit = io.getSysOut().split("\n");
        int eka = etsiEka(rivit);

        assertFalse("Code a clock as defined by the assignment 78.4 "
                + "With input 50 59 23 the first printed clock value should be 23:59:50", eka == -1);

        assertTrue("Code a clock as defined by the assignment 78.4 "
                + "With input 50 59 23 the first printed clock value should be 23:59:50. \nNow the following is printed " + rivit[eka], rivit[eka].contains("23:59:50"));

        tarkistaTarkanKellonEteneminen(rivit, eka);
    }

    @Points("78.4")
    @Test
    public void noExtraVariables4() {
        saniteettitarkastus();
    }

    private void saniteettitarkastus() throws SecurityException {
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
            assertTrue("The class " + klassName + " needs instance variables only for the value and the upper bound, remove the rest", var <= 3);
        }
    }

    /*
     * helpers
     */
    private void main() {
        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, "main", args.getClass());

            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) args);
        } catch (Throwable e) {
            fail("There was an exception: " + e.getMessage() + ". Do you have an infinite loop in your code?");
        }
    }

    private String toString(Object olio) throws Throwable {
        String metodi = "toString";

        String v = "\nError caused by code c = new BoundedCounter(4); c.toString()";

        return klass.method(olio, metodi)
                .returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private void next2(Object kortti) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(counterLuokka, "next");
        ReflectionUtils.invokeMethod(void.class, metodi, kortti);
    }

    private void next(Object kortti) throws Throwable {
        String metodi = "next";

        String v = "\nError caused by code c = new BoundedCounter(4); c.next()";

        klass.method(kortti, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private int arvo(Object kortti) throws Throwable {
        String metodi = "getValue";

        String v = "\nError caused by code c = new BoundedCounter(4); c.value()";

        return klass.method(kortti, metodi)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    private void setValue(Object kortti, int arvo) throws Throwable {
        String metodi = "setValue";

        String v = "\nError caused by code c = new BoundedCounter(4); c.setValue(" + arvo + ")";

        klass.method(kortti, metodi)
                .returningVoid().taking(int.class).withNiceError(v).invoke(arvo);
    }

    private Object newLaskuri(int alku) throws Throwable {
        Reflex.MethodRef1<Object, Object, Integer> ctor = klass.constructor().taking(int.class).withNiceError();
        assertTrue("Add class " + klassName + " a constructor: public " + klassName + "(int upperBound)", ctor.isPublic());
        return ctor.invoke(alku);
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }

    private String etunollaa(int arvo) {
        if (arvo > 9) {
            return "" + arvo;
        }

        return "0" + arvo;
    }

    private int etsiEka(String[] rivit) {
        for (int i = 0; i < rivit.length; i++) {
            if (rivit[i].matches(".*\\d\\d:\\d\\d")) {
                return i;
            }
        }

        return -1;
    }

    private void tarkistaEteneminen(String[] rivit, int alku) {
        try {
            String a = rivit[61 + alku + 1];
        } catch (ArrayIndexOutOfBoundsException a) {
            fail("Your clock should advance more that 60 seconds!");
        }

        int sekunninPaasta = 1;
        String pitaisi = "00:01";
        assertTrue("After " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

        sekunninPaasta = 60;
        pitaisi = "01:00";



        assertTrue("After " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

        sekunninPaasta = 61;
        pitaisi = "01:01";
        assertTrue("after " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

    }

    private void tarkistaTarkanKellonEteneminen(String[] rivit, int alku) {
        try {
            String a = rivit[15 + alku + 1];
        } catch (ArrayIndexOutOfBoundsException a) {
            fail("Your clock should advance more that 15 seconds!");
        }

        int sekunninPaasta = 1;
        String pitaisi = "23:59:51";
        assertTrue("if initially 23:59:50, after " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

        sekunninPaasta = 9;
        pitaisi = "23:59:59";
        assertTrue("if initially 23:59:50, after " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

        sekunninPaasta = 10;
        pitaisi = "00:00:00";
        assertTrue("if initially 23:59:50, after " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));

        sekunninPaasta = 11;
        pitaisi = "00:00:01";
        assertTrue("if initially 23:59:50, after " + sekunninPaasta + " seconds the time should be  " + pitaisi, rivit[sekunninPaasta + alku].contains(pitaisi));
    }
}
