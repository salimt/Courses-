package ui;

import model.MyProfile;

public class Main {

    public static void main(String[] args) {
        MyProfile mainProfile = new MyProfile("Johnny Appleseed", 20, "Coquitlam", "SPL");
        MyProfile friend1 = new MyProfile("Paul Carter", 30, "London", "Network Systems");
        MyProfile friend2 = new MyProfile("Alan Turing", 24, "Coquitlam", "A Thinking Ape");
        MyProfile friend3 = new MyProfile("Ada Lovelace", 40, "Coquitlam", "AppNeta");
        MyProfile friend4 = new MyProfile("Alfonzo Church", 50, "New York City", "NYU Research");
        MyProfile friend5 = new MyProfile("Bob Ross", 44, "Chicago", "CBS");

        mainProfile.addFriend(friend1);
        mainProfile.addFriend(friend2);
        mainProfile.addFriend(friend3);
        mainProfile.addFriend(friend4);
        mainProfile.addFriend(friend5);

        mainProfile.printFriendNames();
        mainProfile.printFriendsNearMe();
    }


}
