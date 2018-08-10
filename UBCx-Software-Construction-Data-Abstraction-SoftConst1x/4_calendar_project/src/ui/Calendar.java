/**
 * author: salimt
 */

package ui;

import model.*;

public class Calendar {
    public static void main(String[] args) {
        Date d = new Date();
        Time t = new Time();
        System.out.println("Date: " + d);
        System.out.println("Time: " + t);

        Entry poolParty = new Meeting(d,t,"POOL PARTY @BEVERLY HILLS");
        System.out.println("\nAttendees: ");
        ((Meeting) poolParty).addAttendees("salimt");
        ((Meeting) poolParty).addAttendees("Aslan Kral");
        ((Meeting) poolParty).addAttendees("Balık Nemo");
        ((Meeting) poolParty).getAttendees();
        poolParty.getLabel();

        System.out.println("\nAttendees: ");
        ((Meeting) poolParty).removeAttendees("Aslan Kral");
        ((Meeting) poolParty).getAttendees();

        ((Meeting) poolParty).sendInvites();


        Entry movieNight = new Reminder(d, t, "Stanley Kubrick Movies");
        System.out.println("\nMovie Night: ");
        ((Reminder) movieNight).setNote("It's Stanley Kubrick Night!!!");
        ((Reminder) movieNight).getNote();

        Entry soccerDay = new Event(d, t, "Soccer(Boys only)");
        System.out.println("\nSoccer Day: ");
        ((Event) soccerDay).setReminder("Don't forget the socks");
        ((Event) soccerDay).getReminder();

    }
}
