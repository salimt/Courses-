/**
 * @author: salimt
 */

import edu.duke.FileResource;

import java.util.Arrays;

public class VigenereCipher {
    CaesarCipher[] ciphers;

    public static void main(String[] args) {
        VigenereCipher vc = new VigenereCipher(new int[4]);
        //FileResource fr = new FileResource("athens_keyflute.txt");
        //System.out.println(vc.sliceString(fr.asString(), 0, 3));
        //System.out.println(vc.tryKeyLength(fr.asString(),5, 'e' ));
        vc.breakVigenere();
    }
    
    public VigenereCipher(int[] key) {
        ciphers = new CaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new CaesarCipher(key[i]);
        }
    }
    
    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.encryptLetter(c));
            i++;
        }
        return answer.toString();
    }
    
    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            CaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.decryptLetter(c));
            i++;
        }
        return answer.toString();
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
        FileResource fr = new FileResource();
        String file = fr.asString();
        int[] keyList = tryKeyLength(file,4,'e');
        VigenereCipher vc = new VigenereCipher(keyList);
        System.out.println(vc.decrypt(file));
        //System.out.println(Arrays.toString(keyList));
    }
    
    public String toString() {
        return Arrays.toString(ciphers);
    }
    
}
