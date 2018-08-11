
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Test;

public class HangmanLogicTest {

    @Test
    public void faultsZeroAtStart() {
        HangmanLogic instance = new HangmanLogic("PORKKANA");
        assertEquals("Number of faults should be at the berinning0", 0, instance.numberOfFaults());
    }

    @Test
    public void guessedLettersEmptyAtStart() {
        HangmanLogic instance = new HangmanLogic("PORKKANA");
        assertEquals("Guessed letters should be empty at the beginning", "", instance.guessedLetters());
    }

    @Test
    @Points("83.1")
    public void rightGuessHasNoEffectOnFaults() {
        String kirjain = "O";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        assertTrue("With a correct guess, the number of faults should not increase", instance.numberOfFaults() == 0);
    }

    @Test
    @Points("83.1")
    public void wrongGuessIncreasesFaults() {
        String kirjain = "A";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        assertTrue("With a wrong guess, the number of faults should increase by one", instance.numberOfFaults() == 1);
    }

    @Test
    @Points("83.1")
    public void guessedLetterIsAddedToTheSetOfGuessed() {
        String kirjain = "A";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        assertTrue("The guessed letter should be included to the already guessed letters."
                + "", instance.guessedLetters().equals(kirjain));
    }

    @Test
    @Points("83.1")
    public void  guessedLetterIsAddedToTheSetOfGuessed2() {
        String kirjain = "O";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        assertTrue("The guessed letter should be included to the already guessed letters.", instance.guessedLetters().equals(kirjain));
    }

    @Test
    @Points("83.1")
    public void guessedLetterIsAddedToTheSetOfGuessedOnlyOnce() {
        String kirjain = "A";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        instance.guessLetter(kirjain);
        assertTrue("The guessed letter should be included to the already guessed only once!"
                + "", instance.guessedLetters().equals(kirjain));
    }

    @Test
    @Points("83.1")
    public void numberOfFaultsDoesNotIncreaseWithAlreadyGuessedLetter() {
        String kirjain = "A";
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter(kirjain);
        instance.guessLetter(kirjain);
        assertTrue("Number of faults should not increase if a already guessed letter is "
                + "guessed again", instance.numberOfFaults() == 1);
    }

    @Test
    @Points("83.1")
    public void withTwoLetters() {
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter("A");
        instance.guessLetter("B");

        boolean ok = instance.guessedLetters().length() == 2
                && instance.guessedLetters().contains("A")
                && instance.guessedLetters().contains("B");

        assertTrue("The set of guessed letters should grow then two different letters guessed"
                + "", ok);
    }

    @Test
    @Points("83.2")
    public void hiddenWordLong() {
        HangmanLogic instance = new HangmanLogic("PORKKANA");
        String expResult = "________";
        String result = instance.hiddenWord().replaceAll("\\s+", "");
        assertEquals("Hidden version of word length of eight should be  ________", expResult, result);
    }

    @Test
    @Points("83.2")
    public void hiddenWordShort() {
        HangmanLogic instance = new HangmanLogic("MOOC");
        String expResult = "____";
        String result = instance.hiddenWord().replaceAll("\\s+", "");
        assertEquals("Hidden version of word length of four should be ____", expResult, result);
    }

    @Test
    @Points("83.2")
    public void hiddenWordChangesWithCorrectGuesses() {
        HangmanLogic instance = new HangmanLogic("MOOC");
        instance.guessLetter("O");
        String expResult = "_OO_";
        String result = instance.hiddenWord().replaceAll("\\s+", "");
        assertEquals("When the word MOOC has letter O correctly guessed,"
                + "the hidden word should be _OO_", expResult, result);
    }
}
