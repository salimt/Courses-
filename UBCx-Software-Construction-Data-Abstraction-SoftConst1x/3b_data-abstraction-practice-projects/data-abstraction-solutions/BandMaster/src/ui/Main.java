package ui;

import model.Band;

public class Main {

    public static void main(String[] args) {
        Band band;
        band = new Band("Foo Fighters");
        band.addMember("Dave Grohl");
        band.addMember("Taylor Hawkins");
        band.addMember("Curt Cobain");
        System.out.println("Foo Fighters are: " + band.getMembers());
        band.playGig(1000);
        band.playGig(20000);
        band.payMembers(2000);
        System.out.println("Foo Fighters have made: $" + band.getTotalMoney());
        System.out.println("Foo Fighters have: $" + band.getCurrentMoney() + " now");
        System.out.println("Foo Fighters made: $" + band.averagePerShow() + " per show");
    }


}
