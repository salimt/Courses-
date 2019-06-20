/**
 * @author: salimt
 */
package ui;

import model.Game;
import model.random.NumberSquare;
import model.PlayerCard;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static model.Game.SIDE_LENGTH;

public class BingoUI {

    private Game game;
    private PlayerCard playerCard;
    private int[] stampCounts;

    public static void main (String[] args) throws InterruptedException {
        new BingoUI();
    }


    public BingoUI() throws InterruptedException {

        game = new Game();
        playerCard = new PlayerCard();

        game.addCard(playerCard);
        game.addCard(new PlayerCard());
        game.addCard(new PlayerCard());

        stampCounts = new int[game.getCards().size()];
        printGreeting();
        playGame();
    }

    //MODIFIES: this
    //EFFECTS: runs game actions loop with time delay until a player has won
    private void playGame() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        while(!game.isGameOver()) {
            game.callNext();
            System.out.println("\nNumber called: " + formatBingoCall());
            TimeUnit.MILLISECONDS.sleep(1000);
            checkStampsAndUpdate();
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("\nYour card is: ");
            TimeUnit.MILLISECONDS.sleep(100);
            printPlayerCard(playerCard);
            TimeUnit.MILLISECONDS.sleep(500);
        }
        if (playerCard.hasBingo()){
            System.out.println("\nCongratulations! You win!");
        } else {
            System.out.println("\nSorry, another player won this time.");
            System.out.println("\nWinning card:");
            printPlayerCard(findWinningCard());
        }
    }

    //EFFECTS: returns the string of the bingo call in format Letter-Number
    private String formatBingoCall() {
        return game.getCurrentCall().getLetter() + "-" + game.getCurrentCall().getNumber();
    }

    //MODIFIES: this
    //EFFECTS: updates stamp counts of each player and prints message if a card has been stamped
    private void checkStampsAndUpdate() throws InterruptedException {

        int size = game.getCards().size();
        for (int i=0; i < size; i++) {
            int playerStampCount = game.getCards().get(i).getNumberOfSquaresStamped();

            if (stampCounts[i] < playerStampCount) {
                if (game.getCards().get(i).equals(playerCard)) {
                    System.out.println("\nSweet! You have stamped " + formatBingoCall());
                    TimeUnit.MILLISECONDS.sleep(500);
                }
                else {
                    System.out.println("\nAnother player has stamped " + formatBingoCall());
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }
            stampCounts[i] = playerStampCount;
        }
    }

    //EFFECTS: prints welcome greeting
    private void printGreeting() throws InterruptedException {
        System.out.println("Welcome to BINGO!");
        System.out.println("Here is your bingo card: ");
        printPlayerCard(playerCard);
    }

    //EFFECTS: prints player card in rows and columns where X represents a stamped square
    private void printPlayerCard(PlayerCard card) throws InterruptedException {
        List<NumberSquare> squares = card.getSquares();
        NumberSquare sqr;
        StringBuilder cardString = new StringBuilder();
        cardString.append("\n B   I   N   G   O");
        for (int i=0; i < squares.size(); i++) {
            sqr = squares.get(i);
            if (i % SIDE_LENGTH == 0) cardString.append("\n");
            cardString.append(sqr.isStamped() ? " X" : formatNumber(sqr.getNumber()));
            cardString.append("  ");
        }
        System.out.println(cardString.toString());
    }

    //EFFECTS: returns the i as a string with a leading space if i < 10
    private String formatNumber(int i){
        if (i < 10){
            return " " + i;
        } else {
            return "" + i;
        }
    }

    //EFFECTS: returns the winning card of this game
    private PlayerCard findWinningCard(){
        PlayerCard card = new PlayerCard();

        for(PlayerCard c : game.getCards()){
            if (c.hasBingo())
                card = c;
        }

        return card;
    }
}
