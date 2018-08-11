
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class A_MyDateTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "MyDate";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("91.1")
    @Test
    public void test1() {
        saniteettitarkastus("MyDate", 3, "new object variables, those are not needed ");
    }


    @Points("91.1")
    @Test
    public void test2() throws Throwable {
        String method = "advance";

        Object olio = new MyDate(1, 1, 2011);

        assertTrue("add class " + klassName + " method public void " + method + "() ", klass.method(olio, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nCode that caused the failure MyDate p = new MyDate(1, 1, 2011); "
                + "p.advance();";
        
        klass.method(olio, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
        
    }

    @Points("91.1")
    @Test
    public void test3() {
        Class c = MyDate.class;
        String method = "advance";
        String virhe = "Add class MyDate method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, advance.toString().contains("public"));
        assertFalse(virhe, advance.toString().contains("static"));


        MyDate muokattava = new MyDate(26, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, advance, muokattava);
        } catch (Throwable t) {
            fail("Add class MyDate method public void advance()");
        }


        virhe = "Ensure that method advance advances the date by one \n"
                + "When you create MyDate myDate = new MyDate(26, 12, 2011); and call method myDate.advance() once, the result should be 27.12.2011.\n";
        Assert.assertEquals(virhe, "27.12.2011", muokattava.toString());
    }

    @Points("91.1")
    @Test
    public void test4() {
        Class c = MyDate.class;
        String method = "advance";
        String virhe = "Add class MyDate method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, advance.toString().contains("public"));
        assertFalse(virhe, advance.toString().contains("static"));


        MyDate muokattava = new MyDate(29, 12, 2011);

        try {
            ReflectionUtils.invokeMethod(void.class, advance, muokattava);
        } catch (Throwable t) {
            fail("Add class MyDate method public void advance()");
        }


        virhe = "Ensure that method advance advances the date by one  \n"
                + "When you create MyDate myDate = new MyDate(29, 12, 2011); and call method myDate.advance() once, the result should be 30.12.2011.\n";
        Assert.assertEquals(virhe, "30.12.2011", muokattava.toString());

            try {
            ReflectionUtils.invokeMethod(void.class, advance, muokattava);
        } catch (Throwable t) {
            fail("");
        }


        virhe = "Ensure that method advance advances the date by one  \n"
                + "When you create MyDate myDate = new MyDate(29, 12, 2011); "
                + "and call method myDate.advance() twice, the result should be 1.1.2012.\n";
        Assert.assertEquals(virhe, "1.1.2012", muokattava.toString());
    }        
    
    @Points("91.1")
    @Test
    public void test5() {
        Class c = MyDate.class;
        String method = "advance";
        String virhe = "Add class MyDate method public void advance()";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, advance.toString().contains("public"));
        assertFalse(virhe, advance.toString().contains("static"));


        MyDate muokattava = new MyDate(30, 12, 2011);

        try {
            for (int i = 0; i < 500; i++) {
                ReflectionUtils.invokeMethod(void.class, advance, muokattava);
            }
        } catch (Throwable t) {
            fail("Ensure that method advance advances the date by one ");
        }

        virhe = "Ensure that method advance advances the date by one \n"
                + "When you create MyDate myDate = new MyDate(30, 12, 2011); and call method myDate.advance() 500 times, the date shuld have reached year 2013.\nNow it was " + muokattava.toString();

        Assert.assertTrue(virhe, muokattava.toString().contains("2013"));
    }

    @Points("91.2")
    @Test
    public void test6() throws Throwable {
        String method = "advance";

        Object olio = new MyDate(1, 1, 2011);

        assertTrue("add class " + klassName + " method public void " + method + "(int numberOfDays) ", 
                klass.method(olio, method)
                    .returningVoid().taking(int.class).isPublic());

        String v = "\nCode that caused the failure MyDate p = new MyDate(1, 1, 2011); "
                + "p.advance(23);";
        
        klass.method(olio, method)
                .returningVoid().taking(int.class).withNiceError(v).invoke(23);
        
    }   

    @Points("91.2")
    @Test
    public void test7() {
        Class c = MyDate.class;
        String method = "advance";
        String virhe = "Add class MyDate method public void advance(int numberOfDays)";
        Method advance = null;
        try {
            advance = ReflectionUtils.requireMethod(c, method, int.class);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, advance.toString().contains("public"));
        assertFalse(virhe, advance.toString().contains("static"));


        MyDate muokattava = new MyDate(30, 12, 2011);
        try {
            ReflectionUtils.invokeMethod(void.class, advance, muokattava, 3);
        } catch (Throwable t) {
            fail("Add class MyDate method public void advance(int numberOfDays).");
        }

        MyDate menneisyys = new MyDate(2, 1, 2012);
        MyDate tulevaisuus = new MyDate(4, 1, 2012);

        Assert.assertEquals("Ensure that the method advance(param) advances the date by the given amount of days \n"
                + "After MyDate myDate = new MyDate(30, 12, 2011); myDate.advance(3); The date should be 3.1.2012.\n",
                "3.1.2012",
                muokattava.toString());
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
    

    private void saniteettitarkastus2(String luokanNimi, int muuttujia, String msg) throws SecurityException {

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
            assertTrue(msg, var <= muuttujia);
        }
    }

    private String kentta(String toString) {
        return toString.replace("MyDate" + ".", "");
    }
}
