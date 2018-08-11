
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class C_MyDateTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "MyDate";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("91.3")
    @Test
    public void test1() throws Throwable {
        String method = "afterNumberOfDays";

        Object olio = new MyDate(1, 1, 2011);

        assertTrue("Add class " + klassName + " method public MyDate " + method + "(int days) ", klass.method(olio, method)
                .returning(MyDate.class).taking(int.class).isPublic());

        String v = "\nCode that caused the failure MyDate p = new MyDate(1, 1, 2011); "
                + "p.afterNumberOfDays(7);";


        klass.method(olio, method)
                .returning(MyDate.class).taking(int.class).withNiceError(v).invoke(7);

    }

    @Points("91.3")
    @Test
    public void test2() {
        Class c = MyDate.class;
        String method = "afterNumberOfDays";
        String virhe = "Add class MyDate method public MyDate afterNumberOfDays(int days)";
        Method m = null;
        try {
            m = ReflectionUtils.requireMethod(c, method, int.class);
        } catch (Throwable t) {
            fail(virhe);
        }
        assertTrue(virhe, m.toString().contains("public"));
        assertFalse(virhe, m.toString().contains("static"));


        MyDate original = new MyDate(30, 12, 2011);
        MyDate newDate = null;
        try {
            newDate = ReflectionUtils.invokeMethod(MyDate.class, m, original, 4);
        } catch (Throwable t) {
            fail(virhe);
        }

        Assert.assertEquals("Ensure that afterNumberOfDays does not alter the object for which it is called.",
                "30.12.2011",
                original.toString());

        Assert.assertFalse("Now afterNumberOfDays-method returns null.\n With call MyDate original = new MyDate(30, 12, 2011); MyDate newDate = original.afterNumberOfDays(5); The new date should be in January 2012.",
                 newDate==null);        
        
        Assert.assertEquals("Ensure that afterNumberOfDays does not alter the object for which it is called. \n"
                + "With MyDate original = new MyDate(30, 12, 2011); MyDate newDate = original.afterNumberOfDays(5); The new date should be in January 2012.",
                "4.1.2012", newDate.toString());
    }


}
