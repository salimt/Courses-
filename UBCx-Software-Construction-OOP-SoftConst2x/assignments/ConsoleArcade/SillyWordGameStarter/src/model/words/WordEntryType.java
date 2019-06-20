/**
 * @author: salimt
 */

package model.words;

public enum WordEntryType {
    ADJECTIVE("Adjective"), ADVERB("Adverb"),
    NOUN("Noun"), PLURAL_NOUN("Plural noun"),
    VERB_PRESENT_TENSE("Present tense verb (e.g. write, run)"),
    VERB_SUFFIX_ING("Verb ending in ing (e.g. writing, running)"),
    VERB_PAST_TENSE("Past tense verb (e.g. wrote, ran)"),
    NUMBER("Number"),
    EXCLAMATION("Exclamation (e.g. hooray, whoops)");

    private String instructions;

    WordEntryType(String value) {
        this.instructions = value;
    }

    //EFFECTS: returns the instructions for this word entry type
    public String getInstructions(){
        return instructions;
    }
}
