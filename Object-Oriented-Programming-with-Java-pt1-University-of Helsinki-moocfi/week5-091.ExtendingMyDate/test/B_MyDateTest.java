
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

//@RunWith(PowerMockRunner.class)
@PrepareForTest({MyDate.class})
public class B_MyDateTest {

    @Points("91.2")
    @Test
    public void test1() {
        Class c = MyDate.class;
        String method = "advance";
        String virhe = "Add class MyDate method public void advance(int numberOfDays)";
        int montakoPaivaaEteenpain = 3;

        Method parametritonEtene = null;
        try {
            parametritonEtene = ReflectionUtils.requireMethod(c, method);
        } catch (Throwable t) {
            fail(virhe);
        }

        MyDate mockPaivays = PowerMock.createMock(MyDate.class, parametritonEtene);

        try {
            parametritonEtene.invoke(mockPaivays);
        } catch (Throwable t) {
            fail(virhe + ", ensure that the method is public.");
        }
        PowerMock.expectLastCall().times(montakoPaivaaEteenpain);

        PowerMock.replay(mockPaivays);

        Method parametrillinenEtene = null;
        try {
            parametrillinenEtene = ReflectionUtils.requireMethod(mockPaivays.getClass(), method, int.class);
        } catch (Throwable t) {
            fail("Ensure that the method advance() is called from the method advance(int numberOfDays). Ensure that the parameterless method is called numberOfDays times!.");
        }
        try {
            parametrillinenEtene.invoke(mockPaivays, montakoPaivaaEteenpain);
        } catch (Throwable t) {
            fail("Ensure that the method advance() is called from the method advance(int numberOfDays). Ensure that the parameterless method is called numberOfDays times!.");
        }

        try {
            PowerMock.verify(mockPaivays);
        } catch (Throwable t) {
            fail("Ensure that the method advance() is called from the method advance(int numberOfDays). Ensure that the parameterless method is called numberOfDays times!." + t);
        }
    }
}
