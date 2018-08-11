
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Rule;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;
import org.junit.Before;
import static org.powermock.api.easymock.PowerMock.*;
import org.powermock.reflect.exceptions.MethodNotFoundException;

@Points("72.3")
@PrepareForTest({Accounts.class, Account.class})
public class B_Test<_Tileja, _Account> {

    @Rule
    public PowerMockRule p = new PowerMockRule();
    
    String metodinNimi = "transfer";

    String klassName = "Accounts";
    
    Method metodi;    



    @Test
    public void testaaToString() throws Exception {
        Account a = createMock(Account.class);
        Account b = createMock(Account.class);
        Account c = createMock(Account.class);

        expectNew(Account.class, EasyMock.anyString(), EasyMock.eq(100.0)).andReturn(a);
        expectNew(Account.class, EasyMock.anyString(), EasyMock.eq(0.0)).andReturn(b);
        expectNew(Account.class, EasyMock.anyString(), EasyMock.eq(0.0)).andReturn(c);

        mockStaticPartial(Accounts.class, metodinNimi);
        reset(Accounts.class);

        metodi = ReflectionUtils.requireMethod(Accounts.class, metodinNimi, Account.class, Account.class, double.class);

        metodi.invoke(
                null, a, b, 50.0);
        metodi.invoke(
                null, b, c, 25.0);

        replay(Accounts.class);
        replay(a, Account.class);
        replay(b, Account.class);
        replay(c, Account.class);

        try {
            Accounts.main(new String[0]);
            verifyAll();
        } catch (Throwable e) {
            fail("Create three accounts with initial balance 100, 0 and 0. \n"
                    + "Use method \"transfer\" to at the start transfer 50 "
                    + "from first accound to the second\nand "
                    + "then 25 from the second account to the third.");
        }
    }
    
    private void kutsu(Account milta, Account mille, double summa) throws Throwable {
        metodi = ReflectionUtils.requireMethod(Accounts.class, metodinNimi, Account.class, Account.class, double.class);
        metodi.invoke(null, milta, mille, summa);
    }
}
