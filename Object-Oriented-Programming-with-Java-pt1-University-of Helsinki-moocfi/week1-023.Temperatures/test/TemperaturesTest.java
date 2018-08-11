
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.powermock.api.easymock.PowerMock.*;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import java.util.NoSuchElementException;
import junit.framework.AssertionFailedError;
import org.junit.Before;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(Graph.class)
public class TemperaturesTest {

    @Rule
    public MockStdio io = new MockStdio();
    Graph k;

    @Before
    public void before() {
        k = createStrictMock(Graph.class);
        Graph.instance = k;
    }

    void ajaMain() {
        replayAll();
        try {
            Temperatures.main(new String[0]);
        } catch (NoSuchElementException e) {
            // pass
        }
    }

    void tarkasta(String viesti) {
        try {
            ajaMain();
            verifyAll();
        } catch (AssertionError t) {
            fail(viesti + " More info: " + t);
        } catch (Throwable t) {
            fail("Something went wrong: "+t);
        }
    }

    @Points("23.1")
    @Test
    public void testOne() {
        io.setSysIn("3\n");
        k.lisaaNumero_(3);
        tarkasta("Your program did not call Graph.addNumber(3) when input was \"3\".");
    }

    @Points("23.1")
    @Test
    public void testMany() {
        io.setSysIn("3\n4\n5\n");

        //String out = io.getSysOut();

        k.lisaaNumero_(3);
        k.lisaaNumero_(4);
        k.lisaaNumero_(5);

        tarkasta("Your program did not call Graph.addNumber() correctly fot inputs 3, 4 and 5.");
    }

    @Points("23.2")
    @Test
    public void testPartTwoToSmall() {
        io.setSysIn("-1000\n");
        tarkasta("You should not add -1000 to graph.");
    }

    @Points("23.2")
    @Test
    public void testPartTwoToBig() {
        io.setSysIn("1000\n");
        tarkasta("You should not add 1000 to graph.");
    }

    @Points("23.2")
    @Test
    public void testAll() {
        io.setSysIn("0\n-31\n-40\n15\n16\n39\n41\n49\n0\n");
        k.lisaaNumero_(0);
        k.lisaaNumero_(15);
        k.lisaaNumero_(16);
        k.lisaaNumero_(39);
        k.lisaaNumero_(0);
        tarkasta("Given the input 0, -31, -40, 15, 16, 39, 41, 49, 0, you should add to graph only values 0, 15, 16, 39, 0.");
    }
}   
   
