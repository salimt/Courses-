import java.lang.reflect.Method;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;

public class MethodSignature {
    
    public String name;
    public Class retType;
    public Class[] args;
    
    public MethodSignature(Class retType, String name, Class... args) {
        this.retType = retType;
        this.name = name;
        this.args = args;
    }
    
    public Method findIn(Class cl) {
        Method m;
        try {
            m = ReflectionUtils.requireMethod(cl, name, args);
        } catch (AssertionError e) {
            fail("Does class "+cl.getSimpleName()+" have method public "+toSignature()+"?");
            return null;
        }

        assertEquals("Check that class "+cl.getSimpleName()+" method "+
                     toSignature()+" has right return type!",retType, m.getReturnType());
        return m;

    }

    public String toSignature() {
        StringBuilder b = new StringBuilder();
        b.append(retType.getSimpleName()).append(" ").append(name).append("(");
        for (Class c : args) {
                b.append(c.getSimpleName()).append(", ");
        }
        if (args.length > 0) {
            b.delete(b.length()-2,b.length());
        }
        b.append(")");
        return b.toString();
    }

    public Object invokeIn(Class cl, Object o, Object... args) {
        Method m = findIn(cl);

        try {
            return ReflectionUtils.invokeMethod(retType,m,o,args);
        } catch (AssertionError e) {
            throw e;
        } catch (Throwable t) {
            throw new Error("Something went wrong when called "+toSignature()+":",t);
        }

    }
    
}
