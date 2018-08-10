package ui;

import model.*;

public class FlyerDemo {

    public static void main(String[] args) {
        Hawk hawk = new Hawk();
        Bird hummingbird = new Hummingbird();
        Airplane planie = new Airplane();
        Cafe cafePlane = new Airplane();
        Flyer flyerPlane = new Airplane();
        PrivatePlane pp = new PrivatePlane();


        hawk.fly();
        hummingbird.fly();

        FlyerDemo fd = new FlyerDemo();
        fd.lunchService(cafePlane);
        fd.firstPartOfFlight(planie);
    }

    public void luxuryTakeoff(PrivatePlane p){
        p.takeOff();
        p.bringWarmTowels();
    }

    public void lunchService(Cafe c){
        c.serveDrinks();
        c.serveSnacks();
    }

    public void firstPartOfFlight(Airplane a){
        a.takeOff();
        a.serveDrinks();
    }


}