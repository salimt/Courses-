import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GuessingGameTest {

    String klassName = "GuessingGame";
    Reflex.ClassRef<Object> klass;

    @Before
    public void setup() {
        klass = Reflex.reflect(klassName);
    }

    @Test
    @Points("105.1")
    public void test1() throws Throwable {
        MockInOut io = new MockInOut("n\n\n\n\n\n\n\n\n\n");
        GuessingGame game = new GuessingGame();

        String metodi = "isGreaterThan";
        assertTrue("Add the class GuessingGame the method "
                + "public boolean isGreaterThan(int number)",
		   klass.method(game, metodi).returning(boolean.class).taking(int.class).isPublic());

        String v = "Problem caused by the code \n"
                + "GuessingGame a = new GuessingGame();"
                + "Scanner reader = new Scanner(System.in);"
                + "a.isGreaterThan(reader, 1);\n";

        klass.method(game, metodi).returning(boolean.class).taking(int.class).
                withNiceError(v).invoke(1);
    }

    @Test
    @Points("105.1")
    public void test2() throws Throwable {
        MockInOut io = new MockInOut("n\n");
        GuessingGame game = new GuessingGame();

        String metodi = "isGreaterThan";
        Boolean result = klass.method(game, metodi).returning(boolean.class).taking(int.class)
                .invoke(44);

        assertFalse("When GuessingGame game = new GuessingGame(); \n"
                + "If user inputs n when the method game.isGreaterThan is called, "
                + "the method should return false.", result);

        String out = io.getOutput();
        String v = "When GuessingGame game = new GuessingGame(); \n"
                + "method call isGreaterThan(44); should print "
                + "\"Is your number greater than 44? (y/n)\". ";
        assertTrue(v + "\nNow it did not print anything", out.length() > 0);
        assertTrue(v + "\nNow output was\n" + out, out.contains("" + 44) && out.contains("s your number greater than"));
    }

    @Test
    @Points("105.1")
    public void test2b() throws Throwable {
        MockInOut io = new MockInOut("y\n");

        GuessingGame game = new GuessingGame();

        String metodi = "isGreaterThan";

        Boolean result = klass.method(game, metodi).returning(boolean.class)
                .taking(int.class).invoke(55);

        assertTrue("If user inputs y when isGreaterThan is called, the method should return true.", result);

        String out = io.getOutput();
        String v = "When GuessingGame game = new GuessingGame(); \n"
                + "method call game.isGreaterThan(55); should print "
                + "\"Is your number greater than 55? (y/n)\". ";
        assertTrue(v + "\nNow it did not print anything", out.length() > 0);
        assertTrue(v + "\nNow output was\n" + out, out.contains("" + 55) && out.contains("s your number greater than"));

    }

    /*
     * 
     */
    @Test
    @Points("105.2")
    public void test3() throws Throwable {
        String metodi = "average";
        GuessingGame game = new GuessingGame();
        assertTrue("In the class GuessingGame, add the method public int average(int first, int second)",
                klass.method(game, metodi).returning(int.class).taking(int.class, int.class).isPublic());


        String v = "Problem caused by the code \n"
                + "GuessingGame a = new GuessingGame();"
                + "a.average(3, 8);\n";

        klass.method(game, metodi).returning(int.class).taking(int.class, int.class).
                withNiceError(v).invoke(3, 8);
    }

    @Test
    @Points("105.2")
    public void test4() {
        int[][] luvutJaOletetut = {{1, 1, 1}, {3, 4, 3}, {6, 12, 9}, {100, 100, 100}};

        for (int[] luvut : luvutJaOletetut) {
            int tulos = -1;
            try {
                GuessingGame game = new GuessingGame();
                tulos = klass.method(game, "average").returning(int.class).taking(int.class, int.class).invoke(luvut[0], luvut[1]);
            } catch (Throwable ex) {
                Logger.getLogger(GuessingGameTest.class.getName()).log(Level.SEVERE, null, ex);
            }

            Assert.assertEquals("Check the average of values " + luvut[0] + " and " + luvut[1], luvut[2], tulos);
        }

    }

    @Points("105.3")
    @Test(timeout = 10000)
    public void test5() {
        testaaLukuaVerbose(1, 10, null);
        testaaLukuaVerbose(1, 10, null);
        testaaLukuaVerbose(1, 10, null);
        testaaLukuaVerbose(1, 100, null);
        testaaLukuaVerbose(1, 10000000, null);
        testaaLukuaVerbose(50, 150, null);
        testaaLukuaVerbose(50, 150, false);
        testaaLukuaVerbose(78, 1193, null);
        testaaLukuaVerbose(78, 1193, true);
    }

    private void testaaLukuaVerbose(int ala, int yla, Boolean vastausAina) {
        GuessingGameTest.BinaariHaunTulokset tulokset = generoiTulokset(ala, yla, vastausAina);
        // määrittele valmiiksi vastaukset, joiden pitäisi johtaa lukuun
        List<String> vastaukset = tulokset.getVastaukset();

        String input = "";
        String vastausLista = "";
        for (int i = 0; i < vastaukset.size(); i++) {
            input += vastaukset.get(i) + "\n";
            vastausLista += vastaukset.get(i);
            if (i < vastaukset.size() - 1) {
                vastausLista += ", ";
            }
        }

        MockInOut mio = new MockInOut(input);

        try {
            GuessingGame game = new GuessingGame();
            game.play(ala, yla);
        } catch (NoSuchElementException e) {
            fail("Your program tries to read input despite it is not needed\n"
                    + "Note that the maximum number of questions is not always needed\n"                    
                    + "When searching " + tulokset.valittuLuku + " from the range " + ala + "-" + yla + " "
                    + "the following answers are enough: "
                    + vastausLista);
        } catch (Exception e) {
            fail("GuessingGame game = new GuessingGame();\n game.play("
                    + ala + "," + yla + ");\n caused exception with answers "
                    + vastausLista
                    + " \n more info: " + e.toString());
        }

        String output = mio.getOutput();

        Pattern pattern = Pattern.compile(".*?than\\s+(\\d+)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(output);

        int mones = 1;
        for (Integer kysyttyLuku : tulokset.getKysytytLuvut()) {
            if (!matcher.find()) {
                fail("GuessingGame should ask " + tulokset.getKysytytLuvut().size() + " questions with the range " + ala + "-" + yla + ", when the value is " + tulokset.valittuLuku + ", with input: " + vastausLista + ". \n"
                        + "You printed:\n" + output);
            }

            String tarjottuLukuString = matcher.group(1);
            int tarjottuLuku = Integer.parseInt(tarjottuLukuString);
            if (tarjottuLuku != kysyttyLuku) {
                fail("GuessingGame asked a wrong value " + tarjottuLuku + " in question number " + mones + ", it should have asked " + kysyttyLuku + 
                        "\n with range " + ala + "-" + yla + " and input: " + vastausLista + ". The guessed number was " + tulokset.valittuLuku + ""
                        + "\nOutput was:\n" + output);
            }
            mones++;
        }

        Pattern pattern2 = Pattern.compile(".*thinking\\s+of\\s+is\\s+(\\d+).*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(output);

        boolean vastausLoytyy = matcher2.matches();
        if (!vastausLoytyy) {
            fail("The guessing game do not print the correct number! Output was: "+output);
        }

        String palautettuLukuString = matcher2.group(1);
        int palautettuLuku = Integer.parseInt(palautettuLukuString);

        int luku = tulokset.getValittuLuku();
        if (palautettuLuku != luku) {
            fail("GuessingGame printed as result " + palautettuLuku
                    + ", it should have been " + luku + ". Range was "
                    + ala + "-" + yla + " and answers were: " + vastausLista);
        }
    }

    private void testaaLukua(int ala, int yla, Boolean vastausAina) {
        GuessingGameTest.BinaariHaunTulokset tulokset = generoiTulokset(ala, yla, vastausAina);
        // määrittele valmiiksi vastaukset, joiden pitäisi johtaa lukuun
        List<String> vastaukset = tulokset.getVastaukset();

        String input = "";
        String vastausLista = "";
        for (int i = 0; i < vastaukset.size(); i++) {
            input += vastaukset.get(i) + "\n";
            vastausLista += vastaukset.get(i);
            if (i < vastaukset.size() - 1) {
                vastausLista += ", ";
            }
        }

        MockInOut mio = new MockInOut(input);

        try {
            GuessingGame game = new GuessingGame();
            game.play(ala, yla);
        } catch (NoSuchElementException e) {
            fail("Your program asks for more input that needed. Remember that in all cases the maximum number of inputs is not needed"
                    + "When searching number " + tulokset.valittuLuku + " from range " + ala + "-" + yla + ""
                    + "you need just the questions : "
                    + vastausLista);
        } catch (Exception e) {
            fail("Exception with range"
                    + ala + "-" + yla + ", and answers: "
                    + vastausLista
                    + " more info " + e.toString());
        }

        String output = mio.getOutput();

        Pattern pattern = Pattern.compile(".*?greater\\s+(\\d+)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(output);

        int mones = 1;
        for (Integer kysyttyLuku : tulokset.getKysytytLuvut()) {
            if (!matcher.find()) {
                fail("GuessingGame should have asked " + tulokset.getKysytytLuvut().size() + 
                        " questions with the range" + ala + "-" + yla + ", when number was: " + 
                        tulokset.valittuLuku + ", with input: " + vastausLista );
            }

            String tarjottuLukuString = matcher.group(1);
            int tarjottuLuku = Integer.parseInt(tarjottuLukuString);
            if (tarjottuLuku != kysyttyLuku) {
                fail("GuessingGame asked a wrong number in question number " + tarjottuLuku + " " + mones + 
                        ". It should have asked " + kysyttyLuku + " range was " + ala + "-" + yla + 
                        ", the input: " + vastausLista + ". Guessed number: " + tulokset.valittuLuku);
            }
            mones++;
        }

        Pattern pattern2 = Pattern.compile(".*number\\s+is\\s+(\\d+).*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher2 = pattern2.matcher(output);

        boolean vastausLoytyy = matcher2.matches();
        if (!vastausLoytyy) {
            fail("GuessingGame does not return the guessed number "+output);
        }

        String palautettuLukuString = matcher2.group(1);
        int palautettuLuku = Integer.parseInt(palautettuLukuString);

        int luku = tulokset.getValittuLuku();
        if (palautettuLuku != luku) {
            fail("GuessingGame returned " + palautettuLuku
                    + ", when the result should have been " + luku + ". Range was "
                    + ala + "-" + yla + " and the input was: " + vastausLista);
        }
    }

    private GuessingGameTest.BinaariHaunTulokset generoiTulokset(int ala, int yla, Boolean vastausAina) {
        Random random = new Random();
        int nykyinenAla = ala;
        int nykyinenYla = yla;

        List<String> vastaukset = new ArrayList<String>();
        List<Integer> kysytytLuvut = new ArrayList<Integer>();
        while (nykyinenAla < nykyinenYla) {
            int puolivali = (nykyinenAla + nykyinenYla) / 2;
            kysytytLuvut.add(puolivali);

            boolean suurempi;
            if (vastausAina != null) {
                suurempi = vastausAina.booleanValue();
            } else {
                suurempi = random.nextBoolean();
            }

            if (suurempi) {
                vastaukset.add("y");
                nykyinenAla = puolivali + 1;
            } else {
                vastaukset.add("n");
                nykyinenYla = puolivali;
            }
        }

        return new GuessingGameTest.BinaariHaunTulokset(nykyinenAla, vastaukset, kysytytLuvut);
    }

    private class BinaariHaunTulokset {

        private int valittuLuku;
        private List<String> vastaukset;
        private List<Integer> kysytytLuvut;

        public BinaariHaunTulokset(int valittuLuku,
                List<String> vastaukset, List<Integer> kysytytLuvut) {
            this.valittuLuku = valittuLuku;
            this.vastaukset = vastaukset;
            this.kysytytLuvut = kysytytLuvut;
        }

        public int getValittuLuku() {
            return valittuLuku;
        }

        public List<Integer> getKysytytLuvut() {
            return kysytytLuvut;
        }

        public List<String> getVastaukset() {
            return vastaukset;
        }
    }
}
