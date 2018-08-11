import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type exam scores, -1 completes: ");

        gradeDist(scanner);

        }

        //00–29	failed
        //30–34	1
        //35–39	2
        //40–44	3
        //45–49	4
        //50–60	5

        //EFFECTS: grades[6] equals number of scores that are between 0 < num <= 60
        //grades[score] increases by one if the given score between the required
        public static void gradeDist(Scanner scanner) {
            int[] grades = new int[7];


            while(true){
                int entry = Integer.parseInt(scanner.nextLine());
                if(entry == -1){ break; }
                else if(entry > 0 && 29 >= entry)  { grades[0]++; grades[6]++; }
                else if(entry >= 30 && 34 >= entry){ grades[1]++; grades[6]++; }
                else if(entry >= 35 && 39 >= entry){ grades[2]++; grades[6]++; }
                else if(entry >= 40 && 44 >= entry){ grades[3]++; grades[6]++; }
                else if(entry >= 45 && 49 >= entry){ grades[4]++; grades[6]++; }
                else if(entry >= 50 && 60 >= entry){ grades[5]++; grades[6]++; }
            }

            System.out.println("Grade distribution: ");
            for(int i = 5; i>=0; i-- ){
                System.out.println(i + ": " + new String(new char[grades[i]]).replace("\0", "*"));
            }System.out.println("Acceptance percentage: " + (100 * (double)(grades[6]-grades[0]) / (double)grades[6]));

    }
}
