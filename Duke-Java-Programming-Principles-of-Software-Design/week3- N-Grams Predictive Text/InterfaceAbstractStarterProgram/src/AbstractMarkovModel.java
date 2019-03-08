
/**
 * Abstract class AbstractMarkovModel - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected Random myRandom;
    
    public AbstractMarkovModel() {
        myRandom = new Random();
    }
    
    public void setTraining(String s) {
        myText = s.trim();
    }

    protected ArrayList <String> getFollows(String key) {
        ArrayList <String> letters = new ArrayList <>();
        int pos = 0;
        while(pos<myText.length()){
            int start = myText.indexOf(key,pos);
            if(start==-1 || start+key.length()>=myText.length()-1){break;}
            letters.add(myText.substring(start+key.length(), start+key.length()+1));
            pos = start+key.length();
        }return letters;
    }
}
