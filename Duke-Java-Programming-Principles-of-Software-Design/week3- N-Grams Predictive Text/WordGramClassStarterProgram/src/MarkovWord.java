import java.util.ArrayList;
import java.util.Random;

/**
 * @author: salimt
 */


public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int myOrder) {
        this.myOrder = myOrder;
    }

    public void setRandom(int seed){ this.myRandom = new Random(seed); }

    public void setTraining(String text){ myText = text.split("\\s+"); }

    public int indexOf(String[] words, WordGram target, int start){
        for(int i=start; i<words.length-myOrder; i++){
            if(words[i].equals(target.wordAt(0))){
                if(new WordGram(words, i, myOrder).equals(target)){
                    return i;
                }
            }
        }return -1;
    }

    public ArrayList <String> getFollows(WordGram kGram){
        ArrayList<String> follows = new ArrayList<>();
        int idx = 0;
        while(true){
            idx = indexOf(myText, kGram, idx);
            if(idx == myText.length-myOrder || idx == -1){ break; }
            follows.add(myText[idx+myOrder]);
            idx++;
        }return follows;
    }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram wg = new WordGram(myText, index, myOrder );
        sb.append(wg.toString());
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(wg);
            if (follows.size() == 0) { break; }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            wg.shiftAdd(next);
            sb.append(next);
            sb.append(" ");
        }return sb.toString().trim();
    }

}
