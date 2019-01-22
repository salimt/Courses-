/**
 * @author: salimt
 */

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsInFiles {

    private HashMap<String, ArrayList<String>> wordsFiles;

    public WordsInFiles(){
        wordsFiles = new HashMap <String, ArrayList<String>>();
    }
    public static void main(String[] args) {
        WordsInFiles wf = new WordsInFiles();
        wf.tester();
    }

    public void addWordsFromFile(File f){
        FileResource r = new FileResource(f);

        for(String w: r.words()){
            if(!wordsFiles.containsKey(w)){
                ArrayList<String> fileNames = new ArrayList<String>();
                fileNames.add(f.getName());
                wordsFiles.put(w, fileNames);
            }else{
                ArrayList<String> fileNames = wordsFiles.get(w);
                if (!fileNames.contains(f.getName())) {
                    fileNames.add(f.getName());
                }
            }
        }
    }

    public void buildWordFileMap(){
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            addWordsFromFile(f);
        }System.out.println(wordsFiles);

    }

    public int maxNumber(){
        int highestVal = 0;
        for (Map.Entry<String, ArrayList<String>> entry : wordsFiles.entrySet()) {
            if(entry.getValue().size() > highestVal){
                highestVal = entry.getValue().size();
            }
        }
        return highestVal;
    }

    public ArrayList <String> wordsInNumFiles(int number){
        ArrayList<String> words = new ArrayList<String>();

        for (Map.Entry<String, ArrayList<String>> entry : wordsFiles.entrySet()) {
            if(entry.getValue().size() == number){
                words.add(entry.getKey());
            }
        }return words;
    }

    public void printFilesIn(String word){
        System.out.println(wordsFiles.get(word));
    }

    public void tester() {
        this.buildWordFileMap();
        int max = this.maxNumber();
        ArrayList <String> words = this.wordsInNumFiles(max);
        System.out.println(wordsInNumFiles(7).size()); //5

        //for (String word : words) {
        //    System.out.print(word + " appears in the files ");
        //    this.printFilesIn(word);
        //}
    }
}
