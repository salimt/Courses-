
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
@Points("72.3")
public class A_Test<_Accounts, _Account> {

    String metodinNimi = "transfer";

    String klassName = "Accounts";
    
    Reflex.ClassRef<_Account> _AccountClassRef;
    Reflex.ClassRef<_Accounts> _AccountsClassRef;

    Method metodi;
    
    @Before
    public void setUp() {
        // Alustettava täällä luultavasti PowerMockin takia.
        _AccountClassRef = Reflex.reflect("Account");
        _AccountsClassRef = Reflex.reflect("Accounts");
    }
    
    @Test
    public void methodTransferExists() throws Throwable {
        // Tässä riittäisi myös käyttää Class[Ref]<Account> ja Class[Ref]<Accounts> suoraan.
        
        assertTrue("Class " + klassName + " should have the method public static void " + metodinNimi + "(Account from, Account to, double amount) ", 
                _AccountsClassRef.staticMethod(metodinNimi).returningVoid().
                                                 taking(ReflectionUtils.findClass("Account"),ReflectionUtils.findClass("Account"), double.class).isPublic());
        
        String v = "\nCode that caused the error "
                + "Account t1 = new Account(\"Matt\",10.0); "
                + "Account t2 = new Account(\"Pekka\",0.0);"
                + "transfer(t1, t2, 5.0); ";
        
        _Account t1 = _AccountClassRef.constructor().taking(String.class, double.class).invoke("Matt", 10.0);
        _Account t2 = _AccountClassRef.constructor().taking(String.class, double.class).invoke("Pekka", 0.0);
        
        Class<_Account> c = _AccountClassRef.cls();
        
        _AccountsClassRef
                .staticMethod(metodinNimi)
                .returningVoid()
                .taking(c, c, double.class)
                .withNiceError(v)
                .invoke(t1, t2, 5.0);

    }

    @Test
    public void testa() throws Exception {
        Account eka = new Account("eka", 1000);
        Account toka = new Account("toka", 0);
        double summa = 100;

        String virhe = "add to your program the method public static void transfer(Account from, Account to, double amount);";

        try {
            kutsu(eka, toka, summa);
        } catch (Throwable e) {
            if (e.getMessage() != null && (e.getMessage().contains("puuttuu") || e.getMessage().contains("missing"))) {
                fail(virhe);
            } else {
                String msg = "click show backtrace, the error is in line denoted by \" Caused by\"\n";

                throw new Exception(msg, e);
            }
            assertTrue(virhe, metodi.toString().contains("static"));
            assertTrue(virhe, metodi.toString().contains("void"));
            assertTrue(virhe, metodi.toString().contains("public"));
        }

        assertEquals(""
                + "account1 = new Account(\"first\",1000);\n"
                + "account2 = new Account(\"second\",0);\n"
                + "transfer(first, second, 100); \n"
                + "the balance of account1", 900, eka.balance(), 0.1);
        assertEquals(""
                + "account1 = new Account(\"first\",1000);\n"
                + "account2 = new Account(\"second\",0);\n"
                + "transfer(first, second, 100); \n"
                + "the balance of account2", 100, toka.balance(), 0.1);
    }
    
    private void kutsu(Account milta, Account mille, double summa) throws Throwable {
        metodi = ReflectionUtils.requireMethod(Accounts.class, metodinNimi, Account.class, Account.class, double.class);
        metodi.invoke(null, milta, mille, summa);
    }
}
