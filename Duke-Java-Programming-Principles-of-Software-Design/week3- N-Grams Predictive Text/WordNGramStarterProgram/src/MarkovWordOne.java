
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author salimt
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;

    public static void main(String[] args) {
        MarkovWordOne mw = new MarkovWordOne();
        String[] tt = "this is just a test yes this is a simple test".split(" ");
        mw.setRandom(20);
        mw.setTraining("this is just a test yes this is a simple test");
        mw.getRandomText(5);
    }
    
    public MarkovWordOne() { myRandom = new Random(); }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){ myText = text.split("\\s+"); }
	
	public String getRandomText(int numWords){
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length-1);  // random word to start with
		String key = myText[index];
		sb.append(key);
		sb.append(" ");
		for(int k=0; k < numWords-1; k++){
		    ArrayList<String> follows = getFollows(key);
		    if (follows.size() == 0) {
		        break;
		    }
			index = myRandom.nextInt(follows.size());
			String next = follows.get(index);
            //System.out.println(next);
			sb.append(next);
			sb.append(" ");
			key = next;
		}return sb.toString().trim();
	}

	private int indexOf(String[] word, String target, int start){
        for(int i=start; i<word.length; i++){
            if(word[i].equals(target)){ return i; }
        }return -1;
    }
	
	private ArrayList<String> getFollows(String key) {
	    ArrayList<String> follows = new ArrayList<>();
        int idx = indexOf(myText, key, 0)+1;
        follows.add(myText[idx]);
        while(true){
            idx = indexOf(myText, key, idx);
            if(idx == myText.length-1 || idx == -1){ break; }
            follows.add(myText[idx+1]);
            idx++;
        }return follows;
    }


    // TESTS
    public void testIndexOf(){
        String[] words = "this is just a test yes this is a simple test".split(" ");
        System.out.println(indexOf(words, "this", 3));
    }

}
