
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

@Points("20")
public class UsernamesTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void goodAccepted() {
        String[][] tunnusSalasana = {{"alex", "mightyducks"}, {"emily", "cat"}};

        for (String[] pari : tunnusSalasana) {
            sopivaKay(pari[0], pari[1]);
        }
    }

    @Test
    public void badRejected() {
        String[][] tunnusSalasana = {
            {"peter", "secret"},
            {"alex", ""},
            {"alex", "badducks"},
            {"Emily", "dog"},
            {"emily", "dog"},
            {"", "cat"}
        };

        for (String[] pari : tunnusSalasana) {
            sopimatonEiKay(pari[0], pari[1]);
        }
    }

    private void sopivaKay(String k, String s) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(k + "\n" + s + "\n");
        callMain(Usernames.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("You should print something", out.length() > 0);

        assertTrue("With input username: \"" + k + "\" password: \"" + s +
                " you should print \"You are now logged into the system!\", you printed \"" + out + "\". Remember that Strings can not be compared with ==, equals() should be used instead", out.toLowerCase().contains("ogged"));;
        assertTrue("With input username: \"" + k + "\" password: \"" + s +
                " you should print \"You are now logged into the system!\", you printed \"" + out + "\". Remember that Strings can not be compared with ==, equals() should be used instead", !out.toLowerCase().contains("nvali"));
    }

    private void sopimatonEiKay(String k, String s) {
        int oldOut = io.getSysOut().length();
        io.setSysIn(k + "\n" + s + "\n");
        callMain(Usernames.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("You should print something!", out.length() > 0);

        assertTrue("With input username: \"" + k + "\" password: \"" + s + "\" "
                +  "you should print \"Your username or password was invalid!\", you printed \"" + out + "\". Remember that Strings can not be compared with ==, equals() should be used instead", !out.toLowerCase().contains("ogged"));
        assertTrue("With input username: \"" + k + "\" password: \"" + s + "\" "
                +  "you should print \"Your username or password was invalid!\", you printed \"" + out + "\". Remember that Strings can not be compared with ==, equals() should be used instead ", out.toLowerCase().contains("nvali"));
    }

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        }  catch (NoSuchElementException e) {
            fail("Make sure that you read the user input with command reader.nextLine()");
        } catch (Throwable e) {
            fail("Something unexpected happened, more info: "+e);
        }
    }
}
