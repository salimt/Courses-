/**
 * @author: salimt
 */

package ui;

import model.Monster;
import model.Room;
import model.Treasure;

public class MonsterMaze {

    public static void main(String[] args) throws InterruptedException {
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        Room r3 = new Room(3);
        Room r4 = new Room(4);
        Room r5 = new Room(5);
        Room r6 = new Room(6);

        Monster m1 = new Monster();
        Monster m2 = new Monster();
        Monster m3 = new Monster();

        Treasure t1 = new Treasure(100);

        m3.setTreasure(t1);


        r1.addChoice(r2);
        r1.addChoice(r4);
        r1.addChoice(m1);

        r2.addChoice(r3);
        r2.addChoice(r6);

        r3.addChoice(m3);

        r4.addChoice(r5);

        r5.addChoice(m2);

        r6.addChoice(t1);

        Game g = new Game(r1);
    }



}
