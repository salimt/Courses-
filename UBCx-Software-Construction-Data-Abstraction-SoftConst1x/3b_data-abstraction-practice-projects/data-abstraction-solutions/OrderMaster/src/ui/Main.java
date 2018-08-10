package ui;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Order o1 = new Order(63, 'A', "Kate");
        Order o2 = new Order(70, 'D', "James");
        o2.setInstructions("Extra hot");
        Order o3 = new Order(12, 'E', "Amy");
        o3.setInstructions("To go");

        List<Order> orders = new ArrayList<>();
        orders.add(o1);
        orders.add(o2);
        orders.add(o3);

        o2.completed();

        for(Order o : orders){
            if(o.isCompleted()){
                System.out.println(o.customerReceipt());
            } else {
                System.out.println(o.cookingInstructions());
            }
        }
    }


}
