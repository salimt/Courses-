import java.util.Scanner;

public class LoopsEndingRemembering {
    public static void main(String[] args) {
        
        Scanner reader = new Scanner(System.in);
        System.out.println("Type numbers:");

        int sum = 0;
        int numlen = 0;
        int evenNums = 0;
        int oddNums = 0;

        while(true){
            int num = Integer.parseInt(reader.nextLine());
            if(num == -1){
                break;
            }else{
                sum += num;
                numlen += 1;
                if(num%2 == 0){
                    evenNums += 1;
                }else{
                    oddNums += 1;
                }
            }
        }
        System.out.println("Thank you and see you later!");
        System.out.println("The sum is " + sum);
        System.out.println("How many numbers: " + numlen);
        System.out.println("Average: " + ((double)sum/(double)numlen));
        System.out.println("Even numbers: " + evenNums);
        System.out.println("Odd numbers: " + oddNums);
    }
}
