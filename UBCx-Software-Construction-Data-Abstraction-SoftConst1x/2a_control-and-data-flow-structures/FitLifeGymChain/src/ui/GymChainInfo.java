package ui;

import model.GymChain;
import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import model.gym.DeluxeGym;
import model.gym.FitnessClass;
import model.gym.Gym;
import model.members.Member;
import model.weights.Barbell;
import model.weights.Dumbbell;
import model.weights.Plate;
import model.weights.WeightMachine;

import static model.members.MembershipLevel.*;

public class GymChainInfo {

    public static final String SUNNY_GROVE_GYM_NAME = "Sunny Grove";
    public static final String PINE_VALLEY_GYM_NAME = "Pine Valley";
    public static final String PALM_LAKE_GYM_NAME = "Palm Lake";
    public static final String AMY_MEMBER_NAME = "Amy";
    public static final String JAMES_MEMBER_NAME = "James";
    public static final String MICHELA_MEMBER_NAME = "Michela";

    //TODO 1: assign a different value to the constant below
    public static final String OLYMPIA_GYM_NAME = "Olympia";
    //TODO 1: assign a different value to the constant below
    public static final String SALIM_MEMBER_NAME = "Salim";

    public static void main(String[] args) {
        GymChain chain = new GymChain();
        loadGyms(chain);
        loadMembers(chain);
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

        //TODO 3: construct a new Member object with the parameter YOUR_MEMBER_NAME
        Member m4 = new Member(SALIM_MEMBER_NAME);
        //TODO 3: uncomment these 2 lines:
        m4.setMembershipLevel(DELUXE);
        chain.signUpMember(m4);
    }

    private static void loadGyms(GymChain chain){
        Gym g1 = new Gym(SUNNY_GROVE_GYM_NAME);
        initializeGym1(g1);
        chain.addGym(g1);

        WeightMachine wm = new WeightMachine("quadriceps", 75);

        //TODO 2: pass in parameters to these methods:
        g1.setHasPool(true);
//        wm.setWeight(...);
        wm.setWeight(80);
//        g1.addWeight(...);
        g1.addWeight(new WeightMachine("biceps", 50));


        Gym g2 = new DeluxeGym(PINE_VALLEY_GYM_NAME);
        initializeGym2(g2);
        chain.addGym(g2);

        Gym g3 = new Gym(PALM_LAKE_GYM_NAME);
        initializeGym3(g3);
        chain.addGym(g3);

        //TODO 4: pick a variable name for the gym below and uncomment:
        Gym g0 = new Gym(OLYMPIA_GYM_NAME);
        //TODO 4: add your new gym's variable name and uncomment:
        initializeGym0(g0);
        //TODO 4: add your new gym's variable name and uncomment:
        chain.addGym(g0);
    }


    private static void initializeGym0(Gym gym){
        //TODO 4: uncomment the next 4 lines:
         gym.addWeight(new Barbell(135)); //TODO 4: make a new barbell
        gym.addCardioMachine(new IndoorBike()); //TODO 4: make a new IndoorBike
//         gym.addWeight(bar);
//         gym.addCardioMachine(bike);
//        NOTE: the Barbell constructor takes one parameter, an int
//        NOTE: the IndoorBike constructor doesn't take any parameters
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
