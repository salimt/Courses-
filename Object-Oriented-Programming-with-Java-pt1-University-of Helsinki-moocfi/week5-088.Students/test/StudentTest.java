
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class StudentTest {

    String luokanNimi = "Student";
    Class luokka;
    Reflex.ClassRef<Object> klass;
    String klassName = "Student";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("88.1")
    @Test
    public void test1() {
        assertTrue("Class " + klassName + " should be public, define it as follows \npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("88.1")
    @Test
    public void test2() throws Throwable {
        Reflex.MethodRef2<Object, Object, String, String> ctor = klass.constructor().taking(String.class, String.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(String name, String studentNumber)", ctor.isPublic());
        ctor.invoke("Pekka", "1234567");
    }

    public Object luo(String name, String opnro) throws Throwable {
        Reflex.MethodRef2<Object, Object, String, String> ctor = klass.constructor().taking(String.class, String.class).withNiceError();
        return ctor.invoke(name, opnro);
    }

    @Points("88.1")
    @Test
    public void test3() {
        Method m = null;
        try {
            String[] args = new String[0];
            m = ReflectionUtils.requireMethod(Main.class, "main", args.getClass());
        } catch (Throwable e) {
            e.printStackTrace();
            fail("main-method puuttuu: " + e.getMessage());
        }

        String virhe = "";
        if (m.toString().indexOf("thr") > 0) {
            virhe = m.toString().substring(+m.toString().indexOf("thr"));
        }

        assertFalse("main should not throw exeption, remove: " + virhe, m.toString().contains("throws"));
    }

    @Points("88.1")
    @Test
    public void test4() {
        saniteettitarkastus(klassName, 2, "instance variables for name and studentnumber");
    }

    @Points("88.1")
    @Test
    public void test5() throws Throwable {
        String method = "getName";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Pekka", "123456");

        assertTrue("add class " + klassName + " method public String " + method + "() ", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        String v = "\nCode that caused the problem Student o = new Student(\"Pekka\",\"123456\"); "
                + "o.getName();";

        assertEquals("Check the code Student o = new Student(\"Pekka\",\"123456\"); "
                + "o.getName();", "Pekka", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        tuoteClass.method(new Object(), method).returningVoid().takingNoParams().withNiceError(method);
    }

    @Points("88.1")
    @Test
    public void test6() throws Throwable {
        Class c = ReflectionUtils.findClass(luokanNimi);
        String method = "getStudentNumber";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);

        Object olio = luo("Pekka", "123456");

        assertTrue("add class " + klassName + " method public String " + method + "() ", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());

        String v = "\nCode that caused the problem Student o = new Student(\"Pekka\",\"123456\"); "
                + "o.getStudentNumber();";

        assertEquals("Check the code o = new Student(\"Pekka\",\"123456\"); o.getStudentNumber();", "123456", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

    }

    @Points("88.1")
    @Test
    public void test8() throws Throwable {
        String name = "Peter";
        String studentNumber = "12345T";
        Object student = newStudent(name, studentNumber);
        String vastaus = toString(student);

        assertFalse("Add class " + luokanNimi + " method public String toString() as the assignment defines", vastaus.contains("@"));
        assertTrue("student = new Student(\"" + name + "\", \"" + studentNumber + "\"), but toString() was not done right, output: " + vastaus,
                vastaus.contains(name+" ("+studentNumber+")"));
    }

    @Points("88.2")
    @Test
    public void test9() throws Throwable {
        String[] inputRows = new String[]{
            "Martti Ahtisaari", "33442111", "", "hopi"
        };

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();

        String v = "With input \"Martti Ahtisaari\", \"33442111\", \"\")";

        assertTrue("Your program should start by prompting user with text \"name:\"", output.contains("ame"));
        assertTrue("Your program should start by prompting user with text \"studentnumber:\"", output.contains("mber"));

        assertFalse(v + " your program should stop when user enters an empty name.\n"
                + "Now your program asks studentnumber for the 'nonexistent' student!\n"
                + "Output was \n" + output, output.substring(output.indexOf("mber") + "mber".length()).contains("mber"));


    }

    @Points("88.2")
    @Test
    public void test10() throws Throwable {
        String[] inputRows = new String[]{
            "Martti Ahtisaari", "33442111", "", "hopi"
        };

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();

        assertTrue("With input \"Martti Ahtisaari\", \"33442111\", \"\"  the list of students was not printed correctly \n"
                + "Ensure that your main program works according to the assignment definition\n"
                + "Output was: " + output,
                output.contains("Martti Ahtisaari (33442111)"));

    }

    @Points("88.2")
    @Test
    public void test11() throws Throwable {
        String[] inputRows = new String[]{
            "Alan Turing", "932847214", "Martti Ahtisaari", "33442111",
            "Donald Knuth", "84732", "", "hip"
        };

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();

        for (String row : inputRows) {
            if (row.isEmpty()) {
                break;
            }
            assertTrue("All the entered students were not printed\n"
                     + "Ensure that your main program works according to the assignment definition\n"
                    + ".Output was:\n" + output,
                    output.contains(row));
        }
    }

    @Points("88.3")
    @Test
    public void test12() throws Throwable {
        String[] inputRows = new String[]{
            "Martti", "123", "Arto", "333", "", "to"
        };

        String[] expectedResults = new String[]{
            "Arto", "333"
        };

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();
        int index = output.indexOf("esult");
        if (index < 0) {
            fail("Your program should print \"result\" before the results are printed \n"
                    + "Ensure that your main program works according to the assignment definition.");
        }

        output = output.substring(Math.max(index-1,0));

        for (String row : expectedResults) {
            assertTrue("The search did not work as expected. With input "
                    + "\"Martti\" \"123\" \"Arto\" \"333\" and rearch term \"to\"\n"
                    + "Ensure that your main program works according to the assignment definition.\n "
                    + "Output was:\n" + output,
                    output.contains(row) && !output.contains("Martti"));
        }
    }

    @Points("88.3")
    @Test
    public void test13() throws Throwable {
        String[] inputRows = new String[]{
            "Alan Turing", "932847214", "Martti Ahtisaari", "33442111",
            "Donald Knuth", "84732R", "", "nuth"
        };

        String[] expectedResults = new String[]{
            "Donald Knuth", "84732R"
        };

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();
        int index = output.indexOf("esult");
        if (index < 0) {
            fail("Your program should print \"result\" before the results are printed \n"
                    + "Ensure that your main program works according to the assignment definition.");
        }

        output = output.substring(Math.max(index-1,0));

        for (String row : expectedResults) {
            assertTrue("The search did not work as expected.. \n"
                    + "Ensure that your main program works according to the assignment definition.",
                    output.contains(row) && !output.contains("Turing") && !output.contains("Ahtisaari"));
        }
    }

    @Points("88.3")
    @Test
    public void test14() throws Throwable {
        String[] inputRows = new String[]{
            "Eka student", "932847214", "Toka student", "33442111",
            "Kolmas student", "84732R", "", "ka"
        };

        String[] expectedResults = new String[]{
            "Eka student", "932847214", "Toka student", "33442111",};

        String input = "";
        for (String row : inputRows) {
            input = input + row + "\n";
        }

        MockInOut mio = new MockInOut(input);
        try {
            Main.main(new String[0]);
        } catch (NoSuchElementException e) {
            // ignore
        } catch (Throwable t) {
            fail("Your program throws an exception " + t.toString());
        }

        String output = mio.getOutput();
        int index = output.indexOf("esult");
        if (index < 0) {
            fail("Your program should print \"result\" before the results are printed \n"
                    + "Ensure that your main program works according to the assignment definition.");
        }

        output = output.substring(Math.max(index-1,0));

        for (String row : expectedResults) {
            assertTrue("The search did not work as expected.. \n"
                    + "Ensure that your main program works according to the assignment definition.",
                    output.contains(row));
        }
    }

    private String toString(Object kortti) throws Throwable {
        Method toString = ReflectionUtils.requireMethod(luokka, "toString");
        return ReflectionUtils.invokeMethod(String.class, toString, kortti);
    }

    private Object newStudent(String name, String studentNumber) {
        try {
            luokka = ReflectionUtils.findClass(luokanNimi);
            return ReflectionUtils.invokeConstructor(luokka.getConstructor(String.class, String.class), name, studentNumber);
        } catch (Throwable t) {
            fail("check the following code:\n "
                    + " Student op = new Student(\"" + name + "\", \"" + studentNumber + "\");");
        }
        return null;
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
