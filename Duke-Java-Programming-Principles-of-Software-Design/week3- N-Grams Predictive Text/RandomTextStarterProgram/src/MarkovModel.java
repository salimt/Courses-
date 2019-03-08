/**
 * @author: salimt
 */

import java.util.ArrayList;
import java.util.Random;

public class MarkovModel {
    private String myText;
    private Random myRandom;
    private int nOrder;

    public MarkovModel() {
        myRandom = new Random();
    }

    public void setRandom(int seed) { myRandom = new Random(seed); }

    public void setTraining(String s) { myText = s.trim(); }

    public void setN(int n){ nOrder = n; }

    public String getRandomText(int numChars) {
        if (myText == null) { return ""; }
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-nOrder);
        String key = myText.substring(index, index+nOrder);
        sb.append(key);

        for(int k=0; k < numChars-nOrder; k++){
            ArrayList<String> follows = getFollows(key);
            if(follows.size()==0){ break; }
            String next = follows.get(myRandom.nextInt(follows.size()));
            sb.append(next);
            key = key.substring(1) + next;
        }return sb.toString();
    }

    public ArrayList <String> getFollows(String key) {
        ArrayList <String> letters = new ArrayList <>();
        int pos = 0;
        while(pos<myText.length()){
            int start = myText.indexOf(key,pos);
            if(start==-1 || start+nOrder>=myText.length()-1){break;}
            letters.add(myText.substring(start+nOrder, start+nOrder+1));
            pos = start+nOrder;
        }return letters;
    }
}

