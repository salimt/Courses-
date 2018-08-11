
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class CardAndRegisterTest {

    LyyraCard card;
    CashRegister register;

    @Before
    public void setUp() {
        card = new LyyraCard(10);
        register = new CashRegister();
    }
    Reflex.ClassRef<Object> klassL;
    String klassNameL = "LyyraCard";
    Reflex.ClassRef<Object> klassK;
    String klassNameK = "CashRegister";

    @Before
    public void haeLuokka() {
        klassL = Reflex.reflect(klassNameL);
        klassK = Reflex.reflect(klassNameK);
    }

    @Points("86.1")
    @Test
    public void test1() {
        saniteettitarkastus("LyyraCard", 1, "Do not add LyyraCard new instance variables, you do not need any!");
    }

    @Points("86.1")
    @Test
    public void test2() {
        assertEquals("If card has enough monet, pay should return true. Check code: \n"
                + "k = new LyyraCard(10); k.pay(8);", true, card.pay(8));
        assertEquals("The balance should decrease when payment made. Check code: "
                + "k = new LyyraCard(10); k.pay(8);", 2, card.balance(), 0.01);
    }

    @Points("86.1")
    @Test
    public void test3() {
        assertEquals("Payment should be possible when the balance equals the payed amount. Check code: "
                + "k = new LyyraCard(10); k.pay(10);", true, card.pay(10));
        assertEquals("Payment should be possible when the balance equals the payed amount. Check code: "
                + "k = new LyyraCard(10); k.pay(10);", 0, card.balance(), 0.01);
    }

    /*
     *
     */
    @Points("86.2")
    @Test
    public void test4() {
        saniteettitarkastus("CashRegister", 3, "Do not add new instance variables to CashRegistry, those are not needed!");
    }

    @Points("86.2")
    @Test
    public void test5() {
        assertTrue("When CashRegister is created, it should have 1000 cash which should be reflected printing the object"
                + "\nOutput now: \"" + register + "\"", register.toString().contains("money in register 1000"));
    }

    @Points("86.2")
    @Test
    public void test6() {
        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("New register should have cash 1000 euros and total amount of sold lunches  0, ", odotettu, register.toString());
    }

    @Points("86.2")
    @Test
    public void test7() {
        double vaihto = register.payEconomical(4);

        String virhe = "When paying Economical lunch with 4 euros  (with call register.payEconomical(4)) ";
        assertEquals(virhe + "the change should be returned correctly.", 1.5, vaihto, 0.01);

        assertTrue(virhe + " the money in register should increase by 2.5 euros so it should be 1002.5. \nNow register has: " + register, register.toString().contains("money in register 1002.5"));

        assertTrue(virhe + " amount of sold economical lunches should be 1. \n"
                + "Now register has: " + register, register.toString().contains("economical lunches sold: 1"));
        assertTrue(virhe + " amount of sold gourmet lunch should be still 0. Now register has: " + register, register.toString().contains("gourmet lunches sold: 0"));
    }

    @Points("86.2")
    @Test
    public void test8() {
        double vaihto = register.payGourmet(5);

        String virhe = "When paying Gourmet lunch with 5 euros (with call register.payGourmet(5)) ";
        assertEquals(virhe + "the change should be returned correctly.", 1, vaihto, 0.01);

        assertTrue(virhe + " the money in register should increase by 4.0 euros so it should be 1004.0. \nNow register has: " + register, register.toString().contains("money in register 1004"));

        assertTrue(virhe + " mount of sold gourmet lunches should be 1. Now register has: " + register, register.toString().contains("gourmet lunches sold: 1"));
        assertTrue(virhe + " amount of sold economial lunch should be still 0. Now register has: " + register, register.toString().contains("economical lunches sold: 0"));
    }

    @Points("86.2")
    @Test
    public void test9() {
        double vaihto = register.payEconomical(2.5);

        String virhe = "When paying Economical lunch with 2.5 euros (with call register.payEconomical(2.5)) ";
        assertEquals(virhe + "the change should be 0.", 0, vaihto, 0.01);

        assertTrue(virhe + " the money in register should increase by 2.5 euros so it should be 1002.5. \nNow register has: " + register, register.toString().contains("money in register 1002.5"));

        assertTrue(virhe + " amount of sold economical lunches should be 1. \n"
                + "Now register has: " + register, register.toString().contains("economical lunches sold: 1"));
        assertTrue(virhe + " amount of sold gourmet lunch should be still 0. Now register has: " + register, register.toString().contains("gourmet lunches sold: 0"));
    }

    @Points("86.2")
    @Test
    public void test10() {
        double vaihto = register.payGourmet(4);

        String virhe = "When paying Gourmet lunch with 4 euros (with call register.payGourmet(4.0)) ";
        assertEquals(virhe + "the change should be 0.", 0, vaihto, 0.01);

       assertTrue(virhe + " the money in register should increase by 4.0 euros so it should be 1004.0. \nNow register has: " + register, register.toString().contains("money in register 1004"));

        assertTrue(virhe + " mount of sold gourmet lunches should be 1. Now register has: " + register, register.toString().contains("gourmet lunches sold: 1"));
        assertTrue(virhe + " amount of sold economial lunch should be still 0. Now register has: " + register, register.toString().contains("economical lunches sold: 0"));
     }

    @Points("86.2")
    @Test
    public void test11() {
        register.payGourmet(5);
        register.payEconomical(3);
        register.payGourmet(5);
        register.payGourmet(10);
        register.payEconomical(4);

        String virhe = "Aftec the code register.syoMaukkasti(5); register.SyoEdullisesti(3); register.syoMaukkasti(5);"
                + "register.syoMaukkasti(10);register.Edullisesi(4);";
        assertTrue(virhe + " register should have 1017 euros. Now register has: " + register, register.toString().contains("money in register 1017"));
        assertTrue(virhe + " the amount of sold gourmet lunches should be 3. Now register has: " + register, register.toString().contains("gourmet lunches sold: 3"));
        assertTrue(virhe + " the amount of sold economical lunches should be2. Now register has: " + register, register.toString().contains("economical lunches sold: 2"));

    }

    @Points("86.2")
    @Test
    public void test12() {
        double paluu = register.payEconomical(2);

        assertEquals("When trying to pay with to small amount of cash, e.g. register.payEconomical(2), the whole amount should be returned", 2, paluu, 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("After code register= new CashRegister(); register.payEconomical(2); "
                + "the state of register should not change and the toString should be: ", odotettu, register.toString());

        paluu = register.payGourmet(2);
        assertEquals("When trying to pay with to small amount of cash, e.g. register.payGourmet(2), the whole amount should be returned", 2, paluu, 0.01);

        odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("After code register= new CashRegister(); register.payGourmet(2); "
                + "the state of register should not change and the toString should be: ", odotettu, register.toString());
    }

    /*
     *
     */
    @Points("86.3")
    @Test
    public void test13() {
        saniteettitarkastus("CashRegister", 3, "Do not add new instance variables to CashRegistry, those are not needed!");
     }

    @Points("86.3")
    @Test
    public void test14() throws Throwable {
        String method = "payEconomical";

        CashRegister k = new CashRegister();

        assertTrue("add class " + klassNameK + " method public boolean " + method + "(LyyraCard card) ",
                klassK.method(k, method).returning(boolean.class).taking(LyyraCard.class).isPublic());

        String v = "\nVCode that causes the failure"
                + "k = new CashRegister(); lk = new LyyraCard(10); k." + method + "(lk);";

        LyyraCard lk = new LyyraCard(10);

        klassK.method(k, method).returning(boolean.class).taking(LyyraCard.class).withNiceError(v).invoke(lk);
    }

    @Points("86.3")
    @Test
    public void test15() {
        String sken = "register = new CashRegister(); card = new LyyraCard(10); register.payEconomical(card);";
        Boolean ok = payEconomical(register, card);

        assertEquals("If balance is high enough you should be capable of paying a economical lunch with the card."
                + " Check following:\n" + sken, true, ok);

        assertEquals("Card balance should be decreased by the payed amount. Check code:\n"
                + sken + " card.balance();\n", 7.5, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 1 gourmet lunches sold: 0";
        assertEquals("When a card payment for economical lunch has been made to a newly created register, the amount of cash in register should not change, and"
                + " number of sold economical lunches should be 1.\n", odotettu, register.toString());
    }

    @Points("86.3")
    @Test
    public void test16() {
        String sken = "register = new CashRegister(); card = new LyyraCard(2.5); register.payEconomical(card);";
        card = new LyyraCard(2.5);
        Boolean ok = payEconomical(register, card);

        assertEquals("If balance is high enough you should be capable of paying a economical lunch with the card. Check following:\n" + sken + "\n", true, ok);

        assertEquals("Card balance should be decreased by the payed amount. Check code:\n"
                + sken + " card.balance();\n", 0, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 1 gourmet lunches sold: 0";
        assertEquals("When a card payment for economical lunch has been made to with newly created register, the amount of cash in register should not change, and"
                + " number of sold economical lunches should be 1.", odotettu, register.toString());
    }

    @Points("86.3")
    @Test
    public void test17() {
        String sken = "register = new CashRegister(); card = new LyyraCard(2); register.payEconomical(card);";
        card = new LyyraCard(2);
        Boolean ok = payEconomical(register, card);

        assertEquals("If balance is not enough, the payment should be unsuccesful. Check following:\n"
                + sken + "\n", false, ok);

        assertEquals("Card balance should not change with an unsuccesful payment. Check code:\n"
                + sken + " card.balance();\n", 2, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("The unsuccesfull payment should not change the state of a newly creted register",
                odotettu, register.toString());
    }

    @Points("86.3")
    @Test
    public void test18() throws Throwable {
        String method = "payGourmet";

        CashRegister k = new CashRegister();

        assertTrue("add class " + klassNameK + " method public boolean " + method + "(LyyraCard card) ",
                klassK.method(k, method).returning(boolean.class).taking(LyyraCard.class).isPublic());

        String v = "\nVCode that causes the failure"
                + "k = new CashRegister(); lk = new LyyraCard(10); k." + method + "(lk);";

        LyyraCard lk = new LyyraCard(10);

        klassK.method(k, method).returning(boolean.class).taking(LyyraCard.class).withNiceError(v).invoke(lk);
    }

    @Points("86.3")
    @Test
    public void test19() {
        String sken = "register = new CashRegister(); card = new LyyraCard(10); register.payGourmet(card);";
        Boolean ok = payGourmet(register, card);

        assertEquals("If balance is high enough you should be capable of paying a gourmet lunch with the card. Check following: " + sken + " "
                + "\n", true, ok);

        assertEquals("Card balance should be decreased by the payed amount. \n"
                + "Check code "
                + sken + " card.balance();\n", 6, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 1";
        assertEquals("When a card payment for gourmet lunch has been made to with newly created register, the amount of cash in register should not change, and"
                + "number of sold gourmet lunches should be 1.\n", odotettu, register.toString());
    }

    @Points("86.3")
    @Test
    public void test20() {
        String sken = "register = new CashRegister(); card = new LyyraCard(4.0);";
        card = new LyyraCard(4);
        Boolean ok = payGourmet(register, card);

        assertEquals("If balance is high enough you should be capable of paying a gourmet lunch with the card. Check following: " + sken
                + "\n", true, ok);

        assertEquals("Card balance should be decreased by the payed amount. Check code "
                + sken + " card.balance();\n", 0, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 1";
        assertEquals("When a card payment for gourmet lunch has been made to with newly created register, the amount of cash in register should not change, and"
                + "number of sold gourmet lunches should be 1. ", odotettu, register.toString());
    }

    @Points("86.3")
    @Test
    public void test21() {
        String sken = "register = new CashRegister(); card = new LyyraCard(2);";
        card = new LyyraCard(2);
        Boolean ok = payGourmet(register, card);

        assertEquals("If balance is not enough, the payment should be unsuccesful. Check following:\n"
                + sken + "\n", false, ok);

        assertEquals("Card balance should not change with an unsuccesful payment. Check code:\n"
                + sken + " card.balance();\n", 2, card.balance(), 0.01);
        
        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("The unsuccesfull payment should not change the state of a newly creted register",
                odotettu, register.toString());
    }

    /*
     *
     */
    @Points("86.4")
    @Test
    public void test22() {
      saniteettitarkastus("CashRegister", 3, "Do not add new instance variables to CashRegistry, those are not needed!");
     }

    @Points("86.4")
    @Test
    public void test23() throws Throwable {
        String method = "loadMoneyToCard";

        CashRegister k = new CashRegister();

        assertTrue("add class " + klassNameK + " method public void " + method + "(LyyraCard card, double sum) ",
                klassK.method(k, method).returningVoid().taking(LyyraCard.class, double.class).isPublic());

        String v = "\nVCode that causes the failure"
                + "k = new CashRegister(); lk = new LyyraCard(10); k." + method + "(lk, 5);";

        LyyraCard lk = new LyyraCard(10);

        klassK.method(k, method).returningVoid().taking(LyyraCard.class, double.class).withNiceError(v).invoke(lk, 5.0);
    }

    @Points("86.4")
    @Test
    public void test24() {
        lataa(register, card, 10);
        String virhe = "Does the method for loading money work? check code:\n"
                + "register = new CashRegister(); card = new LyyraCard(10); register.loadMoneyToCard(card, 10); card.balance()";
        assertEquals(virhe, 20, card.balance(), 0.01);

        String odotettu = "money in register 1010.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("When money is loaded to card, the loaded amount should be added to cash register "
                + "At the beginning register has1000, when 10 euros loaded to card"
                + " register toString should be: \n", odotettu, register.toString());

    }

    @Points("86.4")
    @Test
    public void test25() {
        lataa(register, card, -10);
        String virhe = "Loading a negative amount should not change card balance or the amount of cash in the register! check code:\n"
                + "register = new CashRegister(); card = new LyyraCard(10); register.loadMoneyToCard(card, -10); card.balance()";
        assertEquals(virhe, 10, card.balance(), 0.01);

        String odotettu = "money in register 1000.0 economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("Loading a negative amount should not change card balance or the amount of cash in the register!"
                + "At the beginning register has1000, when 10 euros loaded to card -10"
                + " register toString should be: \n", odotettu, register.toString());

    }

    @Points("86.4")
    @Test
    public void test26() {
        Random seka = new Random();
        int ladatutYhteensa = 0;
        int[] ladatut = new int[5];
        for (int i = 0; i < ladatut.length; i++) {
            int ladattava = 1 + seka.nextInt(20);
            ladatut[i] = ladattava;
            ladatutYhteensa += ladattava;
            lataa(register, card, ladattava);
        }

        String mj = "";
        for (int l : ladatut) {
            mj += l + " ";
        }

        String virhe = "Does the method for loading money work? "
                + "Check card balance after the following amounts of money have been loaded to the card " + mj;
        assertEquals(virhe, 10 + ladatutYhteensa, card.balance(), 0.01);

        double exp = 1000 + ladatutYhteensa;
        String odotettu = "money in register " + exp + " economical lunches sold: 0 gourmet lunches sold: 0";
        assertEquals("When money is loaded to card, the loaded amount should be added to cash register "
                + "At the beginning register has 1000, when " + mj + " loaded to card "
                + " register toString should be: ", odotettu, register.toString());

    }
    /*
     *
     */

    private void lataa(Object register, Object card, double summa) {
        String methodNimi = "loadMoneyToCard";
        try {
            Method method = ReflectionUtils.requireMethod(CashRegister.class, methodNimi, LyyraCard.class, double.class);
            ReflectionUtils.invokeMethod(void.class, method, register, card, summa);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("add class CashRegister method public boolean loadMoneyToCard(LyyraCard card, double sum)");
        }
    }

    private boolean payEconomical(Object register, Object card) {
        String methodNimi = "payEconomical";
        try {
            Method method = ReflectionUtils.requireMethod(CashRegister.class, methodNimi, LyyraCard.class);
            return ReflectionUtils.invokeMethod(boolean.class, method, register, card);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("add class CashRegister method public boolean payEconomical(LyyraCard card)");
        }
        return false;
    }

    private boolean payGourmet(Object register, Object card) {
        String methodNimi = "payGourmet";
        try {
            Method method = ReflectionUtils.requireMethod(CashRegister.class, methodNimi, LyyraCard.class);
            return ReflectionUtils.invokeMethod(boolean.class, method, register, card);
        } catch (Throwable t) {
            t.printStackTrace();
            fail("add class CashRegister method public boolean payGourmet(LyyraCard card)");
        }
        return false;
    }


    private void saniteettitarkastus(String luokanNimi, int muuttujia, String msg) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString()), field.toString().contains("private"));
        }


        String viesti = ", NOTE: if you need to save lunc prices to instance variables, do it as follows\n"
                + " private static final double PRICE_OF_ECONOMICAL = 2.5; ";

        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue(msg + viesti, var <= muuttujia);
        }

//        if (kentat.length > 1) {
//            int var = 0;
//            for (Field field : kentat) {
//                if (!field.toString().contains("final")) {
//                    var++;
//                }
//            }
//            assertTrue("The class " + luokanNimi + " needs only instance variable for the amount of weightings, remove the extra "
//                    + "", var < muuttujia);
//        }
    }

    private String kentta(String toString) {
        return toString.replace("LyyraCard" + ".", "");
    }
}
