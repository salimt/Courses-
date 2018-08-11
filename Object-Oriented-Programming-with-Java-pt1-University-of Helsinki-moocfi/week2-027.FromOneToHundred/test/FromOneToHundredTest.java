
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("27")
public class FromOneToHundredTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        ReflectionUtils.newInstanceOfClass(FromOneToHundred.class);
        FromOneToHundred.main(new String[0]);
        String tulostus = io.getSysOut().trim();
        String[] rivit = tulostus.split("\\s+");
        if (rivit.length != 100) {
            fail("Your output should contain 100 lines.");
        }

        if (!"1".equals(rivit[0])) {
            fail("First line should be 1, now it was " + rivit[0]);
        }

        if (!"50".equals(rivit[49])) {
            fail("50'th line should be 50, now it was " + rivit[49]);
        }

        if (!"100".equals(rivit[rivit.length - 1])) {
            fail("Last line should be 100, now it was " + rivit[rivit.length - 1]);
        }
    }
}
