package ui;

import model.GymChain;
import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import model.gym.DeluxeGym;
import model.gym.FitnessClass;
import model.gym.Gym;
import model.members.Member;
import model.weights.*;

import static model.members.MembershipLevel.BASIC;
import static model.members.MembershipLevel.CLASSES;
import static model.members.MembershipLevel.DELUXE;

public class GymChainInfo {

    public static final String SUNNY_GROVE_GYM_NAME = "Sunny Grove";
    public static final String PINE_VALLEY_GYM_NAME = "Pine Valley";
    public static final String PALM_LAKE_GYM_NAME = "Palm Lake";
    public static final String AMY_MEMBER_NAME = "Amy";
    public static final String JAMES_MEMBER_NAME = "James";
    public static final String MICHELA_MEMBER_NAME = "Michela";
    public static final String YOUR_GYM_NAME = "UBC";
    public static final String YOUR_MEMBER_NAME = "Sazi";


    public static void main(String[] args) {
        GymChain chain = new GymChain();
        loadGyms(chain);
        loadMembers(chain);

        FitnessClass cycle = new FitnessClass("Indoor Cycling", "Tuesday at 12:30PM");
        chain.joinGymIncentive("Elisa", cycle);

        Gym myGym = new Gym(YOUR_GYM_NAME);
        initializeGym0(myGym);
        myGym.addFitnessClass(cycle);
        chain.addGym(myGym);

        Member m0 = new Member("Raeanne");
        m0.setMembershipLevel(CLASSES);
        myGym.guideMemberThroughWorkout(m0);

        InfoManager info = new InfoManager(chain);
        System.out.println("You are now in the FitLife information console.");
        info.handleUserInput();
    }

    private static void loadMembers(GymChain chain){
        Member m1 = new Member(AMY_MEMBER_NAME);
        m1.setMembershipLevel(CLASSES);
        Member m2 = new Member(JAMES_MEMBER_NAME);
        m2.setMembershipLevel(DELUXE);
        Member m3 = new Member(MICHELA_MEMBER_NAME);
        m3.setMembershipLevel(BASIC);

        chain.signUpMember(m1);
        chain.signUpMember(m2);
        chain.signUpMember(m3);

        Member m4 = new Member(YOUR_MEMBER_NAME);
        m4.setMembershipLevel(DELUXE);
        chain.signUpMember(m4);
    }

    private static void loadGyms(GymChain chain){
        Gym g1 = new Gym(SUNNY_GROVE_GYM_NAME);
        initializeGym1(g1);
        chain.addGym(g1);

        Dumbbell d = new Dumbbell(30);

        g1.setHasPool(true);
        d.setWeight(35);
        g1.addWeight(d);

        Gym g2 = new DeluxeGym(PINE_VALLEY_GYM_NAME);
        initializeGym2(g2);
        chain.addGym(g2);

        Gym g3 = new Gym(PALM_LAKE_GYM_NAME);
        initializeGym3(g3);
        chain.addGym(g3);
    }


    private static void initializeGym0(Gym gym){
         Barbell bar = new Barbell(45);
         WeightMachine wm = new WeightMachine("pectoralis", 50);
         IndoorBike bike = new IndoorBike();
         gym.addWeight(bar);
         gym.addWeight(wm);
         gym.addCardioMachine(bike);
    }

    private static void initializeGym1(Gym gym){
        gym.setHasChildCare(true);
        gym.setHasPool(true);
        gym.addWeight(new Barbell(135));
        gym.addWeight(new Plate(20));
        gym.addWeight(new Plate(20));
        gym.addWeight(new WeightMachine("biceps", 50));
        gym.addCardioMachine(new IndoorBike());
        gym.addCardioMachine(new Treadmill());
        FitnessClass fc = new FitnessClass("HIIT", "Friday at 4PM");
        gym.addFitnessClass(fc);
    }

    private static void initializeGym2(Gym gym){
        gym.setHasChildCare(true);
        gym.setHasPool(false);
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new WeightMachine("hamstrings", 130));
        gym.addCardioMachine(new IndoorBike());
        gym.addCardioMachine(new Treadmill());
        gym.addCardioMachine(new Elliptical());
        FitnessClass fc = new FitnessClass("Power Lift", "Wednesday at 8AM");
        gym.addFitnessClass(fc);
        FitnessClass fc2 = new FitnessClass("Hatha Yoga", "Sunday at 11AM");
        gym.addFitnessClass(fc2);
    }

    private static void initializeGym3(Gym gym){
        gym.setHasChildCare(true);
        gym.setHasPool(true);
        gym.addWeight(new WeightMachine("trapezius", 70));
        gym.addWeight(new WeightMachine("triceps", 30));
        gym.addWeight(new Barbell(90));
        gym.addCardioMachine(new Treadmill());
        gym.addCardioMachine(new Treadmill());
        gym.addCardioMachine(new IndoorBike());
        gym.addCardioMachine(new Elliptical());
        FitnessClass fc = new FitnessClass("Cardio Blast", "Monday at 6:30AM");
        gym.addFitnessClass(fc);
    }

}
