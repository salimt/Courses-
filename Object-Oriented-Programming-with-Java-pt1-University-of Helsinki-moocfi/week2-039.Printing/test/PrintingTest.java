
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.powermock.api.easymock.PowerMock.*;

import java.lang.reflect.Field;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(Printing.class)
public class PrintingTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Points("39.1 39.2 39.3 39.4")
    @Test
    public void noVariablesTest() {
        Field[] fs = Printing.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("You have variable " + fs[0].getName() + " in the class Printing, please remove that");
        }
    }

    @Test
    @Points("39.1")
    public void printStarsTest1() {
        MockInOut mio = new MockInOut("");
        Printing.printStars(3);
        String out = sanitize(mio.getOutput());
        assertTrue("You did not print \"*\" when printStars was called", out.contains("*"));
        assertTrue("You did not print a line break when printStars was called", out.contains("\n"));
        assertEquals("Problem in printStars(3)", "***\n", out);
        mio.close();
    }

    @Test
    @Points("39.1")
    public void printStarsTest2() {
        MockInOut mio = new MockInOut("");
        Printing.printStars(7);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in printStars(7).", "*******", out.trim());
    }

    @Test
    @Points("39.2")
    public void printSquareTest1() {
        MockInOut mio = new MockInOut("");
        Printing.printSquare(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in printSquare(3).", "***\n***\n***", out.trim());
    }

    @Test
    @Points("39.2")
    public void printSquareTest2() {

        mockStaticPartial(Printing.class, "printStars");
        Printing.printStars(4);
        Printing.printStars(4);
        Printing.printStars(4);
        Printing.printStars(4);

        replay(Printing.class);

        try {
            Printing.printSquare(4);
            verifyAll();
        } catch (AssertionError e) {
            fail("The method call printSquare(4) should call four times printStars(4). More info: " + e);
        }
    }

    @Test
    @Points("39.3")
    public void printRectangleTest1() {
        MockInOut mio = new MockInOut("");
        Printing.printRectangle(4, 2);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in printRectangle(4,2).", "****\n****", out.trim());
    }

    @Test
    @Points("39.3")
    public void printRectangleTest2() {

        mockStaticPartial(Printing.class, "printStars");
        Printing.printStars(5);
        Printing.printStars(5);
        Printing.printStars(5);
        Printing.printStars(5);

        replay(Printing.class);

        try {
            Printing.printRectangle(5, 4);
            verifyAll();
        } catch (AssertionError e) {
            fail("\"The method call printRectangle(5,4) should call four times printStars(5). More info: " + e);
        }
    }

    @Test
    @Points("39.4")
    public void printTriangleTest1() {
        MockInOut mio = new MockInOut("");
        Printing.printTriangle(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in printTriangle(3).", "*\n**\n***", out.trim());
    }

    @Test
    @Points("39.4")
    public void printTriangleTest2() {

        mockStaticPartial(Printing.class, "printStars");
        Printing.printStars(1);
        Printing.printStars(2);

        replay(Printing.class);

        try {
            Printing.printTriangle(2);
            verifyAll();
        } catch (AssertionError e) {
            if (e.toString().contains("Unexpected method call Tulostelua.printStars(0):")) {
                fail("The method call printTriangle(2) should call first printStars(1) and then printStars(2). "
                        + "\nHowever you called first printTriangle(0). "
                        + "Change your program so that this unnecessary call does not happen");
            }
            fail("The method call printTriangle(2) should call first printStars(1) and then printStars(2). "
                    + "\n" + e);
        }
    }

    @Test
    @Points("39.4")
    public void printTriangleTest3() {

        mockStaticPartial(Printing.class, "printStars");
        Printing.printStars(1);
        Printing.printStars(2);
        Printing.printStars(3);
        Printing.printStars(4);

        replay(Printing.class);

        try {
            Printing.printTriangle(4);
            verifyAll();
        } catch (AssertionError e) {
            fail("Method call printRectangle(4) should call  printStars(1),  printStars(2),  printStars(3) and  printStars(4)" + e);
        }
    }
}
