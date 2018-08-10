/**
 * author: salimt
 */

package ui;
import model.*;

public class Main {
    public static void main(String[] args) {
        Zookeeper zw = new Zookeeper("Jake", 27);
        Animal A = new Animal("Kin", "Angola", 5, zw, 15);
        System.out.println(A.getAge());
        System.out.println(A.getCaretaker().getName());
        zw.setFavourite(A);
        System.out.println(A.getCaretaker().getFavourite().getName());
    }
}
