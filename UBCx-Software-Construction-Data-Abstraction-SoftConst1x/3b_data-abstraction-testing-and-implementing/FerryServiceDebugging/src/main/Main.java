package main;

import model.Ferry;
import model.Passenger;

import java.util.Date;
import java.util.Scanner;

public class Main {

    private static final int firstClassPrice = 30;
    private static final int economyPrice = 15;
    private static Scanner input;
    private static Passenger passenger;

    public static void main(String[] args) {

        input = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name = input.nextLine();
        passenger = new Passenger(name);

        System.out.println("Hello, " + passenger.getName() + "! " +
                "Would you like to purchase a ticket for a ferry sailing? Enter 'yes' or 'no': ");
        wouldLikeTicket();
    }

    private static void wouldLikeTicket() {
        String wouldLikeTicket = input.nextLine();
        if(wouldLikeTicket.equalsIgnoreCase("no")) {
            System.out.println("Thank you and have a nice day.");
        }
        else {

            initiateBuyTicket();
        }
    }

    private static void initiateBuyTicket() {

        initiateTopUp();

        System.out.println("Please enter the name of your destination:");
        String destination = input.next();
        System.out.println("What type of ticket would you like to purchase on the "
                + destination + " ferry? Please enter 'first class' or 'economy':");

        int ticketPrice;
        input.nextLine();
        String ticketType = input.nextLine();

        if(ticketType.equalsIgnoreCase("first class")) {
            ticketPrice = firstClassPrice;
        }

        else {
            ticketPrice = economyPrice;
        }

        Date date = new Date();
        Ferry ferry = new Ferry(destination, date, ticketPrice);
        boolean enoughBalance = passenger.getFerryCard().buyTicket(ferry);
        if(enoughBalance) {
            System.out.println("You have successfully purchased a ticket in " + ticketType
                    + " for the next available sailing to " + destination + ". Thank you for sailing with us!");
        }
        else if(!enoughBalance) {
            System.out.println("You do not have enough balance on your card for this purchase. Please top up your card with at least "
                    + Integer.toString(ticketPrice - passenger.getFerryCard().getBalance())  + " dollars.");
            initiateBuyTicket();
        }
    }

    private static void initiateTopUp() {

        System.out.println("Your ferry card balance is currently: "
                + Integer.toString(passenger.getFerryCard().getBalance()) + ". Please enter the amount you want to top up your card: ");
        int topUpAmount = input.nextInt();
        passenger.getFerryCard().topUp(topUpAmount);
        System.out.println("Thank you for topping up your card. Your new balance is "
                + Integer.toString(passenger.getFerryCard().getBalance()) + " dollars.");
    }


}