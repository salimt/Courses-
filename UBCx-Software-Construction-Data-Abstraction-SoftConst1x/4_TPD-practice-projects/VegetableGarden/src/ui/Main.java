/**
 * author: salimt
 */

package ui;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        List<Vegetable> garden = new ArrayList<>();
        garden.add(new Carrot());
        garden.add(new Tomato());
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

        for(Vegetable v : garden){
            if(v.getName().equals("Carrot")){
                System.out.println("Old Name: " + v.getName());
                v.setName("Mas");
                System.out.println("New Name: " + v.getName());
            }
        }

    }


}
