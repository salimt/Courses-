import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.*;

/**
 * @author: salimt
 */

public class VigenereBreaker {

    public static void main(String[] args) {
        //FileResource fr = new FileResource("src/dictionaries/English");
        VigenereBreaker vc = new VigenereBreaker();
        //HashSet<String> dict = vc.readDictionary(fr);
        //System.out.println(vc.countWords("Abraham, Abram, kkqkq", dict));
        //FileResource msg = new FileResource("src/titus-small_key5.txt");
        //System.out.println(vc.breakForLanguage(msg.asString(), dict));
        vc.breakVigenere();
        //vc.mostCommonCharIn(dict);
    }

    public VigenereBreaker() {

    }

    public String sliceString(String message, int whichSlice, int totalSlice){
        String slicedStr = "";
        for(int i=whichSlice; i<message.length(); i+=totalSlice){
            slicedStr = slicedStr + message.charAt(i);
        }return slicedStr;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon){
        int[] keys = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for(int i=0; i<klength; i++){
            keys[i] = cc.getKey(sliceString(encrypted, i, klength));
        }return keys;
    }

    public void breakVigenere(){
        /*
        VigenereBreaker vc = new VigenereBreaker();
        FileResource fr = new FileResource();
        FileResource dict = new FileResource("src/dictionaries/English");
        System.out.println(vc.breakForLanguage(fr.asString(), vc.readDictionary(dict)));
        //System.out.println(Arrays.toString(tryKeyLength(vc.breakForLanguage(fr.asString(), vc.readDictionary(dict)), )));*/

        HashMap<String, HashSet<String>> languages = new HashMap<>();
        VigenereBreaker vc = new VigenereBreaker();
        DirectoryResource dr = new DirectoryResource();
        FileResource encrypted = new FileResource("src/athens_keyflute.txt");
        for(File fr: dr.selectedFiles()){
            languages.put(fr.getName(), vc.readDictionary(new FileResource(fr)));
        }
        vc.breakForAllLanguages(encrypted.asString(), languages);
    }

    public HashSet readDictionary(FileResource fr){
        HashSet<String> words = new HashSet <>();
        for(String k: fr.lines()){
            words.add(k.toLowerCase());
        }return words;
    }

    public int countWords(String message, HashSet dict){
        String[] newMsg = message.toLowerCase().split("\\W");
        int newMsgLen = newMsg.length;
        ArrayList <String> words = new ArrayList <>();

        for(int i=0; i<newMsgLen; i++){
            if(dict.contains(newMsg[i])){words.add(newMsg[i]); }
        }return words.size();
    }

    public String breakForLanguage(String encrypted, HashSet dict){
        String msg = "";
        int highest = 0;
        int keyLen = 0;
        char mostCommonChr = mostCommonCharIn(dict);

        for(int i=1; i<100; i++){
            String decrypted = new VigenereCipher(tryKeyLength(encrypted, i, mostCommonChr)).decrypt(encrypted);
            int counter = countWords(decrypted, dict);
            if(i==38){ System.out.println("When Key is " + i + ", number of valid words are: " + counter); }
            if(counter > highest){
                msg = decrypted; highest = counter; keyLen = tryKeyLength(encrypted, i, mostCommonChr).length;
            }
        }System.out.println("Valid Words: " + highest + "\nKey Length: " + keyLen + "\n");
        return msg;
    }

    public char mostCommonCharIn(HashSet<String> dict){
        HashMap<Character, Integer> letters = new HashMap <>();
        for(String word: dict){
            for(int i=0; i<word.length(); i++){
                if(!letters.containsKey(word.charAt(i))){
                    letters.put(word.charAt(i), 1);
                }else{ letters.put(word.charAt(i), letters.get(word.charAt(i))+1); }
            }
        }return Collections.max(letters.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public void breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages){
        String msg = "";
        String trueLan = "";
        int highest = 0;

        for(String lang: languages.keySet()){
            for(int i=1; i<100; i++){
                String decrypted = new VigenereCipher(tryKeyLength(encrypted, i,
                                        mostCommonCharIn(languages.get(lang)))).decrypt(encrypted);
                int counter = countWords(decrypted, languages.get(lang));
                if(counter > highest){ msg = decrypted; highest = counter; trueLan = lang; }
            }
        }System.out.println(msg);
        System.out.println("The most possible language: " + trueLan);
    }
}
