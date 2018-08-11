
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.*;
import static org.junit.Assert.*;

@Points("19")
public class AgeCheckTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void goodAgeAccepted() {
        int[] sopivat = {0, 1, 10, 85, 120};
        for (int ika : sopivat) {
            testaaJaTarkastaSopivaIka(ika);    
        }       
    }

    @Test
    public void badAgeNotAccepted() {
        int[] sopivattomat = {-100, -1, 121, 1000};
        for (int ika : sopivattomat) {
            testaaJaTarkastaSopimatonIka(ika);    
        }       
    }    
    
    private void testaaJaTarkastaSopivaIka(int ika) {
        int oldOut = io.getSysOut().length(); 
        io.setSysIn(ika+"\n");
        callMain(AgeCheck.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("You should print something", out.length() > 0);
        assertTrue("With input "+ika+" you should print \"OK\", you printed " + out, out.toLowerCase().contains("ok"));        
        assertTrue("With input "+ika+" you should print \"OK\", you printed " + out, !out.toLowerCase().contains("mpos"));
    }
    
    private void testaaJaTarkastaSopimatonIka(int ika) {
        int oldOut = io.getSysOut().length(); 
        io.setSysIn(ika+"\n");
        callMain(AgeCheck.class);
        String out = io.getSysOut().substring(oldOut);

        assertTrue("You should print something", out.length() > 0);
        assertTrue("With input "+ika+" you should print \"Impossible\", you printed " + out, out.toLowerCase().contains("mpos"));
        assertTrue("With input "+ika+" you should print \"Impossible\", you printed " + out, !out.toLowerCase().contains("ok"));
    }    

    private void callMain(Class kl) {
        try {
            kl = ReflectionUtils.newInstanceOfClass(kl);
            String[] t = null;
            String x[] = new String[0];
            Method m = ReflectionUtils.requireMethod(kl, "main", x.getClass());
            ReflectionUtils.invokeMethod(Void.TYPE, m, null, (Object) x);
        } catch (Throwable e) {
            fail("Something unexpected happened, more info "+e);
        }
    }
}
