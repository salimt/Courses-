
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

public class PhonebookTest {

    @Rule
    public MockStdio stdio = new MockStdio();
    Reflex.ClassRef<Object> klassH;
    String klassNameH = "Person";
    Reflex.ClassRef<Object> klassP;
    String klassNameP = "Phonebook";

    @Before
    public void haeLuokka() {
        klassH = Reflex.reflect(klassNameH);
    }

    @Points("94.1")
    @Test
    public void test1() {
        assertTrue("Class " + klassNameH + " should be defined as\npublic class " + klassNameH + " {...\n}", klassH.isPublic());
    }

    @Points("94.1")
    @Test
    public void test2() {
        saniteettitarkastus("Person", 2, " object variables for name and phone number");
    }

    @Points("94.1")
    @Test
    public void test3() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, String> ctor = klassH.constructor().taking(String.class, String.class).withNiceError();
        assertTrue("Add the class " + klassNameH + " constructor: public " + klassNameH + "(String name, String number)", ctor.isPublic());
        ctor.invoke("Pekka", "040-123654");
    }

    @Points("94.1")
    @Test
    public void test4() throws Throwable {
        Object h = luoPerson("Pekka", "040-123654");

        assertFalse("In the class Person, define the method toString", h.toString().contains("@"));

        assertTrue("Ensure that Person toString-method works as the assignment defines\n"
                + "h = new Person(\"Pekka\", \"040-123654\"); System.out.print(h) prints now:\n" + h.toString(), h.toString().contains("Pekka") && h.toString().contains("number") && h.toString().contains("040-123654"));
    }

    @Points("94.1")
    @Test
    public void test5() throws Throwable {
        String method = "getName";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameH);

        Object olio = luoPerson("Pekka", "040-123654");

        assertTrue("In the class " + klassNameH + ", add the method public String " + method + "() ", klass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        String v = "\nCode that caused the problem h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.getName();";

        assertEquals("Check the code h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.getName();", "Pekka", klass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luoPerson("Jukka", "040-123654");

        assertTrue("In the class " + klassNameH + ", add the method public String " + method + "() ", klass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        v = "\nCode that caused the problem h = new Person(\"Jukka\", \"040-123654\"); "
                + "h.getName();";

        assertEquals("Check the code h = new Person(\"Jukka\", \"040-123654\"); "
                + "h.getName();", "Jukka", klass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Points("94.1")
    @Test
    public void test6() throws Throwable {
        String method = "getNumber";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameH);

        Object olio = luoPerson("Pekka", "040-123654");

        assertTrue("In the class " + klassNameH + ", add the method public String " + method + "() ", klass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        String v = "\nCode that caused the problem h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.getNumber();";

        assertEquals("Check the code h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.getNumber();", "040-123654", klass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luoPerson("Pekka", "040-333444");

        assertTrue("In the class " + klassNameH + ", add the method public String " + method + "() ", klass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        v = "\nCode that caused the problem h = new Person(\"Pekka\", \"040-333444\"); "
                + "h.getNumber();";

        assertEquals("Check the code h = new Person(\"Pekka\", \"040-333444\"); "
                + "h.getNumber();", "040-333444", klass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Points("94.1")
    @Test
    public void test7() throws Throwable {
        String method = "changeNumber";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameH);

        Object olio = luoPerson("Pekka", "040-123654");

        assertTrue("In the class " + klassNameH + ", add the method public void " + method + "(String newNumber) ", klass.method(olio, method)
                .returningVoid().taking(String.class).isPublic());

        String v = "\nCode that caused the problem h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.changeNumber(\"050-444666\");";

        klass.method(olio, method)
                .returningVoid().taking(String.class).withNiceError(v).invoke("050-444666");

        v = "Does the method changeNumber work correctly?\n"
                + "h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.changeNumber(\"050-444666\"); h.getNumber()";

        assertEquals(v, "050-444666", klass.method(olio, "getNumber")
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        v = "Does the method changeNumber work correctly? Check the code\n"
                + "h = new Person(\"Pekka\", \"040-123654\"); "
                + "h.changeNumber(\"050-444666\"); System.out.println(h). Output was:\n";

        assertTrue(v + olio.toString(), olio.toString().contains("050-444666"));

    }

    /*
     * 
     */
    @Points("94.2")
    @Test
    public void test8() {
        klassP = Reflex.reflect(klassNameP);
        assertTrue("The class " + klassNameP + " should be defined\npublic class " + klassNameP + " {...\n}", klassP.isPublic());
    }

    @Points("94.2")
    @Test
    public void test9() {
        saniteettitarkastus("Phonebook", 1, " object variable of type ArrayList<Person>");
    }

    @Points("94.2")
    @Test
    public void test10() {
        String klassName = "Phonebook";
        Field[] kentat = ReflectionUtils.findClass(klassName).getDeclaredFields();

        boolean on = false;

        for (Field field : kentat) {
            if (field.toString().contains("ArrayList")) {
                on = true;
            }
        }

        assertTrue("In the class " + klassName + ", add an object variable of the type ArrayList<Person>", on);
    }

    @Points("94.2")
    @Test
    public void test11() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        Reflex.MethodRef0<Object, Object> ctor = klassP.constructor().takingNoParams().withNiceError();
        assertTrue("The class " + klassNameP + " should have the constructor: public " + klassNameP + "()", ctor.isPublic());
        ctor.invoke();
    }

    @Points("94.2")
    @Test
    public void test12() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        String method = "add";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameP);

        Object olio = klassP.constructor().takingNoParams().invoke();

        assertTrue("In the class " + klassNameP + " method public void " + method + "(String name, String number)", klass.method(olio, method)
                .returningVoid().taking(String.class, String.class).isPublic());

        String v = "\nCode that caused the problem p = new Phonebook(); p.add(\"Pekka\", \"040-123654\");";

        klass.method(olio, method)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Pekka", "040-123654");

        Field lista = ReflectionUtils.findClass(klassNameP).getDeclaredFields()[0];
        lista.setAccessible(true);
        ArrayList hlot = (ArrayList) lista.get(olio);

        assertFalse("The person list of Phonebook is null! \n"
                + "Create list in constructor with command this." + lista.getName() + " = new ArrayList<Person>();", hlot == null);

        assertEquals("Ensure that the code p = new Phonebook(); p.add(\"Pekka\", \"040-123654\");"
                + " creates a Person object that is added to the person list within the phone book\n"
                + "Listan koko: ", 1, hlot.size());

        v = "\nCode that caused the problem p = new Phonebook(); p.add(\"Pekka\", \"040-123654\");"
                + "p.add(\"Jukka\", \"045-222333\");";

        klass.method(olio, method)
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Jukka", "040-123654");

        assertEquals("Ensure that the code p = new Phonebook(); "
                + "p.add(\"Pekka\", \"040-123654\"); p.add(\"Jukka\", \"045-222333\");"
                + " creates a Person object that is added to the person list within the phone book. "
                + "List size: ", 2, hlot.size());
    }

    @Points("94.2")
    @Test
    public void test13() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        String method = "printAll";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameP);

        Object muistio = klassP.constructor().takingNoParams().invoke();

        assertTrue("In the class " + klassNameP + ", add the method public void " + method + "()", klass.method(muistio, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nCode that caused the problem p = new Phonebook(); p.printAll();";

        klass.method(muistio, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Pekka", "040-123654");

        v = "p = new Phonebook(); p.add(\"Pekka\", \"040-123654\"); p.printAll();";

        klass.method(muistio, method).returningVoid().takingNoParams().invoke();

        String out = stdio.getSysOut();

        assertTrue("Test code " + v + "\nNow nothing is printed!", out.length() > 0);

        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Pekka") && out.contains("040-123654"));


        v = "p = new Phonebook(); "
                + "p.add(\"Pekka\", \"040-123654\"); "
                + "p.add(\"Jukka\", \"045-332211\"); "
                + "p.printAll();";

        klass.method(muistio, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Jukka", "045-332211");

        int pit = out.length();

        klass.method(muistio, method).returningVoid().takingNoParams().invoke();

        String out2 = stdio.getSysOut();

        out = stdio.getSysOut().substring(pit);

        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Pekka") && out.contains("040-123654"));
        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Jukka") && out.contains("045-332211"));

        v = "p = new Phonebook(); "
                + "p.add(\"Pekka\", \"040-123654\"); "
                + "p.add(\"Jukka\", \"045-332211\"); "
                + "p.add(\"Liisa\", \"050-525252\"); "
                + "p.printAll();";

        klass.method(muistio, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Liisa", "050-525252");

        pit += out.length();

        klass.method(muistio, method).returningVoid().takingNoParams().invoke();

        out2 = stdio.getSysOut();
        out = stdio.getSysOut().substring(pit);

        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Pekka") && out.contains("040-123654"));
        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Jukka") && out.contains("045-332211"));
        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.contains("Liisa") && out.contains("050-525252"));
        assertTrue("Test code " + v + "\nOutput was:\n" + out, out.split("\n").length > 2);

    }

    /*
     * 
     */
    @Points("94.3")
    @Test
    public void test14() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        String method = "searchNumber";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassNameP);

        Object muistio = klassP.constructor().takingNoParams().invoke();

        assertTrue("In the class " + klassNameP + ", add the method public String " + method + "(String person)", klass.method(muistio, method)
                .returning(String.class).taking(String.class).isPublic());

        String v = "\nCode that caused the problem p = new Phonebook(); p.searchNumber(\"Pekka\");";

        klass.method(muistio, method)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("Pekka");

        klass.method(muistio, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Pekka", "040-123654");

        v = "Check code p = new Phonebook(); "
                + "p.add(\"Pekka\", \"040-123654\"); "
                + "p.add(\"Jukka\", \"045-332211\"); ";

         klass.method(muistio, "add")
                .returningVoid().taking(String.class, String.class).withNiceError(v).invoke("Jukka", "045-332211");

         assertEquals(v+" p.searchNumber(\"Pekka\");\n", "040-123654", klass.method(muistio, method)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("Pekka"));

         assertEquals(v+" p.searchNumber(\"Jukka\");\n","045-332211", klass.method(muistio, method)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("Jukka"));

         String tulos = klass.method(muistio, method)
                .returning(String.class).taking(String.class).withNiceError(v).invoke("Mikko");

        v = "You should return \"number not known\" with code: "
                + "p.add(\"Pekka\", \"040-123654\"); "
                + "p.add(\"Jukka\", \"045-332211\"); ";

        assertTrue(v+" p.searchNumber(\"Mikko\");\nyou did not return anything!", tulos.length()>0);
         assertTrue(v+" p.searchNumber(\"Mikko\");\nyou returned:\n" + tulos, tulos.contains("not") );

//         assertEquals(v+" p.getNumber(\"Mikko\");\n","number ei tiedossa", klass.method(muistio, method)
//                .returning(String.class).taking(String.class).withNiceError(v).invoke("Mikko"));
    }

    /*
     *
     */
    public Object luoPerson(String name, String nro) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, String> ctor = klassH.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(name, nro);
    }

    private void saniteettitarkastus(String luokanNimi, int muuttujia, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("you do not need \"static variables\", remove " + kentta(field.toString(), luokanNimi), field.toString().contains("static") && !field.toString().contains("final"));
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
