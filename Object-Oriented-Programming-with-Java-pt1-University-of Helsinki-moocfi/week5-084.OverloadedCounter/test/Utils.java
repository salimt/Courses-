import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;

public class Utils {
    public static Class getClass(String name) {
        try {
            return ReflectionUtils.findClass(name);
        } catch (AssertionError e) {
            fail("Have you added class "+name+"?");
            return null;
        }
    }
}
