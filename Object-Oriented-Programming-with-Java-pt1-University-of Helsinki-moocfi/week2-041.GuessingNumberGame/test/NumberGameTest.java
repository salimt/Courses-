
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(GuessingNumberGame.class)
public class NumberGameTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    @Points("41.1")
    public void testTooSmall() {
        MockInOut mio = new MockInOut("-1\n");
        int oldOut = mio.getOutput().length();
        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }
        String out = mio.getOutput().substring(oldOut);
        assertTrue("If guess is too small, program should print \"The number is greater\".", out.contains("reater"));
    }

    @Test
    @Points("41.1")
    public void testiLiianIso() {
        MockInOut mio = new MockInOut("101\n");
        int oldOut = mio.getOutput().length();
        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }
        String out = mio.getOutput().substring(oldOut);

        assertTrue("If guess is too large, program should print \"The number is lesser\".", out.contains("less"));
    }

    @Test
    @Points("41.1")
    public void testCorrect() {
        MockInOut mio = new MockInOut("1\n");

        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);

        replace(method(GuessingNumberGame.class, "drawNumber")).with(method(Uno.class, "getUno"));
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (Exception e) {
            // toistolausekkeessa useampia kyselyjä
        }


        String out = mio.getOutput();
        mio.close();
        assertTrue("If guess is correct, program should print \"Congratulations, your guess is correct!\".", out.contains("orrect"));
    }

    @Test
    @Points("41.2")
    public void repeatingTestLower() {
        MockInOut mio = new MockInOut("0\n1\n");

        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);

        replace(method(GuessingNumberGame.class, "drawNumber")).with(method(Uno.class, "getUno"));
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (Exception e) {
            // toistolausekkeessa useampia kyselyjä
        }

        String out = mio.getOutput();
        mio.close();

        assertTrue("If the drawn number is 1 and user input is 0, 1, program should at first "
                + "print\n"
                + "\"The number is greater\", and after that \"Congratulations, your guess is correct!\"", 
                !out.contains("maller") && out.contains("reater") && out.contains("gratula"));
    }

    @Test
    @Points("41.2")
    public void repeatingEnds() {
        MockInOut mio = new MockInOut("0\n1\nzorbas\n");

        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);

        replace(method(GuessingNumberGame.class, "drawNumber")).with(method(Uno.class, "getUno"));
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (NumberFormatException e) {
            fail("Program should end when user guesses the drawn number");
        }

        String out = mio.getOutput();
        mio.close();

        assertTrue("If the drawn number is 1 and user input is 0, 1, program should at first "
                + "print\n"
                + "\"The number is greater\", and after that \"Congratulations, your guess is correct!\"", 
                !out.contains("maller"));

    }

    @Test
    @Points("41.2")
    public void repeatingTestHigher() {
        MockInOut mio = new MockInOut("2\n1\n");

        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);

        replace(method(GuessingNumberGame.class, "drawNumber")).with(method(Uno.class, "getUno"));
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (Exception e) {
            // toistolausekkeessa useampia kyselyjä
        }

        String out = mio.getOutput();
        mio.close();

        assertTrue("If the drawn number is 1 and user input is 2, 1, program should at first print\n"
                + "\"The number is lesser\", and after that \"Congratulations, your guess is correct!\"", 
                !out.contains("reater") && out.contains("lesser") &&out.contains("gratul") );
        
      }

    @Test
    @Points("41.3")
    public void countGuesses() {
        ReflectionUtils.newInstanceOfClass(GuessingNumberGame.class);

        MockInOut mio = new MockInOut("5\n4\n3\n2\n1\n");

        replace(method(GuessingNumberGame.class, "drawNumber")).with(method(Uno.class, "getUno"));
        try {
            GuessingNumberGame.main(new String[0]);
        } catch (Exception e) {
            // toistolausekkeessa useampia kyselyjä
        }

        String out = mio.getOutput();
        mio.close();

        out = out.replaceAll("[^\\d]+", " ").trim();
        out = out.replaceAll("\\s+", " ");

        String[] numerot = out.split("\\s+");
        if (numerot.length == 1 && numerot[0].trim().isEmpty()) {
            fail("You have to print the number of guesses");
        }

        if (numerot.length < 4) {
            fail("You have to print the number of guesses");
        }

        int edellinen = Integer.parseInt(numerot[numerot.length - 1]);
        for (int i = numerot.length - 2; i >= 0; i--) {
            int luku = Integer.parseInt(numerot[i]);
            if (edellinen <= luku) {
                fail("The number of guesses should increase");
            }
        }
    }
}
