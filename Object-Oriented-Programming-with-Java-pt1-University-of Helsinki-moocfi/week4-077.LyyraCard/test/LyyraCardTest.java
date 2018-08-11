
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class LyyraCardTest {

    @Rule
    public MockStdio io = new MockStdio();
    Reflex.ClassRef<Object> klass;
    String klassName = "LyyraCard";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }

    @Points("77.1")
    @Test
    public void classPublic() {
        assertTrue("class " + klassName + " should be public, define it as follows\npublic class Product {...\n}", klass.isPublic());
    }
    String luokanNimi = klassName;
    Class lyyraKorttiLuokka;

    @Points("77.1")
    @Test
    public void testConstructor() throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(double initialBalance)", ctor.isPublic());
        ctor.invoke(4.0);
    }

    @Points("77.1")
    @Test
    public void toStringWorksAtStart() throws Throwable {
        double summa = 10;
        Object card = newKortti(summa);
        String vastaus = toString(card);

        String odotettu = "The card has " + summa + " euros";
        assertFalse("Add LyyraCard class method public String toString()", vastaus.contains("@"));
        assertEquals("with card = new LyyraCard(" + summa + "); method toString does not work correctly:", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.1")
    @Test
    public void toStringWorksAtStart2() throws Throwable {
        double summa = 25;
        Object card = newKortti(summa);
        String vastaus = toString(card);
        String odotettu = "The card has " + summa + " euros";

        assertEquals("with card = new LyyraCard(" + summa + "); method toString does not work correctly:", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.1")
    @Test
    public void noExtraVariables1() {
        saniteettitarkastus();
    }

    /*
     * osa2
     */
    @Points("77.2")
    @Test
    public void noExtraVariables2() {
        saniteettitarkastus();
    }

    @Points("77.2")
    @Test
    public void hasMethodPayEchonomical() throws Throwable {
        String method = "payEconomical";

        //Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object olio = newKortti(4.0);

        assertTrue("add the class " + klassName + " method public void " + method + "() ", klass.method(olio, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nFailure was caused by code  Lyyracard lc = new LyyraCard(4.0); lc.payEconomical()";

        klass.method(olio, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }
    static final double EDULLINEN = 2.5;
    static final double MAUKAS = 4.0;

    @Points("77.2")
    @Test
    public void payEconomicalDecreasesTheBalance() throws Throwable {
        double summa = 6;
        Object card = newKortti(summa);
        syo("Economical", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa - EDULLINEN) + " euros";

        assertEquals("method payEconomical does not the work correctly \n"
                + "Check code card = new LyyraCard(6); card.payEconomical(); System.out.println(card);\n",
                odotettu.toLowerCase(), vastaus.toLowerCase());

        syo("Economical", card);

        vastaus = toString(card);
        odotettu = "The card has " + (summa - 2 * EDULLINEN) + " euros";

        assertEquals("method payEconomical does not the work correctly \n"
                + "Check code card = new LyyraCard(6); card.payEconomical(); card.payEconomical(); System.out.println(card);\n",
                odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.2")
    @Test
    public void hasMethodPayGourmet() throws Throwable {
        String method = "payGourmet";

        //Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Object olio = newKortti(4.0);

        assertTrue("add the class " + klassName + " method public void " + method + "() ", klass.method(olio, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nFailure was caused by code Lyyracard lc = new LyyraCard(4.0); lc.payGourmet()";

        klass.method(olio, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();

    }

    @Points("77.2")
    @Test
    public void payGourmetDecreasesTheBalance() throws Throwable {
        double summa = 10;
        Object card = newKortti(summa);
        syo("Gourmet", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa - MAUKAS) + " euros";

        assertEquals("method payGourmet does not the work correctly \n"
                + "Check code card = new LyyraCard(10); card.payGourmet(); System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());

        syo("Gourmet", card);

        vastaus = toString(card);
        odotettu = "The card has " + (summa - 2 * MAUKAS) + " euros";

        assertEquals("method payGourmet does not the work correctly \n"
                + "Check code card = new LyyraCard(10); card.payGourmet(); card.payEconomical(); System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 3
     */
    @Points("77.3")
    @Test
    public void noExtraVariables3() {
        saniteettitarkastus();
    }

    @Points("77.3")
    @Test
    public void payEconomicalDoesNotCauseNegativeBalance() throws Throwable {
        double summa = 2;
        Object card = newKortti(summa);
        syo("Economical", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa) + " euros";

        assertEquals("The balance should not drop below zero if not enough money for economical lunch. Check code \n"
                + "card = new LyyraCard(2); card.payEconomical(); card.payEconomical(); System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.3")
    @Test
    public void payGourmetDoesNotCauseNegativeBalance() throws Throwable {
        double summa = 7;
        Object card = newKortti(summa);

        syo("Gourmet", card);
        syo("Gourmet", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa - MAUKAS) + " euros";

        assertEquals("The balance should not drop below zero if not enough money for gourmet lunch. \n"
                + "Check code card = new LyyraCard(7); card.payGourmet(); card.payGourmet(); System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.3")
    @Test
    public void canEatWithAllTheMoney() throws Throwable {
        double summa = 4;
        Object card = newKortti(summa);

        syo("Gourmet", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa - 4) + " euros";

        assertEquals("If the balance is 4, gourmet lunch should be selled \n"
                + "Check code card = new LyyraCard(4); card.payGourmet();  System.out.println(card);", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.3")
    @Test
    public void canEatWithAllTheMoney2() throws Throwable {
        double summa = 2.5;
        Object card = newKortti(summa);

        syo("Economical", card);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa - 2.5) + " euros";

        assertEquals("If the balance is 2.5,economical lunch should be selled \n"
                + "Check code card = new LyyraCard(2.5); card.payEconomical();  System.out.println(card);", odotettu.toLowerCase(), vastaus.toLowerCase());
    }
    /*
     * osa 4
     */

    @Points("77.4")
    @Test
    public void noExtraVariables4() {
        saniteettitarkastus();
    }

    @Points("77.4")
    @Test
    public void hasMethodLoadMoney() throws Throwable {
        String method = "loadMoney";

        Object olio = newKortti(4.0);

        assertTrue("add the class " + klassName + " method public void " + method + "(double amount) ", klass.method(olio, method)
                .returningVoid().taking(double.class).isPublic());

        String v = "\nFailure was caused by code Lyyracard lc = new LyyraCard(4.0); lc.loadMoney(2.0);";

        klass.method(olio, method)
                .returningVoid().taking(double.class).withNiceError(v).invoke(2.0);

    }

    @Points("77.4")
    @Test
    public void moneyCanBeLoaded() throws Throwable {
        double summa = 7;
        Object card = newKortti(summa);

        loadMoney(card, 3);

        String vastaus = toString(card);
        String odotettu = "The card has " + (summa + 3) + " euros";

        assertEquals("Method loadMoney does not work. Check code \n"
                + "card = new LyyraCard(7); card.loadMoney(3); System.out.println(card);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.4")
    @Test
    public void ladatullaRahallaVoiOstaa() throws Throwable {
        Object card = newKortti(1);

        loadMoney(card, 5);
        syo("Gourmet", card);

        double summa = 2;
        String vastaus = toString(card);
        String odotettu = "The card has " + summa + " euros";

        assertEquals("Method oadMoney does not work. Check code \n"
                + "card = new LyyraCard(1); card.loadMoney(5); card.payGourmet(); System.out.println(card);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    @Points("77.4")
    @Test
    public void saldoEiKasvaYliRajan() throws Throwable {
        Object card = newKortti(100);

        loadMoney(card, 100);

        double summa = 150;
        String vastaus = toString(card);
        String odotettu = "The card has " + summa + " euros";

        assertEquals("The card balance should not exeed 150. Check code \n"
                + "card = new LyyraCard(100); card.loadMoney(100); card.payGourmet(); System.out.println(card);\n"
                + "", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 5
     */
    @Points("77.5")
    @Test
    public void noExtraVariables5() {
        saniteettitarkastus();
    }

    @Points("77.5")
    @Test
    public void negatiivinenLatausEiMuutaSaldoa() throws Throwable {
        double saldo = 10;
        Object card = newKortti(10);

        loadMoney(card, -7);

        String vastaus = toString(card);
        String odotettu = "The card has " + saldo + " euros";

        assertEquals("Loading a negative amount should not change the balance. Check code\n"
                + "card = new LyyraCard(10); card.loadMoney(-7); card.payGourmet(); System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());

        loadMoney(card, 1);
        loadMoney(card, -3);

        vastaus = toString(card);
        odotettu = "The card has " + (saldo + 1) + " euros";

        assertEquals("Loading a negative amount should not change the balance. heck code\n"
                + "card = new LyyraCard(10); card.loadMoney(-7); card.loadMoney(1); card.loadMoney(-3);System.out.println(card);\n", odotettu.toLowerCase(), vastaus.toLowerCase());
    }

    /*
     * osa 6
     */
    @Points("77.6")
    @Test
    public void manyCards() {
        Main.main(new String[0]);
        String[] rivit = io.getSysOut().split("\n");
        assertTrue("Et tulosta mitään", rivit.length > 0);
        for (String rivi : rivit) {
            assertTrue("Print card info and owner name of card at the same line."
                    + "Remove possible extra code from main()", rivi.toLowerCase().contains("pek") || rivi.toLowerCase().contains("bri"));
            assertFalse("Print only one card info per line. Now you print line " + rivi, rivi.toLowerCase().contains("pek") && rivi.toLowerCase().contains("bri"));
        }
        ArrayList<String> pekka = new ArrayList<String>();
        ArrayList<String> matti = new ArrayList<String>();
        for (String rivi : rivit) {
            if (rivi.toLowerCase().contains("bri")) {
                matti.add(rivi);
            } else if (rivi.toLowerCase().contains("pek")) {
                pekka.add(rivi);
            }
        }

        tarkastaMatinRivit(matti);
        tarkastaPekanRivit(pekka);
    }

    private void tarkastaMatinRivit(ArrayList<String> rivi) {
        String virhe = "Check that your programs output is simillar to the example. Noy you print ";

        assertEquals("Brian's card info should be printed 3 times ", 3, rivi.size());

        assertTrue(virhe + rivi.get(0), rivi.get(0).contains("27.5"));
        assertTrue(virhe + rivi.get(1), rivi.get(1).contains("23.5"));
        assertTrue(virhe + rivi.get(2), rivi.get(2).contains("73.5"));
    }

    private void tarkastaPekanRivit(ArrayList<String> rivi) {
        assertEquals("Pekka's card info should be printed 3 times", 3, rivi.size());

        String virhe = "Check that your programs output is simillar to the example. Noy you print ";
        assertTrue(virhe + rivi.get(0), rivi.get(0).contains("16.0"));
        assertTrue(virhe + rivi.get(1), rivi.get(1).contains("36.0"));
        assertTrue(virhe + rivi.get(2), rivi.get(2).contains("31.0"));
    }

    @Points("77.6")
    @Test
    public void noExtraVariables6() {
        saniteettitarkastus();
    }

    private void saniteettitarkastus() throws SecurityException {
        int n = 0+1;
        saniteettitarkastus(n, klassName);

    }

    private void saniteettitarkastus(int n, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();
        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString()), field.toString().contains("private"));
        }
        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                    System.err.println("JAMO:fields");
                }
            }
            String s = n > 1 ? "s" : "";

            assertTrue("The class " + klassName + " needs instance variable" + s + " only for " + m + ", remove the rest", var <= n);

        }
    }

    /*
     * helpers
     */
    private void syo(String miten, Object card) throws Throwable {
        String method = "pay" + miten;

        String v = "\nFailure was caused by code lc = new LyyraCard(4); lc." + method + "()";

        klass.method(card, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    private void loadMoney(Object card, double maara) throws Throwable {

        String method = "loadMoney";

        String v = "\nFailure was caused by code lc = new LyyraCard(4); lc." + method + "(" + maara + ")";

        klass.method(card, method)
                .returningVoid().taking(double.class).withNiceError(v).invoke(maara);
    }

    private String toString(Object olio) throws Throwable {
        //Reflex.ClassRef<Object> klassi = Reflex.reflect(klassName);
        String method = "toString";

        String v = "\nFailure was caused by code lc = new LyyraCard(4); lc.toString()";

        return klass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke();
    }

    private Object newKortti(double arvo) throws Throwable {
        Reflex.MethodRef1<Object, Object, Double> ctor = klass.constructor().taking(double.class).withNiceError();
        assertTrue("Add class " + klassName + " constructor: public " + klassName + "(double initialBalance)", ctor.isPublic());
        return ctor.invoke(arvo);
    }

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }
}
