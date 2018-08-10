package ui;

import model.FitnessClass;
import model.Gym;
import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import model.members.Member;
import model.weights.Barbell;
import model.weights.Dumbbell;
import model.weights.Plate;
import model.weights.WeightMachine;

import static model.members.MembershipLevel.BASIC;
import static model.members.MembershipLevel.CLASSES;
import static model.members.MembershipLevel.DELUXE;

public class FrontDeskKiosk {

    private Gym gym;
    private Member m1;
    private Member m2;
    private Member m3;

    public static void main(String[] args) {
        FrontDeskKiosk frontDeskKiosk = new FrontDeskKiosk();
        Kiosk kiosk = new Kiosk(frontDeskKiosk.getGym());
        System.out.println("Welcome to FitLifeGym!");

        kiosk.handleUserInput();
        kiosk.endProgram();

        System.out.println("Thank you for choosing FitLifeGym!");
    }

    public FrontDeskKiosk() {
        gym = new Gym();
        gym.setHasChildCare(true);
        gym.setHasPool(false);
        loadWeights();
        loadCardioMachines();
        loadMembers();
        loadClasses();
    }

    // getters
    public Gym getGym() { return gym; }

    // MODIFIES: this
    // EFFECTS: loads weights for the gym
    private void loadWeights() {
        gym.addWeight(new Barbell(100));
        gym.addWeight(new Plate(45));
        gym.addWeight(new Plate(45));
        gym.addWeight(new Plate(10));
        gym.addWeight(new Plate(10));
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new WeightMachine("quadriceps", 30));
        gym.addWeight(new WeightMachine("latissimus dorsi", 70));
    }

    // MODIFIES: this
    // EFFECTS: loads cardio machines for the gym
    private void loadCardioMachines() {
        gym.addCardioMachine(new Treadmill(60, 1, 3.0));
        gym.addCardioMachine(new Elliptical(30, 10));
        gym.addCardioMachine(new IndoorBike(15, 3));
    }

    // MODIFIES: this
    // EFFECTS: loads members at the gym
    private void loadMembers(){
        m1 = new Member("Amy");
        m1.setMembershipLevel(CLASSES);
        m2 = new Member("James");
        m2.setMembershipLevel(DELUXE);
        m3 = new Member("Michela");
        m3.setMembershipLevel(BASIC);

        gym.signUpMember(m1);
        gym.signUpMember(m2);
        gym.signUpMember(m3);
    }

    // MODIFIES: this
    // EFFECTS: loads classes for the gym
    private void loadClasses(){
        FitnessClass fc1 = new FitnessClass("LatinDanceFit", "Wednesdays at 5PM");
        fc1.registerMember(m2);
        fc1.registerMember(m1);
        FitnessClass fc2 = new FitnessClass("Kickboxing", "Mondays at 8AM");
        fc2.registerMember(m1);

        gym.addFitnessClass(fc1);
        gym.addFitnessClass(fc2);
    }


}