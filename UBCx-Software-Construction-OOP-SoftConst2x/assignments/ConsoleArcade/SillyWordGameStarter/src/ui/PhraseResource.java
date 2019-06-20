/**
 * @author: salimt
 */

package ui;

import model.Phrase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.words.WordEntryType.*;

public class PhraseResource {

    public static final int NUM_OPTIONS = 4;

    private List<List<Phrase>> resources;

    public PhraseResource() {
        resources = new ArrayList<>();
        populateResources();
    }

    //EFFECTS: generates a phrase list from resources at random
    public List<Phrase> generatePhraseList(){
        Random r = new Random();
        int index = (r.nextInt(NUM_OPTIONS) % NUM_OPTIONS);
        return resources.get(index);
    }

    //MODIFIES: this
    //EFFECTS: adds all resources
    private void populateResources() {
        resources.add(vacationResource());
        resources.add(designPatternResource());
        resources.add(swimmingResource());
        resources.add(discoveredAnimal());
    }

    //EFFECTS: returns a phrase list about a vacation
    private List<Phrase> vacationResource() {
        List<Phrase> phrases = new ArrayList<>();

        Phrase p = new Phrase("My favourite place to go on vacation is .");
        p.addWordEntry(NOUN, 8, "place");
        phrases.add(p);

        p = new Phrase("It's so there.");
        p.addWordEntry(ADJECTIVE, 2);
        phrases.add(p);

        p = new Phrase("It's the kind of place where you can whenever you want.");
        p.addWordEntry(VERB_PRESENT_TENSE, 8);
        phrases.add(p);

        p = new Phrase("Every time I visit, I go to my favourite restaurant to eat , a local delicacy.");
        p.addWordEntry(NOUN, 12, "food");
        phrases.add(p);

        p = new Phrase("Last year, I tried here for the first time.");
        p.addWordEntry(VERB_SUFFIX_ING, 4);
        phrases.add(p);

        p = new Phrase("The experience was .");
        p.addWordEntry(ADJECTIVE, 3);
        phrases.add(p);

        p = new Phrase("I would recommend that you visit as soon as possible.");
        p.addWordEntry(ADVERB, 2);
        phrases.add(p);

        p = new Phrase("You can only get there by .");
        p.addWordEntry(NOUN, 6, "mode of transportation");
        phrases.add(p);

        p = new Phrase("It takes hours to get there, but the trip is worth it!");
        p.addWordEntry(NUMBER, 2);
        phrases.add(p);

        p = new Phrase("After all, it's not about the journey; it's about the .");
        p.addWordEntry(NOUN, 10);
        phrases.add(p);

        return phrases;
    }

    //EFFECTS: returns a phrase list about a design pattern
    private List<Phrase> designPatternResource() {
        List<Phrase> phrases = new ArrayList<>();

        Phrase p = new Phrase("My favourite design pattern is the pattern.");
        p.addWordEntry(NOUN, 6);
        phrases.add(p);

        p = new Phrase("This pattern is used to improve in your programs.");
        p.addWordEntry(NOUN, 6, "a positive quality, e.g. cheerfulness, glory");
        phrases.add(p);

        p = new Phrase("Before learning this pattern, programmers usually make a common error.");
        p.addWordEntry(ADJECTIVE, 4);
        phrases.add(p);

        p = new Phrase("They tend to while writing their code.");
        p.addWordEntry(VERB_PRESENT_TENSE, 3);
        phrases.add(p);

        p = new Phrase("With this design pattern, you minimize .");
        p.addWordEntry(PLURAL_NOUN, 6);
        phrases.add(p);

        p = new Phrase("The running time of any program with this pattern is 12 .");
        p.addWordEntry(PLURAL_NOUN, 11, "unit of time");
        phrases.add(p);

        p = new Phrase("Any program that computes is a good candidate for this pattern.");
        p.addWordEntry(PLURAL_NOUN, 4);
        phrases.add(p);

        p = new Phrase ("Before I learned about this pattern, I used to design programs.");
        p.addWordEntry(ADJECTIVE, 10);
        phrases.add(p);

        p = new Phrase("Now, I design my programs with this pattern.");
        p.addWordEntry(ADVERB, 5);
        phrases.add(p);

        p = new Phrase("I'm so that I know this technique.");
        p.addWordEntry(ADJECTIVE, 2, "emotion");
        phrases.add(p);

        return phrases;
    }

    //EFFECTS: returns a phrase list about swimming
    private List<Phrase> swimmingResource() {
        List<Phrase> phrases = new ArrayList<>();

        Phrase p = new Phrase("Today, you are going to learn how to become a great swimmer!");
        phrases.add(p);

        p = new Phrase("First, you need a .");
        p.addWordEntry(NOUN, 4, "article of clothing");
        phrases.add(p);

        p = new Phrase("Make sure it's comfortable and not .");
        p.addWordEntry(ADJECTIVE, 6);
        phrases.add(p);

        p = new Phrase("Start out at the end of the pool.");
        p.addWordEntry(ADJECTIVE, 4);
        phrases.add(p);

        p = new Phrase("Then once you're in, start with the paddle.");
        p.addWordEntry(NOUN, 7, "animal");
        phrases.add(p);

        p = new Phrase("Kick your like you're churning butter.");
        p.addWordEntry(PLURAL_NOUN, 2, "body part");
        phrases.add(p);

        p = new Phrase("You should now start to float .");
        p.addWordEntry(ADVERB, 6);
        phrases.add(p);

        p = new Phrase("Now, lift your left .");
        p.addWordEntry(NOUN, 4, "body part");
        phrases.add(p);

        p = new Phrase("Paddle on your left side like you're a dog trying to .");
        p.addWordEntry(VERB_PRESENT_TENSE, 11);
        phrases.add(p);

        p = new Phrase("While you're underwater, don't forget to .");
        p.addWordEntry(VERB_PRESENT_TENSE, 6);
        phrases.add(p);

        p = new Phrase("I know that with this lesson, swimming will now seem .");
        p.addWordEntry(ADJECTIVE, 10);
        phrases.add(p);

        p = new Phrase("We'll learn the paddle next week.");
        p.addWordEntry(NOUN, 3, "animal");
        phrases.add(p);

        return phrases;
    }

    //EFFECTS: returns a phrase list about a newly discovered animal
    private List<Phrase> discoveredAnimal() {
        List<Phrase> phrases = new ArrayList<>();

        Phrase p = new Phrase("Today, a new creature was discovered in .");
        p.addWordEntry(NOUN, 7, "place, fictional");
        phrases.add(p);

        p = new Phrase("This creature has ten .");
        p.addWordEntry(PLURAL_NOUN, 4, "body part");
        phrases.add(p);

        p = new Phrase("They are very rare, only found on a island nearby.");
        p.addWordEntry(ADJECTIVE, 8);
        phrases.add(p);

        p = new Phrase("The discoverers said they were to spot the creature.");
        p.addWordEntry(ADJECTIVE, 5, "emotion");
        phrases.add(p);

        p = new Phrase("The lead scientist, , said the creature may be endangered.");
        p.addWordEntry(NOUN, 3, "person's name");
        phrases.add(p);

        p = new Phrase("Only of them have been spotted to date.");
        p.addWordEntry(NUMBER, 1);
        phrases.add(p);

        p = new Phrase("In their natural habitat, these creatures build their nests out of .");
        p.addWordEntry(NOUN, 11, "material");
        phrases.add(p);

        p = new Phrase("Scientists suspect the creature's closest relative is .");
        p.addWordEntry(NOUN, 7, "animal");
        phrases.add(p);

        p = new Phrase("The lead candidate for this creature's name is the wild .");
        p.addWordEntry(NOUN, 10);
        phrases.add(p);
        return phrases;
    }
}
