import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> birds = new ArrayList<String>();
        int[] birdObserv = new int[150];

        Scanner scanner = new Scanner(System.in);


        //EFFECTS:
        //Add          adds a bird
        //Observation  adds an observation
        //Statistics   prints all the birds
        //Show         prints one bird
        //Quit         terminates the program
        while(true) {
            System.out.print("? ");
            String entry = scanner.nextLine();

            if (entry.equals("Quit")) {
                break;
            }

            if (entry.equals("Add")) {
                System.out.print("Name: ");
                String birdName = scanner.nextLine();
                System.out.print("Latin Name: ");
                String latinName = scanner.nextLine();
                birds.add(birdName + " (" + latinName + ")");
                continue;
            }

            if (entry.equals("Observation")) {
                System.out.print("What was observed:? ");
                String birdName = scanner.nextLine();
                boolean found = false;
                for (String b : birds) {
                    if ((b.contains(birdName))) {
                        birdObserv[birds.indexOf(b)]++;
                        found = true;
                    }
                }if(found == false){ System.out.println("Is not a bird!"); }
            }

            if (entry.equals("Statistics")) {
                for (String b : birds) {
                    System.out.println(b + ": " + birdObserv[birds.indexOf(b)] + " observations");
                }
            }


            if (entry.equals("Show")) {
                System.out.print("What? ");
                String birdName = scanner.nextLine();
                for(String b: birds){
                    if (b.contains(birdName)) {
                        System.out.println(b + ": " + birdObserv[birds.indexOf(b)] + " observations");
                    }
                }
            }

        }
    }

}
