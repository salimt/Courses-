
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] original = {1, 2, 3, 4};
        int[] copied = copy(original);
        int[] reverse = reverseCopy(original);


        // change the copied
        copied[0] = 99;

        // print both
        System.out.println( "original: " + Arrays.toString(original));
        System.out.println( "copied: " + Arrays.toString(copied));
        System.out.println( "reversed: " +Arrays.toString(reverse));
    }

    public static int[] copy(int[] array){
        int[] copyArray = array.clone();

        return copyArray;
    }

    public static int[] reverseCopy(int[] array){
        int[] reverseArray = new int[array.length];

        for(int i=0; i<array.length; i++){ reverseArray[i] = array[array.length-i-1]; }

        return reverseArray;
    }


}
