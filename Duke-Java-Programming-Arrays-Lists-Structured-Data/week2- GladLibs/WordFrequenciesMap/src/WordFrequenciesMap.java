/**
 * @author: salimt
 */

import edu.duke.*;
import java.util.*;

public class WordFrequenciesMap {

    public static void main(String[] args) {
        tester();
    }

    public static void countWords(String filename){
        FileResource fr = new FileResource(filename);
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Integer> counters = new ArrayList<Integer>();
        for(String w : fr.words()){
            w = w.toLowerCase();
            int index = words.indexOf(w);
            if (index == -1){
                words.add(w);
                counters.add(1);
            }
            else {
                int value = counters.get(index);
                counters.set(index, value + 1);
            }
        }
        int total = 0;
        for(int k=0; k < words.size(); k++){
            if (counters.get(k) > 500){
                System.out.println(counters.get(k)+"\t"+words.get(k));
            }
            total += counters.get(k);
        }
        System.out.println("total count: "+total+" different = "+words.size());
    }

    public static void countWordsMap(String filename){
        FileResource fr = new FileResource(filename);
        HashMap<String,Integer> map = new HashMap<String,Integer>();

        for(String w : fr.words()){
            w = w.toLowerCase();
            if (!map.containsKey(w)){
                map.put(w,1);
            }
            else {
                map.put(w,map.get(w)+1);
            }
        }
        int total = 0;
        for(String w : map.keySet()){
            int value = map.get(w);
            if (value > 500){
                System.out.println(value+"\t"+w);
            }
            total += value;
        }
        System.out.println("total count: "+total+" different = "+map.keySet().size());
    }

    public static void tester(){
        String filename = "data/confucius.txt";
        double start = System.currentTimeMillis();
        countWords(filename);
        double end = System.currentTimeMillis();
        double time = (end-start)/1000;
        System.out.println("time = "+time);
        start = System.currentTimeMillis();
        countWordsMap(filename);
        end = System.currentTimeMillis();
        time = (end-start)/1000;
        System.out.println("time = "+time);
    }

}
