
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;

@Points("28")
public class FromHundredToOneTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testi() {
        ReflectionUtils.newInstanceOfClass(FromHundredToOne.class);
        FromHundredToOne.main(new String[0]);
        String tulostus = io.getSysOut().trim();
        String[] rivit = tulostus.split("\\s+");
        if (rivit.length != 100) {
            fail("Your output should contain 100 lines.");
        }

        if (!"100".equals(rivit[0])) {
             fail("First line should be 100, now it was " + rivit[0]);
        }

        if (!"51".equals(rivit[49])) {
            fail("50'th line should be 51, now it was " + rivit[49]);
        }

        if (!"1".equals(rivit[rivit.length - 1])) {
            fail("Last line should be 1, now it was " + rivit[rivit.length - 1]);
 
        }
    }
}
