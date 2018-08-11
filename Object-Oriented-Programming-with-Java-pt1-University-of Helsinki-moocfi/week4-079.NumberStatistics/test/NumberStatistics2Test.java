
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import static org.powermock.api.easymock.PowerMock.*;

@PrepareForTest({Main.class, NumberStatistics.class})
public class NumberStatistics2Test {
    Class laskuriLuokka;

    @Rule
    public PowerMockRule p = new PowerMockRule();
        @Points("79.4")
    @Test
    public void ObjectsUsed() throws Throwable {
        MockInOut mio = new MockInOut("2\n-1\n");

        NumberStatistics summa = createMock(NumberStatistics.class);
        NumberStatistics parilliset = createMock(NumberStatistics.class);
        NumberStatistics parittomat = createMock(NumberStatistics.class);

        //summa.lisaaLuku(2);
        lisaaLukuMock(summa, 2);
        //summa.summa();
        summaMock(summa);
        expectLastCall().andReturn(2);

        //parilliset.lisaaLuku(2);
        lisaaLukuMock(parilliset, 2);
        //parilliset.summa();
        summaMock(parilliset);
        expectLastCall().andReturn(2);

        //parittomat.summa();
        summaMock(parittomat);
        expectLastCall().andReturn(0);

        expectNew(NumberStatistics.class).andReturn(summa);
        expectNew(NumberStatistics.class).andReturn(parilliset);
        expectNew(NumberStatistics.class).andReturn(parittomat);

        replay(summa, NumberStatistics.class);
        replay(parilliset, NumberStatistics.class);
        replay(parittomat, NumberStatistics.class);

        try {
            Main.main(new String[0]);
            verifyAll();
        } catch (Exception e) {
            fail("Program should stop after the input -1");
        } catch (Throwable t) {
            fail("You should use NumberStaticstics objects to track the sum of input and also the sum of even and odd numbers in the input\n"
                    + "Objects should be created in the following orded:\n"
                    + "  The first is used to track sum of all given numbers \n"
                    + "  The second takes care of even numbers and the third the odd numbers. \n"
                    + "NOTE: do not add other values to objects that user entered\n"
                    + "      Remember: number -1 which is used to stop the program shouldn't be included in the statistics!\n"
                    + "Error happened with user input 2 -1\n"
                    + "more info: "+t);
        }
    }


    private void lisaaLukuMock(Object olio, int luku) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(olio.getClass(), "addNumber", int.class);
        ReflectionUtils.invokeMethod(void.class, metodi, olio, luku);
    }

    private void lisaaLuku(Object olio, int luku) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "lisaaLuku", int.class);
        ReflectionUtils.invokeMethod(void.class, metodi, olio, luku);
    }

    private int summa(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(laskuriLuokka, "sum");
        return ReflectionUtils.invokeMethod(int.class, metodi, olio);
    }

    private int summaMock(Object olio) throws Throwable {
        Method metodi = ReflectionUtils.requireMethod(olio.getClass(), "sum");
        return ReflectionUtils.invokeMethod(int.class, metodi, olio);
    }
}
