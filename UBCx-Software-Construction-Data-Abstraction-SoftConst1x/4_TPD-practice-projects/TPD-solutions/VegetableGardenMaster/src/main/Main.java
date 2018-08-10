package main;

import model.VegType;
import model.Vegetable;
import model.Veggies.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        List<Vegetable> garden = new ArrayList<>();
        garden.add(new Carrot());
        garden.add(new Radish());
        garden.add(new Tomato());
        garden.add(new GreenBean());
        garden.add(new Lettuce());

        for(Vegetable v : garden){
            System.out.println(v.getName() + ", " + v.getInstructions());
        }

        for(Vegetable v : garden){
            if(v.getType().equals(VegType.ROOT)){
                v.harvest();
            }
        }

        for(Vegetable v : garden){
            System.out.println(v.getName()+": ");
            v.feed();
            v.water();
        }
    }


}