package ui;


import java.util.Scanner;

import model.FitnessClass;
import model.Gym;
import model.cardio.CardioMachine;
import model.members.Member;
import model.members.Visit;
import model.weights.Weight;

import static model.members.MembershipLevel.BASIC;
import static model.members.MembershipLevel.CLASSES;
import static model.members.MembershipLevel.DELUXE;


public class Kiosk {

    private static final String CHECKIN_COMMAND = "check in";
    private static final String HISTORY_COMMAND = "history";
    private static final String UPGRADE_COMMAND = "upgrade";
    private static final String CLASSES_COMMAND = "classes";
    private static final String INFO_COMMAND = "info";
    private static final String SIGNUP_COMMAND = "sign up";
    private static final String CARDIO_INFO_COMMAND = "cardio";
    private static final String WEIGHT_INFO_COMMAND = "weights";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private boolean runProgram;
    private Gym gym;
    private Member m;

    public Kiosk(Gym gym){
        input = new Scanner(System.in);
        runProgram = true;
        this.gym = gym;
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput(){
        System.out.println("How can I help you today?");
        printInstructions();
        String str;

        while(runProgram){
            if(input.hasNext()) {
                str = input.nextLine();
                str = makePrettyString(str);
                parseInput(str);
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    private void parseInput(String str){
        if (str.length() > 0) {
            switch (str) {
                case CHECKIN_COMMAND:
                    handleCheckIn();
                    break;
                case HISTORY_COMMAND:
                    printHistory();
                    break;
                case UPGRADE_COMMAND:
                    handleUpgrade();
                    break;
                case CLASSES_COMMAND:
                    printClasses();
                    break;
                case INFO_COMMAND:
                    printGymInfo();
                    break;
                case SIGNUP_COMMAND:
                    handleMembershipActivation();
                    break;
                case CARDIO_INFO_COMMAND:
                    printCardioMachines();
                    break;
                case WEIGHT_INFO_COMMAND:
                    printWeights();
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    break;
            }
        }
    }

    //EFFECTS: prints instructions to use kiosk
    private void printInstructions(){
        System.out.println("\nEnter '"+ INFO_COMMAND +"' for information about the gym.");
        if(m == null) {
            System.out.println("Enter '" + SIGNUP_COMMAND + "' to sign up for a membership.");
        } else {
            System.out.println("To check in, enter '"+ CHECKIN_COMMAND +"'. To see your visit history,\n" +
                    "enter '"+ HISTORY_COMMAND +"'.");
            if(m.canReserveClasses()){
                System.out.println("For a list of this gym's classes, enter '"+ CLASSES_COMMAND +"'.");
            } else {
                System.out.println("At your current membership level, you cannot \n" +
                        "attend fitness classes. \nTo upgrade your membership, enter '"+ UPGRADE_COMMAND +"'.");
            }
        }
        System.out.println("To quit at any time, enter '"+ QUIT_COMMAND +"'.");
    }

    //MODIFIES: this
    //EFFECTS: checks in this member to the gym
    private void handleCheckIn(){
        if (m == null){
            printMemberErrorMessage();
        } else {
            m.checkIn();
            System.out.println("Your visit history has been updated.");
            printInstructions();
        }
    }

    //EFFECTS: prints visit history for member
    private void printHistory(){
        if (m == null) {
            printMemberErrorMessage();
        } else {
            System.out.println("Visited " + m.getVisitHistory().size() + " times.");
            for (Visit v : m.getVisitHistory()){
                System.out.println("Checked in: " + v.getDate());
            }
            printInstructions();
        }
    }

    //MODIFIES: this
    //EFFECTS: upgrades this member's level, if possible
    private void handleUpgrade(){
        if (m == null){
            printMemberErrorMessage();
        } else if (m.getLevel() == DELUXE) {
            System.out.println("You already have the highest tier of membership.");
        } else {
            m.upgrade();
            System.out.println("Confirmed. Your level is now " + m.getLevel().toString());
        }
        printInstructions();
    }

    //EFFECTS: prints out classes at this gym
    private void printClasses(){
        for (FitnessClass fc: gym.getFitnessClasses()) {
            System.out.println(fc.getClassName() + " is on " + fc.getDayAndTime() +
                    "\nMax participants: " + fc.getMaxParticipants());
        }
        printInstructions();
    }

    //EFFECTS: prints error message if member is not yet signed up
    private void printMemberErrorMessage(){
        System.out.println("Error: you are not signed up. To sign up,\n" +
                "enter '"+ SIGNUP_COMMAND +"'.");
    }

    //EFFECTS: prints basic gym information
    private void printGymInfo(){
        String poolString;
        String childCareString;
        if(gym.hasPool()) {
            poolString = "We do have a pool.";
        } else {
            poolString = "We do not have a pool.";
        }
        if(gym.hasChildCare()){
            childCareString = "We do have child care.";
        } else {
            childCareString = "We do not have child care.";
        }

        System.out.println("We are pleased to advertise the following amenities.\n" +
                "FitLife has " + gym.getNumberOfCardioMachines() + " cardio machines and " +
                gym.getNumberOfWeights() + " weights.");
        System.out.println(poolString + " " + childCareString);
        System.out.println("\nTo learn more about cardio machines, enter '"+ CARDIO_INFO_COMMAND + "'.\n" +
                "To learn more about weight machines, enter '"+ WEIGHT_INFO_COMMAND +"'.");
        printInstructions();
    }

    //MODIFIES: this
    //EFFECTS: activates membership with prompts and user inputs
    private void handleMembershipActivation(){
        System.out.println("Thank you for choosing FitLifeGym! To activate \n" +
                           "your membership, we will need the following information.\n" +
                           "Please enter your name:");

        String name = input.nextLine();
        m = new Member(name);
        System.out.println("Please choose a membership level: 'b' for basic, \n" +
                           "'c' for classes included, or 'd' for deluxe.");
        String membershipLevel = input.next();

        if (membershipLevel.equals("c")) {
            m.setMembershipLevel(CLASSES);
        } else if (membershipLevel.equals("d")){
            m.setMembershipLevel(DELUXE);
        } else {
            m.setMembershipLevel(BASIC);
        }

        gym.signUpMember(m);

        System.out.println("Confirmed. " + m.getName() + " has been signed up " +
                           "as a member \nwith level " + m.getLevel().toString());
        printInstructions();
    }

    //EFFECTS: prints out the list of cardio machines at this gym
    private void printCardioMachines(){
        System.out.println("FitLife has the following cardio machines:");
        for(CardioMachine c : gym.getCardioMachines()){
            System.out.println(c.getClass().getSimpleName());
        }
        System.out.println("\nTo learn more about weight machines, enter '"+ WEIGHT_INFO_COMMAND +"'.");
        printInstructions();
    }

    //EFFECTS: prints out the list of weights at this gym
    private void printWeights(){
        System.out.println("FitLife has the following weights:");
        for(Weight w: gym.getWeights()){
            System.out.println(w.getClass().getSimpleName() + ", weight: " + w.getWeight());
        }
        System.out.println("\nTo learn more about cardio machines, enter '"+ CARDIO_INFO_COMMAND +"'.");
        printInstructions();
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s){
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    //EFFECTS: stops receiving user input
    public void endProgram(){
        System.out.println("Quitting...");
        input.close();
    }
}
