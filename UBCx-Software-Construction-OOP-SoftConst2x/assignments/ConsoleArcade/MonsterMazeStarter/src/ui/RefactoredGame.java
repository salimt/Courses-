//package ui;
//
//import model.Choice;
//import model.Monster;
//import model.Room;
//import model.Treasure;
//
//import java.util.Scanner;
//
//public class RefactoredGame {
//
//    private static final String INVALID_CHOICE = "You have entered an invalid choice. Please try again.";
//    private static final String ANOTHER_ROUND = "Y";
//    private static final String STOP = "N";
//    private static final String QUIT = "Q";
//
//    private Choice current;
//    private Choice start;
//    private Scanner scanner;
//    private boolean gameOver;
//
//    public RefactoredGame(Choice c) {
//        start = c;
//        current = start;
//        scanner = new Scanner(System.in);
//        gameOver = false;
//        playGame();
//    }
//
//    //MODIFIES: this
//    //EFFECTS: runs gameplay loop including parsing user input and displaying options
//    private void playGame() {
//        printInstructions();
//
//        while(!gameOver) {
//            parsePlayerNextInstruction();
//            if (gameOver) break;
//            current.printOutcome();
//            if (roundOver())
//                offerAnotherRound();
//        }
//
//        System.out.println("You have escaped...");
//        scanner.close();
//    }
//
//    //EFFECTS: prints options for player to try again
//    private void offerAnotherRound() {
//        System.out.println("\nThere are no more choices; you have reached the end of the maze.");
//        System.out.println("Would you like to play again? enter Y (Yes) or N (No): ");
//    }
//
//    //EFFECTS: waits for & parses player's choice
//    private void parsePlayerNextInstruction() {
//        String str = "";
//        while (str.length() == 0)  {
//            if (scanner.hasNext())
//                str += scanner.nextLine();
//        }
//        str = str.trim();
//        handleInput(str);
//    }
//
//    //EFFECTS: selects next choice if s is a number, or quits/continues if s is a valid string
//    private void handleInput(String s) {
//        if (s.length() > 0) {
//            try {
//                int input = Integer.parseInt(s);
//                choose(input);
//            } catch (NumberFormatException e){
//                updateGameState(s.toUpperCase());
//            }
//        }
//    }
//
//    //EFFECTS: sets current choice to input, if valid
//    private void choose(int input) {
//        if (current instanceof Room) {
//            Room r = (Room) current;
//            try {
//                if (input <= 0) throw new Exception();
//                current = r.getChoice(input);
//            } catch (Exception e) {
//                System.out.println(INVALID_CHOICE);
//            }
//        }
//    }
//
//    //EFFECTS: interprets user command to continue or quit game
//    private void updateGameState(String command) {
//        switch(command) {
//            case QUIT:
//            case STOP:
//                gameOver = true;
//                break;
//            case ANOTHER_ROUND:
//                current = start;
//                break;
//            default:
//                System.out.println(INVALID_CHOICE);
//                break;
//        }
//    }
//
//    //EFFECTS: prints initial instructions for the game
//    private void printInstructions() {
//        System.out.println("Welcome to Monster Maze, a dangerous 'choose your own ending' game.");
//        System.out.println("You will travel through the maze by selecting a choice out of every set of options.");
//        System.out.println("Once you make a choice, you cannot go backwards.");
//        System.out.println("Enter q (Quit) at any time to escape the maze.");
//        System.out.println("Good luck!\n");
//
//        System.out.println("For each set of options, enter the number corresponding to your choice.\n");
//
//        current.printOutcome();
//    }
//
//    //EFFECTS: produces true if the current choice has no more options
//    private boolean roundOver() {
//        return (current instanceof Monster) || (current instanceof Treasure);
//    }
//
//}
