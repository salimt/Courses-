/*
 *@author salimt
 */

package ui;

import model.Server;
import model.Order;
import model.Chef;

public class Diner {

    public static void main(String[] args) {
        Server server = new Server();
        Chef chef = new Chef();

        for (int i=0; i < 2 ; i++) {
            System.out.println("Table " + (i + 1) + ":\n");

            server.greet();
            server.describeDish();
            Order o = server.takeOrder();
            chef.makeDish(o);

            System.out.println();

            doOrderRoutine(server, o);
            System.out.println();
        }

        System.out.println();
        chef.doDishes();
    }

    private static void doOrderRoutine(Server e, Order o) {
        System.out.println();
        if (o.isReadyToBeServed())
            e.deliverFood(o);
        if(o.isReadyToBePaid())
            e.takePayment(o);
    }

}
