package ui;

import model.Gym;
import model.cardio.CardioMachine;
import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import model.weights.*;

public class Main {

    public static void main(String[] args) {

        Gym fitLife = new Gym();
        loadMachines(fitLife);
        loadWeights(fitLife);
        fitLife.setHasPool(true);
        fitLife.setHasChildCare(false);

        System.out.println("Welcome to FitLife!\nWe are pleased to advertise the following amenities.\n");
        System.out.println("FitLife has the following cardio machines:");

        for(CardioMachine c : fitLife.getCardioMachines()) {
            System.out.println(c.getClass().getSimpleName());
        }

        System.out.println("\nFitLife has the following weights:");

        for(Weight w: fitLife.getWeights()) {
            System.out.println(w.getClass().getSimpleName() + ", weight: " + w.getWeight());
        }

        System.out.println("\nFitLife pool: " + fitLife.hasPool());
        System.out.println("FitLife child care: " + fitLife.hasChildCare());
        System.out.println("\nEnjoy your workout!");
    }

    private static void loadMachines(Gym gym) {
        gym.addCardioMachine(new Treadmill(60, 1, 3.0));
        gym.addCardioMachine(new Elliptical(30, 10));
        gym.addCardioMachine(new IndoorBike(15, 3));
    }

    private static void loadWeights(Gym gym) {
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


}