
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;

import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.powermock.api.easymock.PowerMock.*;

import java.lang.reflect.Field;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(PrintingLikeBoss.class)
public class PrintingLikeBossTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    public String sanitize(String s) {
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    @Points("40.1 40.2 40.3")
    public void noVariablesTest() {
        Field[] fs = PrintingLikeBoss.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("You have variable " + fs[0].getName() + " in the class PrintingLikeBoss, please remove that");
        }
    }

    @Test
    @Points("40.1")
    public void printStarsTest1() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.printStars(3);
        String out = sanitize(mio.getOutput());
        assertTrue("You did not print \"*\" when printStars was called", out.contains("*"));
        assertTrue("You did not print a line break when printStars was called", out.contains("\n"));
        assertEquals("Problem in printStars(3)", "***\n", out);
        mio.close();
    }

    @Test
    @Points("40.1")
    public void testPrintWhitespaces1() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.printWhitespaces(1);
        String out = sanitize(mio.getOutput());
        assertEquals("Call printWhitespaces(1) should print only one character, now it printed", 1, out.length());
        assertFalse("Call printWhitespaces(1) should print line break, now that happened", out.contains("\n"));
        assertEquals("Problem in printWhitespaces(1)", " ", out);
        mio.close();
    }

    @Test
    @Points("40.1")
    public void testPrintWhitespaces2() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.printWhitespaces(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Call printWhitespaces(1) should print 3 characters, now it printed", 3, out.length());
        assertFalse("Call printWhitespaces(1) should print line break, now that happened", out.contains("\n"));
        assertEquals("Problem in printWhitespaces(3)", "   ", out);
        mio.close();
    }

    @Test
    @Points("40.2")
    public void printTriangleTest1() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.printTriangle(1);
        String out = sanitize(mio.getOutput());
        assertFalse("Do not put a extra whitespace at the start of the line in printTriangle", out.startsWith(" "));
        assertEquals("Problem in printTriangle(1)", "*\n", out);
        mio.close();
    }

    @Test
    @Points("40.2")
    public void printTriangleTest2() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.printTriangle(3);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in printTriangle(3)", "  *\n **\n***\n", out);
        mio.close();
    }

    @Test
    @Points("40.2")
    public void printTriangleTest3() {
        mockStaticPartial(PrintingLikeBoss.class, "printStars", "printWhitespaces");
        reset(PrintingLikeBoss.class);

        PrintingLikeBoss.printWhitespaces(3);
        PrintingLikeBoss.printStars(1);
        PrintingLikeBoss.printWhitespaces(2);
        PrintingLikeBoss.printStars(2);
        PrintingLikeBoss.printWhitespaces(1);
        PrintingLikeBoss.printStars(3);
        PrintingLikeBoss.printWhitespaces(0);
        //expectLastCall().anyTimes();
        PrintingLikeBoss.printStars(4);

        replay(PrintingLikeBoss.class);

        try {
            PrintingLikeBoss.printTriangle(4);
            verifyAll();
        } catch (AssertionError e) {
            fail("The method call printTriangle should call methods printWhitespaces and printStars. More info: " + e);
        }
    }

    @Test
    @Points("40.3")
    public void xmasTreeTest1() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.xmasTree(4);
        String out = sanitize(mio.getOutput());
        assertTrue("the first line printed by call xmasTree(4) should have 3 whitespaces followed by a start, check out the amout of space you print!",
                out.startsWith("   *"));

        mio.close();
    }

    @Test
    @Points("40.3")
    public void xmasTreeTest2() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.xmasTree(4);
        String out = sanitize(mio.getOutput());
        assertEquals("Problem in xmasTree(4)",
                "   *\n  ***\n *****\n*******\n  ***\n  ***\n", out);

        mio.close();
    }

    @Test
    @Points("40.3")
    public void xmasTreeTest3() {
        MockInOut mio = new MockInOut("");
        PrintingLikeBoss.xmasTree(7);
        String out = sanitize(mio.getOutput());
        assertEquals("\"Problem in xmasTree(7)",
                "      *\n"
                + "     ***\n"
                + "    *****\n"
                + "   *******\n"
                + "  *********\n"
                + " ***********\n"
                + "*************\n"
                + "     ***\n"
                + "     ***\n",
                out);

        mio.close();
    }

    @Test
    @Points("40.3")
    public void xmasTreeTest4() {
        MockInOut mio = new MockInOut("");

        mockStaticPartial(PrintingLikeBoss.class, "printStars", "printWhitespaces");
        reset(PrintingLikeBoss.class);
        PrintingLikeBoss.printStars(1);
        PrintingLikeBoss.printStars(3);
        PrintingLikeBoss.printStars(5);
        PrintingLikeBoss.printStars(7);
        PrintingLikeBoss.printStars(9);
        PrintingLikeBoss.printStars(3);
        PrintingLikeBoss.printStars(3);

        PrintingLikeBoss.printWhitespaces(0);
        //expectLastCall().anyTimes();
        PrintingLikeBoss.printWhitespaces(4);
        PrintingLikeBoss.printWhitespaces(3);
        PrintingLikeBoss.printWhitespaces(2);
        PrintingLikeBoss.printWhitespaces(1);
        PrintingLikeBoss.printWhitespaces(3);
        PrintingLikeBoss.printWhitespaces(3);

        replay(PrintingLikeBoss.class);

        try {
            PrintingLikeBoss.xmasTree(5);
            verifyAll();
        } catch (AssertionError e) {
            fail("Do not print anything in method xmasTree. "
                + "Use only methods printWhitespace and printStars in printing!"
                    + "Now something is wrong. More info: " + e);
        }

        assertEquals("Do not print anything in method xmasTree. "
                + "Use only methods printWhitespace and printStars in printing!"
                + "Now you printed: \"" + mio.getOutput() + "\"", "", mio.getOutput());

        mio.close();
    }
}
