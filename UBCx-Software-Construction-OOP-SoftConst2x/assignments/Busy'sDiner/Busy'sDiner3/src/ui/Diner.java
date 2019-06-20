/**
 * @author salimt
 */

package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Diner {

    public static void main(String[] args) {
        Dish dish = generateTurkeyClubSandwich();
        Server server = new Server(dish);
        Chef chef = new Chef();
        Server host = new Server(dish);

        //Table 1
        System.out.println("Table " + 1 + ":\n");

        server.greet();
        server.describeDish();
        Order o = server.takeOrder();

        System.out.println();
        chef.makeDish(o);

        doOrderRoutine(server, o);
        System.out.println();


        //Table 2
        System.out.println("Table " + 2 + ":\n");

        host.greet();
        host.describeDish();
        System.out.println();
        host.takeOrder();

        System.out.println("Oops! Let's let the server handle that...");

        Order o2 = server.takeOrder();
        System.out.println();
        chef.makeDish(o2);

        doOrderRoutine(host, o2);
        System.out.println("Whoops!");
        server.takePayment(o2);


        System.out.println();
        chef.doDishes();
    }

    private static void doOrderRoutine(Server s, Order o) {
        System.out.println();
        if (o.isReadyToBeServed())
            s.deliverFood(o);
        if(o.isReadyToBePaid())
            s.takePayment(o);
    }

    private static Dish generateTurkeyClubSandwich() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("avocado");
        ingredients.add("sriracha");
        ingredients.add("cheddar cheese");
        ingredients.add("bread");
        ingredients.add("lettuce");
        ingredients.add("tomato");
        ingredients.add("turkey");
        ingredients.add("bacon");
        return new Dish("Turkey club sandwich",
                "\"Our trendy sandwich has avocado, sriracha sauce, cheese, veggies, turkey and bacon.\"",
                ingredients,
                "\t1. Pour sriracha\n\t2. Spread avocado\n\t3. Stack meat\n\t4. Place veggies");
    }

}
