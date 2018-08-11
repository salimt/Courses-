
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

@Points("72.2")
//@RunWith(PowerMockRunner.class)
@PrepareForTest({Accounts.class, Account.class})
public class TilejaTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void testa() throws Exception {
        Account matinTili = createMock(Account.class);
        Account omaTili = createMock(Account.class);

        expectNew(Account.class, "Matt's account", 1000.0).andReturn(matinTili);
        expectNew(Account.class, "My account", 0.0).andReturn(omaTili);

        matinTili.withdrawal(100.0);
        omaTili.deposit(100.0);

        replay(matinTili, Account.class);
        replay(omaTili, Account.class);

        try {
            Accounts.main(new String[0]);
            verify(matinTili, Account.class);
            verify(omaTili, Account.class);

        } catch (Throwable t) {
            String virhe = t.getMessage();
            if (virhe.contains("Matt's account")) {
                fail("Create account with parameters \"Matt's account\", 1000.0");
            } else if (virhe.contains("My  acc")) {
                fail("Create account with parameters \"My account\", 0.0");
            } else if (virhe.contains("withdrawal")) {
                fail("Create account with parameters \"Matt's account\", 1000.0 and withdraw 100.0");
            } else if (virhe.contains("deposit")) {
                fail("Create account with parameters  \"My account\", 0.0 and deposit 100.0");
            }
            fail("odottamaton tilanne:\n" + virhe);
        }
    }

    @Test
    public void testToString() throws Exception {
        MockInOut mio = new MockInOut("");

        Accounts.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("After the money transfer, your program should print info on Matt's account", out.contains("900.0"));
        assertTrue("After the money transfer, your program should print info on My account", out.contains("100.0"));        
        mio.close();

    }
}
