/**
 * author: salimt
 */

package ui;
import model.*;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager("Mr.Krabs", 30);
        BurgerByte restaurant = new BurgerByte("Bikini Bottom", manager);
        manager.setManagingBranch(restaurant);
        FryCook frycook = new FryCook("SpongeBob SquarePants", 20, restaurant);
        Cashier cashier = new Cashier("SquidWard Tentacles", 21, restaurant);

        System.out.println(restaurant.getStaff());
        System.out.println("Cashiers: " + manager.getCashiers());
        System.out.println("Team: " + manager.getTeam());
    }

}
