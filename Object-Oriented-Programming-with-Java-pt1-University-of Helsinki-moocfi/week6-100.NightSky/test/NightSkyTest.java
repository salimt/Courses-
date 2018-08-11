
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NightSkyTest {

    @Rule
    public MockStdio stdio = new MockStdio();
    private String tahtitaivasLuokka = "NightSky";
    private Class clazz;
    private String printLineMetodi = "printLine";
    private String printMetodi = "print";
    private String starsInLastPrintMetodi = "starsInLastPrint";
    Reflex.ClassRef<Object> klass;
    String klassName = "NightSky";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
        clazz = ReflectionUtils.findClass(tahtitaivasLuokka);
    }

    @Points("100.1")
    @Test
    public void test1() {
        assertTrue("Class " + klassName + " should be defined as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("100.1 100.2 100.3")
    @Test
    public void test2() {
        saniteettitarkastus(klassName, 4, "instance variables for density, width, height and the amount of stars in last print");
    }

    @Points("100.1")
    @Test
    public void test3() throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(double density)", ctor.isPublic());
        ctor.invoke(25.5);
    }

    @Test
    @Points("100.1")
    public void test4() throws Throwable {
        Reflex.MethodRef2<Object, Object, Integer, Integer> ctor = klass.constructor().taking(int.class, int.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(int width, int height)", ctor.isPublic());
        ctor.invoke(50, 20);
    }

    @Test
    @Points("100.1")
    public void test5() throws Throwable {
        Reflex.MethodRef3<Object, Object, Double, Integer, Integer> ctor = klass.constructor().taking(double.class, int.class, int.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(double density, int width, int height)", ctor.isPublic());
        ctor.invoke(25.0, 50, 20);
    }

    @Test
    @Points("100.1")
    public void test6() throws Throwable {
        String metodi = printLineMetodi;

        Object olio = klass.constructor().taking(double.class, int.class, int.class).invoke(20.0, 10, 5);

        assertTrue("add class " + klassName + " the method public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nerror was caused by the code NightSky t = new(20.0, 10, 5); t.printLine();";

        klass.method(olio, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("100.1")
    public void test7() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 1.0);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printLineMetodi);

        try {
            ReflectionUtils.invokeMethod(void.class, m, inst);
            String out = stdio.getSysOut();
            if (out == null || out.isEmpty()) {
                fail("Ensure that the method " + printLineMetodi + " prints something.");
            }
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printLineMetodi + "() "
                    + "\nCall to the method should print something");
        }
    }

    @Test
    @Points("100.1")
    public void test8() {
        noForbiddens();
    }

    @Test
    @Points("100.1")
    public void test9() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 1.0);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printLineMetodi);

        String out = "";
        try {
            ReflectionUtils.invokeMethod(void.class, m, inst);
            out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printLineMetodi + "().");
        }

        int loppu = Math.max(0, out.length() - 2);
        assertFalse("method " + printLineMetodi + " is allowed to print a new line"
                + "only as the last character!"
                + "Output with code"
                + "NightSky t = new NightSky(1.0); t.printLine();\n"
                + out, out.substring(0, loppu).contains("\n"));

        if (tahdet != 20) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(double density) sets width to 20 and height to 10. \n"
                    + "Check also that the method printLine prints a full line of stars (width 20) when density is 1. "
                    + "\nNow it printed \n"
                    + out);
        }
    }

    @Test
    @Points("100.1")
    public void test10() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 1.0, 5, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density, int width, int height).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printLineMetodi);

        try {
            ReflectionUtils.invokeMethod(void.class, m, inst);
            String out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printLineMetodi + "().");
        }

        if (tahdet != 5) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(double density, int width, int height) initializes the instance variables with given values\n"
                    + "With new NightSky(1, 5, 10); the printed line should have 5 stars.");
        }
    }

    @Test
    @Points("100.1")
    public void test12() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 1.0, 7, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density, int width, int height).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printLineMetodi);

        String out = "";

        try {
            ReflectionUtils.invokeMethod(void.class, m, inst);
            out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printLineMetodi + "().");
        }

        if (tahdet != 7) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(double density, int width, int height) initializes the instance variables with given values\n"
                    + "With new NightSky(1, 7, 10); the printed line should have 7 stars.\n"
                    + "Now the output was:\n" + out);
        }
    }

    @Test
    @Points("100.1")
    public void test13() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 0.1, 10, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density, int width, int height).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printLineMetodi);

        try {
            for (int i = 0; i < 1000; i++) {
                ReflectionUtils.invokeMethod(void.class, m, inst);
            }
            String out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printLineMetodi + "().");
        }

        if (tahdet < 500 || tahdet > 1500) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(double density, int width, int height) initializes the instance variables with given values\n"
                    + "With density of 0.1 rougly 10% of the printed characters should be stars");

        }
    }

    @Test
    @Points("100.2")
    public void test14() throws Throwable {
        String metodi = "print";

        Object olio = klass.constructor().taking(double.class, int.class, int.class).invoke(20.0, 10, 5);

        assertTrue("add class " + klassName + " the method public void " + metodi + "() ", klass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nerror was caused by the code NightSky t = new(20.0, 10, 5); t.print();";

        klass.method(olio, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("100.2")
    public void test15() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, double.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 1.0);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(double density).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printMetodi);

        String out = "";
        try {
            ReflectionUtils.invokeMethod(void.class, m, inst);
            out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printMetodi + "().");
        }

        if (tahdet != 200) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(double density) "
                    + "sets width to 20 and height to 10. Check also that the method " + printMetodi + " "
                    + "prints 200 stars with density 1.\n"
                    + "Now there were " + tahdet + " stars. Output was:\n" + out);
        }

        if (!out.contains("\n")) {
            fail("Ensure that a night sky has multiple lines!");
        }
    }

    @Test
    @Points("100.2")
    public void test16() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 50, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(int width, int height).");
        }

        int tahdet = 0;
        Method m = ReflectionUtils.requireMethod(clazz, printMetodi);

        try {
            for (int i = 0; i < 100; i++) {
                ReflectionUtils.invokeMethod(void.class, m, inst);
            }

            String out = stdio.getSysOut();
            tahdet = laskeTahdet(out);
        } catch (Throwable t) {
            fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printMetodi + "().");
        }

        if (tahdet < 2500 || tahdet > 7500) {
            fail("Ensure that the constructor " + tahtitaivasLuokka + "(int width, int height) "
                    + "sets the variable density to value 0.1, meaning that  "
                    + "roughly 10% of the sky is covered with stars.");

            fail("Ensure that the constructor " + tahtitaivasLuokka + "(int width, int height) "
                    + "sets the density to default value 0.1. Roughly 10% of the printed characters"
                    + "should be stars!");
        }
    }

    @Test
    @Points("100.3")
    public void test17() throws Throwable {
        String metodi = "starsInLastPrint";

        Object olio = klass.constructor().taking(double.class, int.class, int.class).invoke(20.0, 10, 5);

        assertTrue("add class " + klassName + " the method public int " + metodi + "() ", klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nerror was caused by the code NightSky t = new(20.0, 10, 5); t.print(); t.starsInLastPrint()";

        klass.method(olio, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("100.3")
    public void test18() {
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 50, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(int width, int height).");
        }

        int tahdet;
        Method print = ReflectionUtils.requireMethod(clazz, printMetodi);

        Method tahtia = ReflectionUtils.requireMethod(clazz, starsInLastPrintMetodi);

        for (int i = 0; i < 100; i++) {
            String out = stdio.getSysOut();
            try {
                ReflectionUtils.invokeMethod(void.class, print, inst);
            } catch (Throwable ex) {
                fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printMetodi
                        + "().");
            }

            out = stdio.getSysOut().substring(out.length());
            int nahtyTahtia = 0;
            try {
                nahtyTahtia = ReflectionUtils.invokeMethod(int.class, tahtia, inst);
            } catch (Throwable ex) {
                fail("Ensure that class " + tahtitaivasLuokka + " has method public int " + starsInLastPrintMetodi + "().");
            }
            tahdet = laskeTahdet(out);

            if (nahtyTahtia != tahdet) {
                fail("Ensure that the method public int starsInLastPrintMetodi() "
                        + "returns the amount of stars in the las printed Night Sky. \n"
                        + "The method claims the number of stars was " + nahtyTahtia + " "
                        + "but in reality there were " + tahdet + " "
                        + "stars in the Night Sky:\n " + out);
            }
        }
    }

    @Test
    @Points("100.3")
    public void test19() throws Throwable {
        test18();
        Constructor densityKonstruktori = ReflectionUtils.requireConstructor(clazz, int.class, int.class);
        Object inst = null;
        try {
            inst = ReflectionUtils.invokeConstructor(densityKonstruktori, 50, 10);
        } catch (Throwable ex) {
            fail("Ensure that class " + tahtitaivasLuokka + " has constructor public " + tahtitaivasLuokka + "(int width, int height).");
        }

        int tahdet = 0;
        Method print = ReflectionUtils.requireMethod(clazz, printMetodi);

        Method tahtia = ReflectionUtils.requireMethod(clazz, starsInLastPrintMetodi);

        for (int i = 0; i < 100; i++) {
            String out = stdio.getSysOut();
            try {
                ReflectionUtils.invokeMethod(void.class, print, inst);
            } catch (Throwable ex) {
                fail("Ensure that class " + tahtitaivasLuokka + " has method public void " + printMetodi + "().");
            }

            out = stdio.getSysOut().substring(out.length());
            int nahtyTahtia = 0;
            try {
                nahtyTahtia = ReflectionUtils.invokeMethod(int.class, tahtia, inst);
            } catch (Throwable ex) {
                fail("Ensure that class " + tahtitaivasLuokka + " has method public int " + starsInLastPrintMetodi + "().");
            }
            tahdet = laskeTahdet(out);

            if (nahtyTahtia != tahdet) {
                fail("Ensure that the method public int starsInLastPrintMetodi() palauttaa viimeisimmässä tulostuksessa olleiden tähtien määrän.");
            }
        }


        try {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop == null || !desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                return;
            }
            java.net.URI uri = new java.net.URI("https://www.youtube.com/watch?v=SLuW-GBaJ8k");
            desktop.browse(uri);
        } catch (Exception e) {
        }
    }

    private int laskeTahdet(String tuloste) {
        int tahdet = 0;
        for (char c : tuloste.toCharArray()) {
            if (c == '*') {
                tahdet++;
            }
        }

        return tahdet;
    }

    private void noForbiddens() {
        try {
            Scanner lukija = new Scanner(new File("src/NightSky.java"));
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("while")) {
                    fail("Koska nyt harjoitellaan for-toistolauseketta, "
                            + "et saa käyttää ohjelmassasi while-komentoa.");
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
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
