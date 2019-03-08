/**
 * @author: salimt
 */

import java.util.ArrayList;
import java.util.Random;

public class MarkovOne extends AbstractMarkovModel{

    public MarkovOne() {
        myRandom = new Random();
    }

    public void setRandom(int seed) { myRandom = new Random(seed); }

    public void setTraining(String s) { myText = s.trim(); }

    public String getRandomText(int numChars) {
        if (myText == null) { return ""; }
        StringBuilder sb = new StringBuilder();
        String key = Character.toString(myText.charAt(myRandom.nextInt(myText.length()-1)));
        sb.append(key);

        for (int k = 0; k < numChars; k++) {
            ArrayList<String> lettrs = getFollows(key);
            key = lettrs.get(myRandom.nextInt(lettrs.size()));
            sb.append(key);
        }return sb.toString();
    }

    public String toString(){
        return "MarkovModel of order 1.";
    }
}

