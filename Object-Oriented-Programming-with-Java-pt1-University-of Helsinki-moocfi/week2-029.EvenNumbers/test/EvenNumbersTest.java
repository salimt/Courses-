import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import org.junit.*;
import static org.junit.Assert.*;

@Points("29")
public class EvenNumbersTest {
    
    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        ReflectionUtils.newInstanceOfClass(EvenNumbers.class);
        EvenNumbers.main(new String[0]);
        String tulostus = io.getSysOut().trim();
        String[] rivit = tulostus.split("\\s+");
        if (rivit.length != 50) {
            fail("Your output should contain 50 lines.");
        }

        if (!"2".equals(rivit[0])) {
             fail("First line should be 2, now it was " + rivit[0]);
        }

        if (!"50".equals(rivit[24])) {
            fail("25'th line should be 50, now it was " + rivit[24]);
        }

        if (!"100".equals(rivit[rivit.length - 1])) {
            fail("Last line should be 100, now it was " + rivit[rivit.length - 1]);
        }
    }
}
