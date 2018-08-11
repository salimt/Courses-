
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NumberStatistics1Test {

    Random seka = new Random();
    String luokanNimi = "NumberStatistics";
    Class laskuriLuokka;
    Reflex.ClassRef<Object> klass;
    String klassName = "NumberStatistics";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("79.1")
    @Test
    public void classPublic() {
        assertTrue("Class " + klassName + " should be public. Define it as follows:\n"
                + "public class " + klassName + " {...\n}", klass.isPublic());
    }

    @Points("79.1")
    @Test
    public void testConstructor() throws Throwable {
        newOlio();
    }

    @Points("79.1")
    @Test
    public void hasMethodAddNumber() throws Throwable {
        String method = "addNumber";
        int param = 2;
        hasVoidMethodInt(newOlio(), method, param);
    }

    @Points("79.1")
    @Test
    public void addNumberWorks() throws Throwable {
        String method = "addNumber";
        int param = 2;

        Object olio = newOlio();

        assertTrue("Add class " + klassName + " method public void " + method + "(int number) ",
                klass.method(olio, method).returningVoid().taking(int.class).isPublic());

        String v = "\nError was caused by the code:\n"
                + klassName + " t = new " + klassName + "(); t." + method + "(" + param + ");";

        klass.method(olio, method).returningVoid().taking(int.class).withNiceError(v).invoke(2);
    }

    @Points("79.1")
    @Test
    public void hasMethodAmountOfNumbers() throws Throwable {
        hasMethod0(newOlio(), "amountOfNumbers", int.class);
    }

    @Points("79.1")
    @Test
    public void additionAndAmountWorks() throws Throwable {
        Object statistics = newNumberStatistics();

        assertEquals("Amount should be 0 at first. Check the code \n"
                + "statistics = new NumberStatistics(); System.out.println( statistics.amountOfNumbers()); ", 0, amountOfNumbers(statistics));

        addNumber(statistics, 3);

        assertEquals("Amount should grow. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.addNumber(3); System.out.println( statistics.amountOfNumbers()); ", 1, amountOfNumbers(statistics));

        addNumber(statistics, 5);
        addNumber(statistics, 2);
        addNumber(statistics, -4);

        assertEquals("Amount should grow. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4);System.out.println( statistics.amountOfNumbers()); ", 4, amountOfNumbers(statistics));
    }

    @Points("79.1")
    @Test
    public void manyAdditionsWork() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object statistics = newNumberStatistics();

            for (int luku : luvut) {
                addNumber(statistics, luku);
            }

            assertEquals("Added values " + toString(luvut) + " statistics.amountOfNumbers()", luvut.length, amountOfNumbers(statistics));
        }
    }

    @Points("79.1")
    @Test
    public void noExtraVariables1() {
        saniteettitarkastus();
    }

    /*
     * osa 2
     */
    @Points("79.2")
    @Test
    public void noExtraVariablesa2() {
        saniteettitarkastus();
    }

    @Points("79.2")
    @Test
    public void hasMethodSum() throws Throwable {
        hasMethod0(newOlio(), "sum", int.class);
    }

    @Points("79.2")
    @Test
    public void sumWorks() throws Throwable {
        Object statistics = newNumberStatistics();

        assertEquals("Sum should be at stars 0. Check the code \n"
                + "statistics = new NumberStatistics(); System.out.println( statistics.sum()); ", 0, sum(statistics));

        addNumber(statistics, 3);

        assertEquals("Sum should take into accout the added numbers. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.addNumber(3); System.out.println( statistics.sum()); ", 3, sum(statistics));

        addNumber(statistics, 5);
        addNumber(statistics, 2);

        assertEquals("Sum should take into accout the added numbers. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); System.out.println( statistics.sum()); ", 10, sum(statistics));


        addNumber(statistics, -4);

        assertEquals("Sum should take into accout the added numbers.  Check the code \n"
                + "statistics = new NumberStatistics(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4) System.out.println( statistics.sum()); ", 6, sum(statistics));
    }

    @Points("79.2")
    @Test
    public void bigSumWorks() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object statistics = newNumberStatistics();

            int sum = 0;
            for (int luku : luvut) {
                addNumber(statistics, luku);
                sum += luku;
            }

            assertEquals("After adding numbers " + toString(luvut) + " statistics.sum()", sum, sum(statistics));
        }
    }

    @Points("79.2")
    @Test
    public void hasMethodAverage() throws Throwable {
        hasMethod0(newOlio(), "average", double.class,
                "NOTE: if no numbers added to, average is zero!");
    }

    @Points("79.2")
    @Test
    public void averageToimii() throws Throwable {
        Object statistics = newNumberStatistics();

        try {
            assertEquals("Average should be zero at the start. Make sure that you are not dividing by zero! Check the code \n"
                    + "statistics = new NumberStatistics(); System.out.println( statistics.average()); ", 0, average(statistics), 0.01);
        } catch (Exception e) {
            fail("Average should be zero at the start. Make sure that you are not dividing by zero!  Check the code "
                    + "statistics = new NumberStatistics(); System.out.println( statistics.average()); ");
        }

        addNumber(statistics, 3);

        assertEquals("Average not correct. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.average(); statistics.addNumber(3); System.out.println( statistics.average()); ", 3, average(statistics), 0.01);

        addNumber(statistics, 5);
        addNumber(statistics, 2);

        assertEquals("Average not correct. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.average(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); System.out.println( statistics.average()); ", 3.333, average(statistics), 0.01);


        addNumber(statistics, -4);

        assertEquals("Average not correct. Check the code \n"
                + "statistics = new NumberStatistics(); statistics.average(); statistics.addNumber(3); statistics.addNumber(5); statistics.addNumber(2); statistics.addNumber(-4) System.out.println( statistics.average()); ", 1.5, average(statistics), 0.01);
    }

    @Points("79.2")
    @Test
    public void bigAverageWorks() throws Throwable {

        for (int i = 0; i < 5; i++) {
            int[] luvut = arvo(10 + seka.nextInt(10));

            Object statistics = newNumberStatistics();

            double sum = 0;
            for (int luku : luvut) {
                addNumber(statistics, luku);
                sum += luku;
            }
            double ka = sum / luvut.length;

            assertEquals("Values added " + toString(luvut) + " statistics.average()", ka, average(statistics), 0.01);
        }
    }

    /*
     * osa 3
     */
    @Points("79.3")
    @Test
    public void sumOfUserInput() throws Exception {
        MockInOut mio = new MockInOut("2\n-1\n");

        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            fail("Program should stop after the input -1");
        }
        String[] rivit = mio.getOutput().split("\n");
        assertTrue("Your program does not print anything", rivit.length > 0);
        assertTrue("At start your program should print \"Type numbers:\"", rivit[0].contains("ype numbers"));
        String sumRivi = hae(rivit, "Sum");
        assertTrue("Your program should print a line of the form \"Sum: 10\" where 10 is replaced by the calculated sum", sumRivi != null);

        assertTrue("With input 2 -1 your program should print Sum: 2, following was printer: " + sumRivi, sumRivi.contains("2"));
    }

    @Points("79.3")
    @Test
    public void sumOfUserInput2() {
        MockInOut mio = new MockInOut("2\n4\n1\n7\n-1\n");
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            fail("Program should stop after the input -1");
        }
        String[] rivit = mio.getOutput().split("\n");
        String sumRivi = hae(rivit, "sum");

        assertTrue("With input 2 4 1 7 -1 your program should print sum: 14, now it printed: " + sumRivi, sumRivi.contains("14"));
    }

    @Points("79.4")
    @Test
    public void evenAndOdd() {
        MockInOut mio = new MockInOut("2\n4\n1\n6\n-1\n");
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            fail("Program should stop after the input -1");
        }
        String[] rivit = mio.getOutput().split("\n");
        String sumRivi = hae(rivit, "sum");

        assertTrue("ensure that your program prints line of the form \"sum \"", sumRivi != null);
        assertTrue("With input 2 4 1 6 -1 your program should print sum: 13, now it printed: " + sumRivi, sumRivi.contains("13"));

        String parillisetRivi = hae(rivit, "even");
        assertTrue("ensure that your program prints line of the form \"sum of even \"", parillisetRivi != null);
        assertTrue("Your program should print a line of the form \"sum of even: 10\"  where 10 replaced by the counted sum of even numbers", parillisetRivi != null);
        assertTrue("With input 2 4 1 6 -1 your program should print sum of even: 12, now it printed: " + parillisetRivi, parillisetRivi.contains("12"));

        String parittomatRivi = hae(rivit, "odd");
        assertTrue("ensure that your program prints line of the form \"sum of odd \"", parittomatRivi != null);
        assertTrue("With input 2 4 1 6 -1 your program should print sum of odd 1, now it printed: " + parittomatRivi, parittomatRivi.contains("1"));
        assertTrue("Your program should print a line of the form \"sum of odd: 10\" where 10 is replaced by the counted sum of odd numbers", parittomatRivi != null);
    }

    /*
     *
     */
    private Object newNumberStatistics() {
        try {
            laskuriLuokka = ReflectionUtils.findClass(luokanNimi);
            return ReflectionUtils.invokeConstructor(laskuriLuokka.getConstructor());
        } catch (Throwable t) {
            fail("Check the following:  NumberStatistics statistics = new NumberStatistics();");
        }
        return null;
    }

    private void addNumberMock(Object olio, int luku) throws Throwable {
        Method method = ReflectionUtils.requireMethod(olio.getClass(), "addNumber", int.class);
        ReflectionUtils.invokeMethod(void.class, method, olio, luku);
    }

    private void addNumber(Object olio, int luku) throws Throwable {
        Method method = ReflectionUtils.requireMethod(laskuriLuokka, "addNumber", int.class);
        ReflectionUtils.invokeMethod(void.class, method, olio, luku);
    }

    private double average(Object olio) throws Throwable {
        Method method = ReflectionUtils.requireMethod(laskuriLuokka, "average");
        return ReflectionUtils.invokeMethod(double.class, method, olio);
    }

    private int sum(Object olio) throws Throwable {
        Method method = ReflectionUtils.requireMethod(laskuriLuokka, "sum");
        return ReflectionUtils.invokeMethod(int.class, method, olio);
    }

    private int sumMock(Object olio) throws Throwable {
        Method method = ReflectionUtils.requireMethod(olio.getClass(), "sum");
        return ReflectionUtils.invokeMethod(int.class, method, olio);
    }

    private int amountOfNumbers(Object olio) throws Throwable {
        Method method = ReflectionUtils.requireMethod(laskuriLuokka, "amountOfNumbers");
        return ReflectionUtils.invokeMethod(int.class, method, olio);
    }

    private String toString(Object kortti) throws Throwable {
        Method toString = ReflectionUtils.requireMethod(laskuriLuokka, "toString");
        return ReflectionUtils.invokeMethod(String.class, toString, kortti);
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
            assertTrue("The class " + luokanNimi + " needs only instance variables for the amount and sum of numbers, remove the extra "
                    + "", var < 3);
        }
    }

    private String toString(int[] luvut) {
        String mj = "";
        for (int luku : luvut) {
            mj += luku + " ";
        }
        return mj;
    }

    private String enter(int[] luvut) {
        String mj = "";
        for (int luku : luvut) {
            mj += luku + "\n";
        }
        return mj + "-1\n";
    }

    private int[] arvo(int lkm) {
        int[] luvut = new int[lkm];

        for (int i = 0; i < luvut.length; i++) {
            luvut[i] = seka.nextInt(30);
        }

        return luvut;
    }

    private String hae(String[] rivit, String sana) {
        for (String rivi : rivit) {
            if (rivi.toLowerCase().contains(sana.toLowerCase())) {
                return rivi;
            }
        }

        return null;
    }

    private Object newOlio() throws Throwable {
        Reflex.MethodRef0<Object, Object> ctor = klass.constructor().takingNoParams().withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "()", ctor.isPublic());
        return ctor.invoke();
    }

    private void hasMethod0(Object olio, String name, Class<?> returns) throws Throwable {
        hasMethod0(olio, name, returns, "");
    }

    private void hasMethod0(Object olio, String name, Class<?> returns, String msg) throws Throwable {
        String paluu = returns.toString();
        String muuttuja = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("Add class " + klassName + " method public " + paluu + " " + name + "()",
                klass.method(olio, name).returning(returns).takingNoParams().isPublic());

        String v = "\nError was caused by the code:\n"
                + klassName + " " + muuttuja + " = new " + klassName + "(); " + muuttuja + "." + name + "();";

        if (!msg.isEmpty()) {
            msg = "\n" + msg;
        }

        klass.method(olio, name).returning(returns).takingNoParams().withNiceError(v + msg).invoke();
    }

    private void hasVoidMethodInt(Object olio, String name, int v1) throws Throwable {
        String muuttuja = ("" + klassName.charAt(0)).toLowerCase();

        assertTrue("Add class " + klassName + " method public void " + name + "(int number) ",
                klass.method(olio, name).returningVoid().taking(int.class).isPublic());

        String v = "\nError was caused by the code:\n"
                + klassName + " " + muuttuja + " = new " + klassName + "(); " + muuttuja + "." + name + "(" + v1 + ");";

        klass.method(olio, name).returningVoid().taking(int.class).withNiceError(v).invoke(v1);

    }

    private String kentta(String toString) {
        return toString.replace(klassName + ".", "");
    }
}
