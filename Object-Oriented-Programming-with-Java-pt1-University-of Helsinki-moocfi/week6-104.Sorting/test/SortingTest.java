
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.junit.*;
import static org.junit.Assert.*;

public class SortingTest {

    String klassName = "Main";
    Reflex.ClassRef<Object> klass;

    @Before
    public void justForKicks() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    @Points("104.1")
    public void test1() {
        klass = Reflex.reflect(klassName);
        assertTrue("Class " + klassName + " should be public, define it as\n"
                + "public class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("104.1")
    public void test2() throws Throwable {
        String metodi = "smallest";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue("In the class Main, add the following method: public static int smallest(int[] array)",
                klass.staticMethod(metodi).returning(int.class).taking(int[].class).isPublic());


        String v = "Problem caused by the code \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = smallest(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("104.1")
    @Test
    public void test3() {
        noForbiddens();
    }

    @Points("104.1")
    @Test
    public void test4() {
        int[] t = {2, 1, 3, 0};
        pienin(t);

        assertTrue("the method smallest should not change the contents of the parameter ", t[0] == 2);
        assertTrue("the method smallest should not change the contents of the parameter ", t[1] == 1);
        assertTrue("the method smallest should not change the contents of the parameter ", t[2] == 3);
        assertTrue("the method smallest should not change the contents of the parameter ", t[3] == 0);
    }

    @Points("104.1")
    @Test
    public void test5() {
        int[] t = {2, 1, 3};
        int odotettu = 1;

        assertEquals("the method smallest did not work with the input " + toS(t) + "", odotettu, pienin(t));
    }

    @Points("104.1")
    @Test
    public void test6() {
        int[] t = {6, 3, 0, -1, 4};
        int odotettu = -1;

        assertEquals("the method smallest did not work with the input " + toS(t) + "", odotettu, pienin(t));
    }
    Random arpa = new Random();

    @Points("104.1")
    @Test
    public void test7() {
        int[] t = {3, 5, 6, 2, 7, 1, 3, 7, 5};
        int odotettu = arpa.nextInt(t.length);
        t[odotettu] = -10 - arpa.nextInt(10);

        assertEquals("the method smallest did not work with the input " + toS(t) + "", t[odotettu], pienin(t));
    }

    /*
     *
     */
    @Points("104.2")
    @Test
    public void test8() throws Throwable {
        String virhe = "In the class Main, add the following method: public static int indexOfTheSmallest(int[] array)";
        String metodi = "indexOfTheSmallest";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returning(int.class).taking(int[].class).isPublic());


        String v = "Problem caused by the code \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + metodi + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("104.2")
    @Test
    public void test9() {
        noForbiddens();
    }

    @Points("104.2")
    @Test
    public void test10() {
        int[] t = {2, 1, 3, 0};
        indexOfTheSmallest(t);

        assertTrue("the method indexOfTheSmallest should not change the contents of the parameter ", t[0] == 2);
        assertTrue("the method indexOfTheSmallest should not change the contents of the parameter ", t[1] == 1);
        assertTrue("the method indexOfTheSmallest should not change the contents of the parameter ", t[2] == 3);
        assertTrue("the method indexOfTheSmallest should not change the contents of the parameter ", t[3] == 0);
    }

    @Points("104.2")
    @Test
    public void indexOfTheSmallestTest1() {
        int[] t = {2, 1, 3};
        int odotettu = 1;

        assertEquals("the method indexOfTheSmallest did not work with the input " + toS(t) + "", odotettu, indexOfTheSmallest(t));
    }

    @Points("104.2")
    @Test
    public void indexOfTheSmallestTest2() {
        int[] t = {6, 3, 0, -1, 4};
        int odotettu = 3;

        assertEquals("the method indexOfTheSmallest did not work with the input " + toS(t) + "", odotettu, indexOfTheSmallest(t));
    }

    @Points("104.2")
    @Test
    public void indexOfTheSmallestTest3() {
        int[] t = {3, -5, 6, 1, 7, 1, 3, 7, 5};
        int odotettu = arpa.nextInt(t.length);
        t[odotettu] = -10 - arpa.nextInt(10);

        assertEquals("the method indexOfTheSmallest did not work with the input " + toS(t) + "", odotettu, indexOfTheSmallest(t));
    }

    /*
     *
     */
    @Points("104.3")
    @Test
    public void test11() {
        noForbiddens();
    }

    @Points("104.3")
    @Test
    public void test12() throws Throwable {
        String virhe = "In the class Main, add the following method: public static int indexOfTheSmallestStartingFrom(int[] array, int startingIndex)";
        String metodi = "indexOfTheSmallestStartingFrom";

        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returning(int.class).taking(int[].class, int.class).isPublic());


        String v = "Problem caused by the code \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + "int p = " + metodi + "(t, 1);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returning(int.class).taking(int[].class, int.class).withNiceError(v).invoke(t, 1);
    }

    @Points("104.3")
    @Test
    public void test13() {
        int[] t = {2, 1, 3, 0};
        indexOfTheSmallestStartingFrom(t, 0);

        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[0] == 2);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[1] == 1);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[2] == 3);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[3] == 0);

        indexOfTheSmallestStartingFrom(t, 2);

        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[0] == 2);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[1] == 1);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[2] == 3);
        assertTrue("the method indexOfTheSmallestStartingFrom should not change the contents of the parameter ", t[3] == 0);
    }

    @Points("104.3")
    @Test
    public void indexOfTheSmallestStartingFromTest1() {
        int[] t = {-1, 3, 1, 2};
        int a = 0;
        int odotettu = 0;

        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 1;
        odotettu = 2;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 2;
        odotettu = 2;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 3;
        odotettu = 3;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));
    }

    @Points("104.3")
    @Test
    public void indexOfTheSmallestStartingFromTest3() {
        //          0  1  2  3  4  5  6  7  8
        int[] t = {-1, 3, 1, 7, 4, 5, 2, 4, 3};
        int a = 0;
        int odotettu = 0;

        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 1;
        odotettu = 2;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 2;
        odotettu = 2;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 3;
        odotettu = 6;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 4;
        odotettu = 6;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 5;
        odotettu = 6;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 6;
        odotettu = 6;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

        a = 7;
        odotettu = 8;
        assertEquals("the method indexOfTheSmallestStartingFrom did not work with the input " + toS(t) + " startingIndex " + a, odotettu, indexOfTheSmallestStartingFrom(t, a));

    }
    /*
     *
     */

    @Points("104.4")
    @Test
    public void test14() throws Throwable {
        String virhe = "In the class Main, add the method: public static void swap(int[] array, int index1, int index2)";
        String metodi = "swap";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returningVoid().taking(int[].class, int.class, int.class).isPublic());


        String v = "Problem caused by the code \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + metodi + "(t, 1, 3);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returningVoid().taking(int[].class, int.class, int.class).withNiceError(v).invoke(t, 1, 3);
    }

    @Points("104.4")
    @Test
    public void test15() {
        noForbiddens();
    }

    @Points("104.4")
    @Test
    public void swapTest1() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] alkup = {4, 7, 8, 6};
        int[] od = {6, 7, 8, 4};
        int i1 = 0;
        int i2 = 3;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with parameter " + toS(alkup) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.4")
    @Test
    public void swapTest2() {
        //             0  1  2  3
        int[] t = {4, 7, 8, 6};
        int[] alkup = {4, 7, 8, 6};
        int[] od = {4, 8, 7, 6};
        int i1 = 1;
        int i2 = 2;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with parameter " + toS(alkup) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.4")
    @Test
    public void swapTest3() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 2, 8, 6, 9, 7, 3};
        int i1 = 1;
        int i2 = 5;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with parameter " + toS(alkup) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.4")
    @Test
    public void swapTest4() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 7, 9, 6, 8, 2, 3};
        int i1 = 2;
        int i2 = 4;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with parameter " + toS(alkup) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.4")
    @Test
    public void swapTest5() {
        //             0  1  2  3  4  5  6
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {3, 7, 8, 6, 9, 2, 4};
        int i1 = 0;
        int i2 = 6;

        swap(t, i1, i2);
        assertTrue("the method swap does not work correctly with parameter " + toS(alkup) + " index1=" + i1 + " index2=" + i2 + " "
                + "\nthe result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    /*
     *
     */
    @Points("104.5")
    @Test
    public void test17() throws Throwable {
        String virhe = "In the class Main, add the following method: public static void sort(int[] array)";
        String metodi = "sort";
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        assertTrue(virhe,
                klass.staticMethod(metodi).returningVoid().taking(int[].class).isPublic());


        String v = "Problem caused by the code \n"
                + "int[] t = {2, 1, 3, 0};\n"
                + metodi + "(t);\n";
        int[] t = {2, 1, 3, 0};
        klass.staticMethod(metodi).returningVoid().taking(int[].class).withNiceError(v).invoke(t);
    }

    @Points("104.5")
    @Test
    public void test18() {
        noForbiddens();
    }

    @Points("104.5")
    @Test
    public void sortTest1() {
        int[] t = {4, 7, 1};
        int[] alkup = {4, 7, 1};
        int[] od = {4, 7, 1};

        Arrays.sort(od);

        sort(t);
        assertTrue("the method sort does not work correctly with parameter " + toS(alkup) + " "
                + "the result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));

    }

    @Points("104.5")
    @Test
    public void sortTest2() {
        int[] t = {4, 7, 8, 6, 9, 2, 3};
        int[] alkup = {4, 7, 8, 6, 9, 2, 3};
        int[] od = {4, 7, 8, 6, 9, 2, 3};

        Arrays.sort(od);

        sort(t);
        assertTrue("the method sort does not work correctly with parameter " + toS(alkup) + " "
                + "the result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.5")
    @Test
    public void sortTest3() {
        int n = arpa.nextInt(5) + 5;

        int[] t = new int[n];
        int[] alkup = new int[n];
        int[] od = new int[n];

        for (int i = 0; i < n; i++) {
            int arvottu = 20 - arpa.nextInt(30);
            t[i] = arvottu;
            od[i] = arvottu;
            alkup[i] = arvottu;
        }

        Arrays.sort(od);

        sort(t);
        assertTrue("the method sort does not work correctly with parameter " + toS(alkup) + " "
                + "the result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
    }

    @Points("104.5")
    @Test
    public void sortTest4() {
        for (int k = 0; k < 10; k++) {
            int n = arpa.nextInt(20) + 20;

            int[] t = new int[n];
            int[] alkup = new int[n];
            int[] od = new int[n];

            for (int i = 0; i < n; i++) {
                int arvottu = 20 - arpa.nextInt(30);
                t[i] = arvottu;
                od[i] = arvottu;
                alkup[i] = arvottu;
            }

            Arrays.sort(od);

            sort(t);
            assertTrue("the method sort does not work correctly with parameter " + toS(alkup) + " "
                    + "the result was " + toS(t) + " but it should have been " + toS(od), Arrays.equals(od, t));
        }

    }

    /*
     *
     */
    private void sort(int[] t) {
        String metodi = "sort";

        try {
            Method m = ReflectionUtils.requireMethod(Main.class, metodi, int[].class);
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, t);

        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + metodi + " "
                    + "causes ArrayOutOfBounds exception with the input " + toS(t));
        } catch (Throwable e) {
            fail("the method " + metodi + " caused something unexpected to happen, "
                    + "more info:\n" + e);
        }
    }

    private void swap(int[] t, int i1, int i2) {
        String metodi = "swap";

        try {
            Method m = ReflectionUtils.requireMethod(Main.class, metodi, int[].class, int.class, int.class);

            ReflectionUtils.invokeMethod(int.class, m, null, t, i1, i2);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + metodi + " "
                    + "causes ArrayOutOfBounds exception with the input " + toS(t));
        } catch (Throwable e) {
            fail("the method " + metodi + " caused something unexpected to happen, "
                    + "more info:\n" + e);
        }
    }

    private int indexOfTheSmallestStartingFrom(int[] t, int a) {
        String metodi = "indexOfTheSmallestStartingFrom";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, metodi, int[].class, int.class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t, a);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + metodi + " "
                    + "causes ArrayOutOfBounds exception with input " + toS(t));
        } catch (Throwable e) {
            fail("the method " + metodi + " caused something unexpected to happen, "
                    + "more info:\n" + e);
        }
        return -1;
    }

    private int pienin(int[] t) {
        String metodi = "smallest";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, metodi, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + metodi + " "
                    + "causes ArrayOutOfBounds exception with the input " + toS(t));
        } catch (Throwable e) {
            fail("the method " + metodi + " caused something unexpected to happen, "
                    + "more info:\n" + e);
        }
        return -1;
    }

    private int indexOfTheSmallest(int[] t) {
        String metodi = "indexOfTheSmallest";

        try {
            String[] args = new String[0];
            Method m = ReflectionUtils.requireMethod(Main.class, metodi, int[].class);

            return ReflectionUtils.invokeMethod(int.class, m, null, t);
        } catch (ArrayIndexOutOfBoundsException e) {
            fail("the method " + metodi + " "
                    + "causes ArrayOutOfBounds exception with the input " + toS(t));
        } catch (Throwable e) {
            fail("the method " + metodi + " caused something unexpected to happen, "
                    + "more info:\n" + e);
        }
        return -1;
    }

    private String toS(int[] t) {
        return Arrays.toString(t).replace("[", "").replace("]", "");
    }

    private void noForbiddens() {
        try {
            Scanner lukija = new Scanner(new File("src/Main.java"));
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("Arrays.sort(")) {
                    fail("Since we're practicing writing a sorting algorithm, "
                            
                            + "your program should not use Arrays.sort()");
                }

                if (rivi.contains("ArrayList<")) {
                    fail("Since we practicing writing a sorting algorithm, "
                            + "your program should not use ArrayList");
                }
            }
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
