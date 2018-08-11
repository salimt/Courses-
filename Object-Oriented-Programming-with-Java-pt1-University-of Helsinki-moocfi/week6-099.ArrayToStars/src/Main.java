
public class Main {

    public static void main(String[] args) {
        // test the method here
        int[] array = {5, 1, 3, 4, 2};
        printArrayAsStars(array);
    }

    public static void printArrayAsStars(int[] array) {
        for(int i: array){
            for(int num=0; num<i; num++){
                System.out.print("*");
            }
            System.out.println("");
        }
    }
}
