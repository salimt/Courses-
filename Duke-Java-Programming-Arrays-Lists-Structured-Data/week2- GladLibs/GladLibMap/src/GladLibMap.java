/**
 * @author: salimt
 */

import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList> myMap;
    private ArrayList<String> wordsInUse;
    private ArrayList<String> categoriesInUse;
    private Random myRandom;
    private String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private String dataSourceDirectory = "data";


    public GladLibMap(){
        myMap = new HashMap<>();
        wordsInUse = new ArrayList<String>();
        categoriesInUse = new ArrayList<String>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source){
        myMap = new HashMap<>();
        wordsInUse = new ArrayList<String>();
        categoriesInUse = new ArrayList<String>();
        initializeFromSource(source);
        myRandom = new Random();
    }

    public static void main(String[] args) {
        GladLibMap cc = new GladLibMap();
        cc.makeStory();
    }

    private void initializeFromSource(String source) {
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("adjective", "noun", "color", "country",
                                                              "name", "animal", "timeframe", "verb", "fruit"));

        for(int i=0; i<categories.size(); i++){
            String label = categories.get(i);
            myMap.put(label, readIt(source+"/"+label+".txt"));
        }
    }

    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if(label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }else if(myMap.containsKey(label)){
            return randomFrom(myMap.get(label));
        }
        return "**UNKNOWN**";
    }

    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        while(wordsInUse.contains(sub)){
            sub = getSubstitute(w.substring(first+1,last));
        }wordsInUse.add(sub);
        if(!categoriesInUse.contains(w.substring(first+1,last))) {
            categoriesInUse.add(w.substring(first + 1, last));
        }
        return prefix+sub+suffix;
    }

    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }

    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }

    public int totalWordsInMap() {
        int totalWords = 0;
        for (Map.Entry <String, ArrayList> entry : myMap.entrySet()) {
            totalWords += entry.getValue().size();
        }return totalWords;
    }

    public int totalWordsConsidered(){
        int wordNums = 0;
        for(int i=0; i<categoriesInUse.size(); i++){
            if(myMap.get(categoriesInUse.get(i))!=null){
                wordNums += myMap.get(categoriesInUse.get(i)).size();
            }
        }return wordNums;
    }

    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate.txt");
        printOut(story, 60);
        System.out.println("\n\nTotal number of words in all files: " + totalWordsInMap());
        System.out.println("Number of words thats categories in use: " + totalWordsConsidered());
    }

}



