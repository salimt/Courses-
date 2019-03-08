/**
 * Write a description of class MarkovWordTwo here.
 *
 * @author salimt
 */

import java.util.*;

public class MarkovWordTwo implements IMarkovModel {
    private String[] myText;
    private Random myRandom;

    public static void main(String[] args) {
        MarkovWordTwo mw = new MarkovWordTwo();
        String[] tt = "this is just a test yes this is a simple test".split(" ");
        mw.setRandom(20);
        mw.setTraining("this is just a test yes this is a simple test");
        mw.getRandomText(5);
    }

    public MarkovWordTwo() { myRandom = new Random(); }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){ myText = text.split("\\s+"); }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-2);
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");

        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key1, key2);

            if (follows.size() == 0) { break; }

            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }return sb.toString().trim();
    }

    private int indexOf(String[] word, String target1, String target2, int start){
        for(int i=start; i<word.length-1; i++){
            if(word[i].equals(target1) && word[i+1].equals(target2)){ return i; }
        }return -1;
    }

    private ArrayList<String> getFollows(String key1, String key2) {
        ArrayList<String> follows = new ArrayList<>();
        int idx = 0;
        while(true){
            idx = indexOf(myText, key1, key2, idx);
            if(idx == myText.length-2 || idx == -1){ break; }
            follows.add(myText[idx+2]);
            idx++;
        }return follows;
    }


    // TESTS
    /*
    public void testIndexOf(){
        String[] words = "this is just a test yes this is a simple test".split(" ");
        System.out.println(indexOf(words, "this", 3));
    }*/

}
