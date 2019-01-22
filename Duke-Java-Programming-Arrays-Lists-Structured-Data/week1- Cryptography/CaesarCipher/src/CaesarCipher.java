/**
 * @author: salimt
 */

public class CaesarCipher {

    public static void main(String[] args) {
        // TEST
        System.out.println(encrypt("Can you imagine life WITHOUT the internet AND computers in your pocket?", 15));

        System.out.println(encryptTwoKeys("First Legion", 23, 17));
        System.out.println(encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21));



        String decrypted = encryptTwoKeys("Hfs cpwewloj loks cd Hoto kyg Cyy.", 26-14, 26-24);
        System.out.println(decrypted);

        /*
        for(int i=0; i<26; i++){
            for(int j=0; j<26; j++){
                System.out.println(encryptTwoKeys("Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!",i,j));
            }
        }


        FileResource fr = new FileResource();
        String message = fr.asString();
        for(int i=0; i<26; i++){
            for(int j=0; j<26; j++){
                String encrypted = encryptTwoKeys(message,i,j);
                if(encrypted.contains("Duke Computer Science Department Overview")){
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(encrypted);
                    System.out.println();
                }
                //System.out.println(encryptTwoKeys("Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!",i,j));
            }
        }

        int key = 17;
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message, key);
        System.out.println(encrypted);
        String decrypted = encrypt(encrypted, 26-key);
        System.out.println(decrypted);
        */
    }

    public static String encrypt(String input, int key) {
        //Make a StringBuilder with message (encrypted)
        StringBuilder encrypted = new StringBuilder(input);
        //Write down the alphabet
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //Compute the shifted alphabet
        String shiftedAlphabet = alphabet.substring(key) +
                alphabet.substring(0, key);
        //Count from 0 to < length of encrypted, (call it i)
        for (int i = 0; i < encrypted.length(); i++) {
            //Look at the ith character of encrypted (call it currChar)
            char currChar = encrypted.charAt(i);
            //Find the index of currChar in the alphabet (call it idx)
            int idx;
            if (!Character.isUpperCase(currChar)) {
                idx = alphabet.toLowerCase().indexOf(currChar);
            } else {
                idx = alphabet.indexOf(currChar);
            }

            //If currChar is in the alphabet
            if (idx != -1) {
                //Get the idxth character of shiftedAlphabet (newChar)
                char newChar;
                if (!Character.isUpperCase(currChar)) {
                    newChar = shiftedAlphabet.toLowerCase().charAt(idx);
                } else {
                    newChar = shiftedAlphabet.charAt(idx);
                }

                //Replace the ith character of encrypted with newChar
                encrypted.setCharAt(i, newChar);
            }
            //Otherwise: do nothing
        }
        //Your answer is the String inside of encrypted
        return encrypted.toString();
    }

    public static String encryptTwoKeys(String input, int key1, int key2) {
        String newInput = "";
        for(int i=0; i<input.length(); i++){
            if(i%2 == 0){
                newInput = newInput + encrypt(Character.toString(input.charAt(i)), key1);
            }else{
                newInput = newInput + encrypt(Character.toString(input.charAt(i)), key2);
            }
        }
        return newInput;
    }


}
