import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import java.lang.reflect.Field;

@Points("37")
public class PrintingOutTextTest {
    
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void noVariables() {
        Field[] fs = PrintingOutText.class.getDeclaredFields();
        if (fs.length!=0) {
            fail("You have variable "+fs[0].getName()+" in the class PrintingOutText, please remove that");
        }
    }

    @Test 
    public void printsSomething() {
        PrintingOutText.printText();
        assertFalse("You should print something", io.getSysOut().isEmpty());
    }

    @Test
    public void printsRightString() {
        PrintingOutText.printText();
        assertEquals("You did not print the right string", "In the beginning there were the swamp, the hoe and Java.", io.getSysOut().trim());
    }
}
