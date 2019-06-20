package ui;

import model.GymChain;
import model.cardio.CardioMachine;
import model.gym.DeluxeGym;
import model.gym.FitnessClass;
import model.gym.Gym;
import model.members.Member;
import model.weights.Weight;

import java.util.List;
import java.util.Scanner;

import static ui.GymChainInfo.SUNNY_GROVE_GYM_NAME;
import static ui.GymChainInfo.PINE_VALLEY_GYM_NAME;
import static ui.GymChainInfo.PALM_LAKE_GYM_NAME;
import static ui.GymChainInfo.YOUR_GYM_NAME;


public class InfoManager {


    private static final String YOUR_GYM_COMMAND = "ubc";
    private static final String MEMBERS_COMMAND = "members";
    private static final String SUNNY_GROVE_COMMAND = "sunny";
    private static final String PINE_VALLEY_COMMAND = "pine";
    private static final String PALM_LAKE_COMMAND = "palm";
    private static final String CLASSES_COMMAND = "classes";
    private static final String CARDIO_INFO_COMMAND = "cardio";
    private static final String WEIGHT_INFO_COMMAND = "weights";
    private static final String GO_BACK_COMMAND = "back";
    private static final String REGISTER_MEMBER_COMMAND = "r";
    private static final String QUIT_COMMAND = "quit";

    private Scanner input;
    private boolean runProgram;
    private GymChain chain;

    public InfoManager(GymChain chain){
        input = new Scanner(System.in);
        runProgram = true;
        this.chain = chain;
    }

    //EFFECTS: parses user input until user quits
    public void handleUserInput(){
        printInstructions();
        String str;

        while(runProgram){
            str = getUserInputString();
            parseInput(str);
        }
    }

    //EFFECTS: prints instructions to use kiosk
    private void printInstructions(){
        System.out.println("\nYou can request the following information:\n");
        System.out.println("Enter '" + MEMBERS_COMMAND + "' for FitLife members.");
        System.out.println("Enter '"+ SUNNY_GROVE_COMMAND + "' for FitLife " + SUNNY_GROVE_GYM_NAME);
        System.out.println("Enter '"+ PINE_VALLEY_COMMAND + "' for FitLife " + PINE_VALLEY_GYM_NAME);
        System.out.println("Enter '"+ PALM_LAKE_COMMAND + "' for FitLife " + PALM_LAKE_GYM_NAME);
        System.out.println("Enter '" + YOUR_GYM_COMMAND + "' for Fitlife " + YOUR_GYM_NAME);
        System.out.println("To quit at any time, enter '"+ QUIT_COMMAND +"'.");
    }

    private void printMemberInfo(){
        System.out.println("All FitLife members:");
        for(Member m : chain.getMembers()){
            System.out.println(m.getName()+ ", level: " + m.getLevel());
        }
    }

    //EFFECTS: prints out classes at this gym
    private void printClasses(Gym gym) {
        System.out.println("Number of classes at " + gym.getLocationName() +": " +
                gym.getFitnessClasses().size());
        for (FitnessClass fc : gym.getFitnessClasses()) {
            System.out.println(fc.getClassName() + ", " + fc.getDayAndTime());
            handleRegisterMember(fc);
        }
    }

    private void registerMemberForClass(Member m, FitnessClass fc){
        boolean eligible = m.canReserveClasses();

        if (eligible) {
            fc.registerMember(m);
            printRegisteredMembers(fc);
        } else {
            System.out.println("This member is not eligible to reserve classes. \n" +
                    "Their membership level is " + m.getLevel());
        }
    }

    private void printRegisteredMembers(FitnessClass fc){
        System.out.println("All members registered for " + fc.getClassName() + ": ");
        List<Member> memberList = fc.getMembersRegistered();

        for(Member m : memberList){
            System.out.println(m.getName());
        }
    }

    //EFFECTS: prints out the list of cardio machines at this gym
    private void printCardioMachines(Gym gym){
        System.out.println("FitLife " + gym.getLocationName() + " has the following cardio machines:");
        for(CardioMachine c : gym.getCardioMachines()){
            System.out.println(c.getClass().getSimpleName());
        }
    }

    //EFFECTS: prints out the list of weights at this gym
    private void printWeights(Gym gym){
        System.out.println("FitLife " + gym.getLocationName() + " has the following weights:");
        for(Weight w: gym.getWeights()){
            System.out.println(w.getClass().getSimpleName() + ", weight: " + w.getWeight());
        }
    }

    //EFFECTS: prints basic gym information
    private void printGymInfo(Gym gym){
        String gymType = chain.getGymType(gym);

        System.out.println("FitLife " + gym.getLocationName() + " is a " + gymType + " location.");
        System.out.println(gym.getLocationName() + " has the following amenities:\n" +
                "- "+ gym.getNumberOfCardioMachines() + " cardio machines");
        System.out.println("- " + gym.getNumberOfWeights() + " weights");
        System.out.println("- child care: " + gym.hasChildCare());
        System.out.println("- pool: " + gym.hasPool());
        if (gymType.equals("Deluxe"))
            System.out.println("- sauna: true\n- towel service: true\n");
        gymExtraInfoOptions(gym);
    }

    //EFFECTS: stops receiving user input
    public void endProgram(){
        System.out.println("Quitting...");
        input.close();
    }

    private void gymExtraInfoOptions(Gym gym){
        System.out.println("Enter '" + GO_BACK_COMMAND + "' to go back to the main menu.");
        System.out.println("Enter '" + CLASSES_COMMAND + "' for classes at " + gym.getLocationName());
        System.out.println("Enter '" + WEIGHT_INFO_COMMAND + "' for weights at " + gym.getLocationName());
        System.out.println("Enter '" + CARDIO_INFO_COMMAND + "' for cardio machines at "
                + gym.getLocationName());
        handleExtraInfoInput(gym);
    }

    private void handleExtraInfoInput(Gym gym){
        String str = getUserInputString();

        if (str.length() > 0) {
            switch (str) {
                case GO_BACK_COMMAND:
                    printInstructions();
                    break;
                case CLASSES_COMMAND:
                    printClasses(gym);
                    gymExtraInfoOptions(gym);
                    break;
                case WEIGHT_INFO_COMMAND:
                    printWeights(gym);
                    gymExtraInfoOptions(gym);
                    break;
                case CARDIO_INFO_COMMAND:
                    printCardioMachines(gym);
                    gymExtraInfoOptions(gym);
                    break;
                default:
                    parseInput(str);
            }
        }
    }

    private void handleRegisterMember(FitnessClass fc){
        System.out.println("Enter '" + REGISTER_MEMBER_COMMAND + "' to register a member for this class," +
                " or any other key to continue.");
        String str = getUserInputString();
        if(str.length() > 0 && str.equals(REGISTER_MEMBER_COMMAND)){
            System.out.println("Please enter the name of the member to be registered:");
            String memberName = getUserInputString();
            Member m = chain.lookupMemberByName(memberName);
            if (m != null) {
                registerMemberForClass(m, fc);
            } else {
                System.out.println("Invalid member name. Skipping this class.");
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    private void parseInput(String str){
        if (str.length() > 0) {
            switch (str) {
                case SUNNY_GROVE_COMMAND:
                    printGymInfo(chain.lookupGymByName(SUNNY_GROVE_GYM_NAME));
                    break;
                case PINE_VALLEY_COMMAND:
                    printGymInfo(chain.lookupGymByName(PINE_VALLEY_GYM_NAME));
                    break;
                case PALM_LAKE_COMMAND:
                    printGymInfo(chain.lookupGymByName(PALM_LAKE_GYM_NAME));
                    break;
                case YOUR_GYM_COMMAND:
                    printGymInfo(chain.lookupGymByName(YOUR_GYM_NAME));
                    break;
                case MEMBERS_COMMAND:
                    printMemberInfo();
                    printInstructions();
                    break;
                case QUIT_COMMAND:
                    runProgram = false;
                    endProgram();
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    printInstructions();
                    break;
            }
        }
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s){
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    private String getUserInputString(){
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makePrettyString(str);
        }
        return str;
    }
}
