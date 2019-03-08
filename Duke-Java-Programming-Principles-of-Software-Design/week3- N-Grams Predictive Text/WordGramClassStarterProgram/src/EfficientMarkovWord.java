import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @author: salimt
 */


public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap <WordGram, ArrayList<String>> myMap;

/*
    public static void main(String[] args) {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovWord eff = new EfficientMarkovWord(2);
        eff.setTraining(st);
        eff.setRandom(42);
        System.out.println(eff.getRandomText(50));
        eff.printHashMapInfo();
    }*/

    public EfficientMarkovWord(int myOrder) {
        myMap = new HashMap <>();
        this.myOrder = myOrder;
    }

    public void setRandom(int seed){ this.myRandom = new Random(seed); }

    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
        printHashMapInfo(); }

    public ArrayList <String> getFollows(WordGram kGram){ return myMap.get(kGram); }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram wg = new WordGram(myText, index, myOrder);
        sb.append(wg.toString());
        sb.append(" ");

        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = myMap.get(wg);
            if (follows.size() == 0) { break;}
            String next = follows.get(myRandom.nextInt(follows.size()));
            wg = wg.shiftAdd(next);
            sb.append(next);
            sb.append(" ");

        }return sb.toString().trim();
    }

    public void buildMap(){
        for(int i=0; i<myText.length - (myOrder-1); i++){
            WordGram key = new WordGram(myText, i, myOrder);
            String follow = "";

            if(!(i+myOrder+1 >= myText.length)){
                follow = myText[i+myOrder+1];
            }
            if(myMap.containsKey(key)){
                myMap.get(key).add(follow);
            }else{
                myMap.put(key, new ArrayList<>(Arrays.asList(follow)));
            }
        }
    }

    public void printHashMapInfo(){
        int maxSize = Integer.MIN_VALUE;
        for(WordGram e: myMap.keySet()) {
            if (maxSize < myMap.get(e).size()) {
                maxSize = myMap.get(e).size();
            }
        }System.out.println("Number of keys: " + myMap.size() + "\nMax Num of ArrayList: " + maxSize);
    }

}
