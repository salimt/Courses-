import java.util.*;

public class Main {
    public static void main(String[] args) {
        NumberStatistics stats = new NumberStatistics();
        NumberStatistics evenNums = new NumberStatistics();
        NumberStatistics oddNums = new NumberStatistics();

        Scanner reader = new Scanner(System.in);
        System.out.println("Type numbers: ");

        while(true){
            int num = Integer.parseInt(reader.nextLine());
            if(num == -1){ break; }
            else if(num % 2 == 0){ evenNums.addNumber(num); }
            else{ oddNums.addNumber(num); }
            stats.addNumber(num);
        }
        System.out.println("sum: " + stats.sum());
        System.out.println("sum of evenNums: " + evenNums.sum());
        System.out.println("sum of oddNums: " + oddNums.sum());
    }
}

