
import fi.helsinki.cs.tmc.edutestutils.Points;
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

@Points("72.1")
//@RunWith(PowerMockRunner.class)
@PrepareForTest({Accounts.class, Account.class})
public class AccountsTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void testaa() throws Exception {
        Account tiliMock = createMock(Account.class);

        expectNew(Account.class, EasyMock.anyString(), EasyMock.eq(100.0)).andReturn(tiliMock);

        tiliMock.deposit(20.0);
        replay(tiliMock, Account.class);

        try {
            Accounts.main(new String[0]);
            verify(tiliMock, Account.class);

        } catch (Throwable t) {
            String virhe = t.getMessage();
            if (virhe.contains("deposit")) {
                fail("create an account and do a deposit of 20");
            } else if (virhe.contains("constructor")) {
                fail("remember to create account with initial balance of 100.0, "
                        + "the value should be in the constructor parameter");
            }
            fail("Something unexpected happened:\n" + virhe);
        }
    }

    @Test
    public void testaaToString() throws Exception {
        MockInOut mio = new MockInOut("");

        Accounts.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("you should print the account info by calling System.out.println(account); \n"
                + "where account is the variable that holds the account object", out.contains("120.0"));
        mio.close();

    }
}
