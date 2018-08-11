import java.lang.reflect.Constructor;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;

public class ConstructorSignature {
    
    public Class[] args;
    
    public ConstructorSignature(Class... args) {
        this.args = args;
    }
    
    public Constructor findIn(Class cl) {
        try {
            return ReflectionUtils.requireConstructor(cl, args);
        } catch (AssertionError e) {
            fail("Does class "+cl.getSimpleName()+" have constructor public "+toSignature(cl.getSimpleName())+"?");
            return null;
        }
    }

    public String toSignature(String className) {
        StringBuilder b = new StringBuilder();
        b.append(className).append("(");
        for (Class c : args) {
                b.append(c.getSimpleName()).append(", ");
        }
        if (args.length > 0) {
            b.delete(b.length()-2,b.length());
        }
        b.append(")");
        return b.toString();
    }

    public Object invokeIn(Class cl, Object... args) {

        Constructor c = findIn(cl);

        try {
            return ReflectionUtils.invokeConstructor(c,args);
        } catch (AssertionError e) {
            throw e;
        } catch (Throwable t) {
            throw new Error("Something went wrong whem calling constructor "+
                            toSignature(cl.getSimpleName())+":\n",
                            t);
        }

    }

}
