import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

//        int[] values = {6, 5, 8, 7, 11};
//        System.out.println("smallest: " + smallest(values));
//        System.out.println("Index of the smallest: " + indexOfTheSmallest(values));

//        int[] values = {-1, 6, 9, 8, 12};
//        System.out.println(indexOfTheSmallestStartingFrom(values, 1));
//        System.out.println(indexOfTheSmallestStartingFrom(values, 2));
//        System.out.println(indexOfTheSmallestStartingFrom(values, 4));

//        int[] val = {-1,3,1,7,4,5,2,4,3};
//        System.out.println(indexOfTheSmallestStartingFrom(val, 7));

//        int[] values = {3, 2, 5, 4, 8};
//
//        System.out.println( Arrays.toString(values) );
//
//        swap(values, 1, 0);
//        System.out.println( Arrays.toString(values) );
//
//        swap(values, 0, 3);
//        System.out.println( Arrays.toString(values) );

//        int[] values = {8, 3, 7, 9, 1, 2, 4};
//        sort(values);

    }

    public static int smallest(int[] array) {

        int minN = array[0];
        for(int i: array){
            if(minN > i){ minN = i; }
        }return minN;
    }

    public static int indexOfTheSmallest(int[] array) {
        int minNum = smallest(array);
        for(int i=0; i<array.length+1; i++){ if(array[i] == minNum) return i; }

        return -1;
    }

    public static int indexOfTheSmallestStartingFrom(int[] array, int index) {


        int[] sublist = Arrays.copyOfRange(array, index, array.length);
        int minNum = smallest(sublist);
        for(int i=index; i<array.length; i++){ if(array[i] == minNum) return i; }

        return -1;
    }

    public static void swap(int[] array, int index1, int index2) {

        int tempNum = array[index1];
        array[index1] = array[index2];
        array[index2] = tempNum;
    }

    public static void sort(int[] array) {

        for(int i=0; i<array.length; i++){
            int minN = indexOfTheSmallestStartingFrom(array, i);
            System.out.println(Arrays.toString(array));
            swap(array, minN, i);
        }
    }

}
