package main;

import model.exceptions.BeansAmountException;
import model.exceptions.NoCupsRemainingException;
import model.exceptions.StaleCoffeeException;
import model.exceptions.WaterException;
import model.CoffeeMaker;

public class Main {

    public static void main(String[] args){
        CoffeeMaker cm = new CoffeeMaker();
        try {
            cm.brew(2.50, 15);
        } catch (BeansAmountException e) {
            e.printStackTrace();
        } catch (WaterException e) {
            e.printStackTrace();
        }

        System.out.println("Cups Remaining: " + cm.getCupsRemaining());

        for(int i=0; i<21; i++){
            try {
                cm.pourCoffee();
            } catch (NoCupsRemainingException e) {
                e.printStackTrace();
            } catch (StaleCoffeeException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Cups Remaining: " + cm.getCupsRemaining());

    }


}