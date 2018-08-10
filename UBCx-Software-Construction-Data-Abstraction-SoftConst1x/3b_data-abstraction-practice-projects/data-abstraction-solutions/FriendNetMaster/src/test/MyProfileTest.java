package test;

import model.Event;
import model.MyProfile;
import org.junit.Before;
import org.junit.Test;

import static model.EventType.*;
import static org.junit.Assert.*;

public class MyProfileTest {

    private MyProfile friend1;
    private MyProfile friend2;
    private MyProfile friend3;
    private MyProfile friend4;
    private MyProfile friend5;
    private MyProfile friend6;
    private MyProfile friend7;
    private MyProfile mainProfile;
    private Event ev1;
    private Event ev2;
    private Event ev3;
    private Event ev4;

    @Before
    public void setUp() {
        mainProfile = new MyProfile("Johnny Appleseed", 20, "Coquitlam", "SPL");

        friend1 = new MyProfile("Paul Carter", 30, "London", "Network Systems");
        friend2 = new MyProfile("Alan Turing", 24, "Coquitlam", "A Thinking Ape");
        friend3 = new MyProfile("Ada Lovelace", 40, "Coquitlam", "AppNeta");
        friend4 = new MyProfile("Alfonzo Church", 50, "New York City", "NYU Research");
        friend5 = new MyProfile("Bob Ross", 44, "Chicago", "CBS");
        friend6 = new MyProfile("John Carter", 33, "Bellevue", "Microsoft");
        friend7 = new MyProfile("Eric Hamber", 23, "Vancouver", "UBC");

        mainProfile.addFriend(friend1);
        mainProfile.addFriend(friend2);
        mainProfile.addFriend(friend3);
        mainProfile.addFriend(friend4);
        mainProfile.addFriend(friend5);
        friend5.addFriend(friend6);
        friend6.addFriend(friend7);

        ev1 = new Event("John's Wedding", "May 20", "Coquitlam", WEDDING);
        ev2 = new Event("Celina's Party", "May 11", "2298 West 4th Avenue", PARTY);
        ev3 = new Event("Anthony's Birthday", "August 29", "Coquitlam", BIRTHDAY);
        ev4 = new Event("Bill's Lecture", "May 4", "2366 Main Mall", SCHOOL);

        mainProfile.addEvent(ev1);
        mainProfile.addEvent(ev2);
        mainProfile.addEvent(ev3);
        mainProfile.addEvent(ev4);
    }

    @Test
    public void testCanFindPerson() {
        assertEquals(mainProfile.canFindPerson("John Carter"), true);
        assertFalse(mainProfile.canFindPerson("Nick Jonas"));
        assertEquals(mainProfile.canFindPerson("Eric Hamber"), true);
        assertEquals(mainProfile.canFindPerson("Alan Turing"), true);
    }

    @Test
    public void testunFriend() {
        assertTrue(mainProfile.getFriendsList().contains(friend1));
        assertTrue(mainProfile.unFriend("Paul Carter"));
        assertFalse(mainProfile.getFriendsList().contains(friend1));
        assertFalse(mainProfile.unFriend("Linda Bob"));
    }

    @Test
    public void testremoveEvent() {
        assertTrue(mainProfile.getEventList().contains(ev4));
        assertTrue(mainProfile.removeEvent("Bill's Lecture"));
        assertFalse(mainProfile.getEventList().contains(ev4));
        assertFalse(mainProfile.removeEvent("Bill's Lecture"));
        assertFalse(mainProfile.removeEvent("Anthony's Dance Party"));
    }

    @Test
    public void testeventNumNearMe() {
        assertEquals(mainProfile.eventNumNearMe(), 2);
        mainProfile.removeEvent("John's Wedding");
        assertEquals(mainProfile.eventNumNearMe(), 1);
        mainProfile.removeEvent("Anthony's Birthday");
        assertEquals(mainProfile.eventNumNearMe(), 0);
    }

    @Test
    public void testupcomingEventNum() {
        assertEquals(mainProfile.upcomingEventNum(), 4);
        mainProfile.removeEvent("Bill's Lecture");
        assertEquals(mainProfile.upcomingEventNum(), 3);
    }


}