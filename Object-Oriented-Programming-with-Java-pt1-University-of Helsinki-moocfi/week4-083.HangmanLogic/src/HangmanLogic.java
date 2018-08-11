public class HangmanLogic {

    private String word;
    private String guessedLetters;
    private int numberOfFaults;

    public HangmanLogic(String word) {
        this.word = word.toUpperCase();
        this.guessedLetters = "";
        this.numberOfFaults = 0;
    }

    public int numberOfFaults() {
        return this.numberOfFaults;
    }

    public String guessedLetters() {
        return this.guessedLetters;
    }

    public int losingFaultAmount() {
        return 12;
    }

    public void guessLetter(String letter) {

        letter = letter.toUpperCase();
        if (this.word.contains(letter)) {
            this.guessedLetters += letter;
        } else {
            if (!(guessedLetters.contains(letter))) {
                this.guessedLetters += letter;
                this.numberOfFaults++;
            }
        }
    }


    public String hiddenWord() {
        // return the hidden word at the end
        String hiddenWord = "";

        for (int count = 0; count < this.word.length(); count++) {
            char thisLetter = this.word.charAt(count);
            String stringLetter = "" + thisLetter;

            if (guessedLetters.contains(stringLetter)) {
                hiddenWord += thisLetter;
            } else {
                hiddenWord += "_";
            }
        }
        return hiddenWord;
    }

}
