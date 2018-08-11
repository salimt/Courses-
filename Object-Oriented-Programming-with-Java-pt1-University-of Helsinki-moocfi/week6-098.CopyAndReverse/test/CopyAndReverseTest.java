
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class CopyAndReverseTest {

    Reflex.ClassRef<Object> klass;
    String klassName = "Main";

    @Points("98.1")
    @Test
    public void hasMethodCopy() throws Throwable {
        klass = Reflex.reflect(klassName);
        String metodinNimi = "copy";

        assertTrue("Implement the " + klassName + " the method public static int[] " + metodinNimi + "(int[] array) ",
                klass.staticMethod(metodinNimi).returning(int[].class).
                taking(int[].class).isPublic());

        int[] t = {1, 2, 3};

        String v = "\nError cause was the code int[] t = {1,2,3}; " + klassName + "." + metodinNimi + "(t);";

        klass.staticMethod(metodinNimi).returning(int[].class).
                taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("98.1")
    @Test
    public void copy1() {
        int[] original = {1, 2, 3};
        int[] copied = copy(original);

        assertFalse("int[] original = {1, 2, 3}; \nint[] copied = copy(original);\n"
                + "copied was null",copied==null);
        
        assertArrayEquals("Method copy fails with parameter " + toS(original), original, copied);

        copied[0] = 0;
        assertEquals("Changing the copied should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \nint[] copied = copy(original); \ncopied[0]=0; \nSystem.out.println(original[0]); ", 1, original[0]);
    }
    Random arpa = new Random();

    @Points("98.1")
    @Test
    public void copy2() {
        int n = arpa.nextInt(5) + 5;

        int[] original = new int[n];
        for (int i = 0; i < original.length; i++) {
            original[i] = arpa.nextInt(20);
        }
        int eka = original[0];
        int[] copied = copy(original);


        assertArrayEquals("Method copy fails with parameter " + toS(original), original, copied);

        copied[0] = 0;
        assertEquals("Changing the copied should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \nint[] copied = copy(original); \ncopied[0]=0; \nSystem.out.println(original[0]); ", eka, original[0]);
    }

    @Points("98.1")
    @Test
    public void copy3() {
        int n = arpa.nextInt(10) + 10;

        int[] original = new int[n];
        for (int i = 0; i < original.length; i++) {
            original[i] = arpa.nextInt(20);
        }

        int eka = original[0];
        int[] copied = copy(original);

        assertArrayEquals("Method copy fails with parameter " + toS(original), original, copied);

        copied[0] = 0;
        assertEquals("Changing the copied should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \nint[] copied = copy(original); \ncopied[0]=0; \nSystem.out.println(original[0]); ", eka, original[0]);
    }

    /*
     * 
     */
    @Points("98.2")
    @Test
    public void hasMethodReverseCopy() throws Throwable {
        klass = Reflex.reflect(klassName);
        String metodinNimi = "reverseCopy";

        assertTrue("Implemet the class " + klassName + " the method public static int[] " + metodinNimi + "(int[] array) ",
                klass.staticMethod(metodinNimi).returning(int[].class).
                taking(int[].class).isPublic());

        int[] t = {1, 2, 3};

        String v = "\nError cause was the code int[] t = {1,2,3}; " + klassName + "." + metodinNimi + "(t);";

        klass.staticMethod(metodinNimi).returning(int[].class).
                taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("98.2")
    @Test
    public void reverseCopy1() {
        int[] original = {1, 2, 3};
        int[] copied = kaanna(original);
        int[] odotettu = {3, 2, 1};

        assertFalse("int[] original = {1, 2, 3}; \nint[] reversed = reverse(original);\n"
                + "reversed was null",copied==null);
                
        
        assertArrayEquals("method reverseCopy fails with the parameter " + toS(original), odotettu, copied);

        assertEquals("Making a reverse copy should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \n"
                + "int[] reversed = reverseCopy(original); \n"
                + "reversed[0]=0; \n"
                + "System.out.println(original[0]); ", 1, original[0]);

    }

    @Points("98.2")
    @Test
    public void reverseCopy2() {
        int n = arpa.nextInt(5) + 5;

        int[] original = new int[n];
        int[] odotettu = new int[n];

        for (int i = 0; i < odotettu.length; i++) {
            int arvottu = arpa.nextInt(20);
            original[i] = arvottu;
            odotettu[n - 1 - i] = arvottu;
        }

        int eka = original[0];
        int[] copied = kaanna(original);

        assertArrayEquals("method reverseCopy fails with the parameter " + toS(original), odotettu, copied);
        assertEquals("Making a reverse copy should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \n"
                + "int[] reversed = reverseCopy(reversed); \n"
                + "reversed[0]=0; \n"
                + "System.out.println(original[0]); ", eka, original[0]);
    }

    @Points("98.2")
    @Test
    public void reverseCopy3() {
        int n = arpa.nextInt(10) + 10;

        int[] original = new int[n];
        int[] odotettu = new int[n];

        for (int i = 0; i < odotettu.length; i++) {
            int arvottu = arpa.nextInt(20);
            original[i] = arvottu;
            odotettu[n - 1 - i] = arvottu;
        }

        int eka = original[0];
        int[] copied = kaanna(original);

        assertArrayEquals("method reverseCopy fails with the parameter " + toS(original), odotettu, copied);
        assertEquals("Making a reverse copy should not affect to the original! "
                + "Check the following code:\n"
                + "int[] original = {1, 2, 3}; \n"
                + "int[] reversed = reverseCopy(reversed); \n"
                + "reversed[0]=0; \n"
                + "System.out.println(original[0]); ", eka, original[0]);
    }

    /*
     * 
     */
    private int[] copy(int[] t) {
        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, "copy", int[].class);

            return ReflectionUtils.invokeMethod(int[].class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("method copy causes ArrayIndexOutOfBoundsException with input " + toS(t));
        } catch (NullPointerException e) {
            fail("method copy causes NullPointerException with input"+toS(t)+"\n "
                    + "have you created the copy using new int[...]");
        } catch (Throwable e) {
            fail(e.toString()+" with input "+toS(t));
        }
        return null;
    }

    private int[] kaanna(int[] t) {
        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, "reverseCopy", int[].class);

            return ReflectionUtils.invokeMethod(int[].class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("method reverseCopy causes ArrayIndexOutOfBoundsException with input " + toS(t));
        } catch (NullPointerException e) {
            fail("method reverseCopy causes NullPointerException with input"+toS(t)+"\n "
                    + "have you created the copy using new int[...]");
        } catch (Throwable e) {
            fail(e.toString()+" with input "+toS(t));
        }
        return null;
    }

    private String toS(int[] t) {
        return Arrays.toString(t).replace("[", "").replace("]", "");
    }
}
