
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import hangman.Hangman;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest(HangmanUserInteface.class)
public class HangmanTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    @Points("42.1")
    public void asksSomethingFromUser() {
        MockInOut mio = new MockInOut("");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        boolean kysytaanKayttajalta = false;
        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
            kysytaanKayttajalta = true;
        }

        assertTrue("You should ask a command from the user", kysytaanKayttajalta);
    }

    @Test
    @Points("42.1")
    public void rightConditionInWhile() {
        MockInOut mio = new MockInOut("a\nb\n");
        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // pass
        }

        assertWasCalled("gameOn", "You should call the method hangman.gameOn()!");
    }

    @Test
    @Points("42.1")
    public void continueIfCommandNotQuit() {
        MockInOut mio = new MockInOut("tekisi\nmieli\nlimpparia\nja\nsuklaata\nlopetanko\nlopettaisinko\nen!\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        boolean kysytaanKayttajalta = false;
        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
            kysytaanKayttajalta = true;
        }

        assertTrue("Game ends even if user does not give input quit. Do not end the game before quit command given!", kysytaanKayttajalta);
    }

    @Test
    @Points("42.1")
    public void gameEndsWhenQuitEntered() {
        MockInOut mio = new MockInOut("ja\nsit\nkirjoitin\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        boolean lopetetaanKyseleminen = true;
        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
            lopetetaanKyseleminen = false;
        }

        assertTrue("The game should end when user gives command quit", lopetetaanKyseleminen);
    }

    @Test
    @Points("42.1")
    public void gameOnCheckedForEachLoop() {
        MockInOut mio = new MockInOut("ja\nsit\nkirjoitin\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasCalled("gameOn",
                "You should check it the game is still on for each time you execute the while loop."
                + "\nDo the loop as follows while (hirsipuu.gameOn()) { ... ");
    }

    @Test
    @Points("42.2")
    public void statusPrinted() {
        MockInOut mio = new MockInOut("status\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasCalled("printStatus", "When user enters command status, the game status "
                + "should be printed using the method hangman.printStatus() ");
    }

    @Test
    @Points("42.2")
    public void statusNotPrintedIfNotAsked() {
        MockInOut mio = new MockInOut("jotain\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasNotCalled("printStatus",
                "Do not print the game status with hangman.printStatus() if status command not given");
    }

    @Test
    @Points("42.3")
    public void whenLetterGivenGuessMAde() {
        MockInOut mio = new MockInOut("a\nstatus\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasCalled("guess",
                "When user enters a letter, hangman.guess should be called");
    }

    @Test
    @Points("42.3")
    public void noLetterNoGuess() {
        MockInOut mio = new MockInOut("jotain\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasNotCalled("guess", "If user do not enter a single letter,  hangman.guess should not be called");
    }

    @Test
    @Points("42.4")
    public void meuPrintedWithWhiteSpace() {
        MockInOut mio = new MockInOut("\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        int luku = Counter.getValue();

        replace(method(HangmanUserInteface.class, "printMenu")).with(method(Counter.class, "increase"));

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertTrue("You should call method printMenu() when user enters an empty command", Counter.getValue() == luku + 2);
    }

    @Test
    @Points("42.4")
    public void noExtraMenusWhenEmptyNotGiven() {
        MockInOut mio = new MockInOut("jeajea\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        int luku = Counter.getValue();

        replace(method(HangmanUserInteface.class, "printMenu")).with(method(Counter.class, "increase"));

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertTrue("The menu should only be printed at the start if user does not enter an empty command", Counter.getValue() == luku + 1);
    }

    @Test
    @Points("42.5")
    public void manAndWordPrintedInEachCompletedLoop() {
        MockInOut mio = new MockInOut("jeajea\nquit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasCalled("printWord", "You should call hangman.printWord() at the end of while-loop");
        assertWasCalled("printMan", "You should call hangman.printMan() at the end of while-loop");
    }

    @Test
    @Points("42.5")
    public void manAndWordNotPrintedIfLoopNotCompleted() {
        MockInOut mio = new MockInOut("quit\n");
        ReflectionUtils.newInstanceOfClass(HangmanUserInteface.class);

        try {
            HangmanUserInteface.main(new String[0]);
        } catch (NoSuchElementException e) {
            // toistolausekkeessa useampia kyselyjä..
        }

        assertWasNotCalled("printWord", "You should call hangman.printWord() only at the end of while-loop, do not call the method from elsewhere");
        assertWasNotCalled("printMan", "You should call hangman.printMan() only at the end of while-loop, do not call the method from elsewhere");
    }

    private void assertWasCalled(String method, String msg) {
        List<String> mt = getMethodCalls();
        assertFalse(msg, mt == null || mt.isEmpty());
        assertTrue(msg, mt.contains(method));
    }

    private void assertWasNotCalled(String method, String msg) {
        List<String> mt = getMethodCalls();
        assertTrue(msg, mt == null || !mt.contains(method));
    }

    private List<String> getMethodCalls() {
        try {
            Method m = Hangman.class.getDeclaredMethod("getCalledMethods");
            m.setAccessible(true);
            return (List<String>) m.invoke(null);
        } catch (Throwable e) {
        }
        return null;
    }
}
