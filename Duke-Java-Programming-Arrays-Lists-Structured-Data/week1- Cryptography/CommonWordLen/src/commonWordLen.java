/**
 * @author: salimt
 */

import edu.duke.FileResource;

import java.util.*;

public class commonWordLen {
    public static void main(String[] args) {
        FileResource fr = new FileResource();
        String message = fr.asString();
        Integer encrypted = commonLen(message);
        System.out.println(encrypted);
    }

    public static Integer commonLen(String input){
        int[] lengths = new int[20];
        String[] words = input.split("[\\p{Punct}\\s]+");
        for(int i=0; i<words.length; i++){
            //System.out.println(words[i].length());
            lengths[words[i].length()] += 1;
        }
        System.out.println(Arrays.toString(lengths));
        return 5;
    }
}
