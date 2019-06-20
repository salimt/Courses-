/**
 * @author: salimt
 */

package model.words;

public class WordEntry {

    private String word;
    private String description;
    private WordEntryType type;

    public WordEntry(WordEntryType type, int index) {
        this.word = "";
        this.description = "";
        this.type = type;
    }

    //EFFECTS: returns this word's value
    @Override
    public String toString(){
        return word;
    }

    //EFFECTS: returns the description of this word's value
    public String getDescription() {
        return description;
    }

    //MODIFIES: this
    //EFFECTS: sets this word's value to word
    public void setString(String word){
        this.word = word;
    }

    //MODIFIES: this
    //EFFECTS: sets this word's value to
    public void setDescription(String description) {
        this.description = description;
    }

    //EFFECTS: returns the type of this word entry
    public WordEntryType getType(){
        return type;
    }
}
