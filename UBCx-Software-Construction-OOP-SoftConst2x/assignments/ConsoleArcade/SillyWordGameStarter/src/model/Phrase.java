/**
 * @author: salimt
 */

package model;

import model.words.WordEntry;
import model.words.WordEntryType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Phrase {

    private List<String> words;
    private boolean needsWord;
    private int wordEntryIndex;
    private WordEntry wordEntry;

    public Phrase(String phrase) {
        words = new ArrayList<>();
        setPhrase(phrase);
    }

    //EFFECTS: returns true if this phrase needs a word
    public boolean needsWord() { return needsWord; }

    //MODIFIES: this
    //EFFECTS: creates a new word entry of specified type to place at index in this phrase
    public void addWordEntry(WordEntryType type, int index) {
        wordEntry = new WordEntry(type, index);
        needsWord = true;
        wordEntryIndex = index;
    }

    //MODIFIES: this
    //EFFECTS: creates a new word entry of specified type to place at index in this phrase
    //         with specified description
    public void addWordEntry(WordEntryType type, int index, String description) {
        wordEntry = new WordEntry(type, index);
        wordEntry.setDescription(description);
        needsWord = true;
        wordEntryIndex = index;
    }

    //EFFECTS: returns the word entry of this phrase
    public WordEntry getNeededWordEntry() {
        return wordEntry;
    }

    //MODIFIES: this
    //EFFECTS: sets this phrase's value to phrase param
    public void setPhrase(String phrase) {
        Pattern spaceDelimiter = Pattern.compile(" ");
        String exploded[] = spaceDelimiter.split(phrase);
        for (int i=0; i<exploded.length; i++) {
            words.add(exploded[i]);
        }
    }

    //EFFECTS: fills this word entry with the given value
    public void fillWordEntry(String value) {
        wordEntry.setString(value);
        words.add(wordEntryIndex, wordEntry.toString());
        needsWord = false;
    }

    //EFFECTS: returns a complete string of this phrase's value
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String s : words) {
            str.append(s).append(" ");
        }
        return str.toString();
    }

}
