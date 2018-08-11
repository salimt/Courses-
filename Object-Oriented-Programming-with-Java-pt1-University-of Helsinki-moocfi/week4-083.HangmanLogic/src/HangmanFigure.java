// do not touch this!

import javax.swing.*;
import java.awt.*;

public class HangmanFigure extends JPanel {

    private HangmanLogic logic;
    private HangmanUserInterface userInterface;

    HangmanFigure(HangmanLogic logic, HangmanUserInterface ui) {
        super();
        setBackground(Color.WHITE);
        this.logic = logic;
        this.userInterface = ui;

    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        paintHangman(grphcs);

        this.userInterface.repaint();
    }

    private void paintHangman(Graphics g) {
        int virheita = this.logic.numberOfFaults();

        // piirretaan ukko
        int baseY = 200;

        if (virheita > 0) {    // runko
            g.drawLine(90, baseY, 200, baseY);
        }
        if (virheita > 1) {    // palkki
            g.drawLine(125, baseY, 125, baseY - 100);
        }
        if (virheita > 2) {
            g.drawLine(110, baseY, 125, baseY - 15);
        }
        if (virheita > 3) {
            g.drawLine(140, baseY, 125, baseY - 15);
        }
        if (virheita > 4) {    // tuki
            g.drawLine(125, baseY - 100, 175, baseY - 100);
        }
        if (virheita > 5) {
            g.drawLine(125, baseY - 85, 140, baseY - 100);
        }
        if (virheita > 6) {    // naru
            g.drawLine(175, baseY - 100, 175, baseY - 75);
        }
        if (virheita > 7) {    // ruumis
            g.drawOval(170, baseY - 75, 10, 12);
        }
        if (virheita > 8) {
            g.drawOval(170, baseY - 65, 15, 25);
        }
        if (virheita > 9) {    // kadet
            g.drawLine(160, baseY - 65, 170, baseY - 60);
        }
        if (virheita > 10) {
            g.drawLine(183, baseY - 60, 193, baseY - 65);
        }
        if (virheita > 11) {    // jalat
            g.drawLine(165, baseY - 30, 170, baseY - 45);
        }
        if (virheita > 12) {
            g.drawLine(183, baseY - 45, 193, baseY - 30);
        }

        // näytä sana
        g.drawString("Word: " + this.logic.hiddenWord(), 20, 250);

        // näytä virheiden lkm
        g.drawString("Number of Wrong Guesses: " + virheita, 20, 270);


        // näytä viesti
        g.drawString("Message: " + annaTilanne(), 20, 290);

        // näytä arvatut kirjaimet
        drawString(g, "Guessed Letters: " + this.logic.guessedLetters(), 20, 310, 240);
    }

    public String annaTilanne() {
        String salattuSana = this.logic.hiddenWord();
        if (!salattuSana.contains("_")) {
            return "You Win!";
        }

        if (logic.numberOfFaults() > this.logic.losingFaultAmount()) {
            return "You Lost!";
        }

        return "Enter the Letter!";
    }

    // piirretään tekstiä joka vaihtaa riviä tarvittaessa
    public void drawString(Graphics g, String string, int x, int y, int width) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int lineHeight = fontMetrics.getHeight();

        int curX = x;
        int curY = y;

        String[] words = string.split(" ");

        for (String word : words) {
            // Find out thw width of the word.
            int wordWidth = fontMetrics.stringWidth(word + " ");

            // If text exceeds the width, then move to next line.
            if (curX + wordWidth >= x + width) {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            // Move over to the right for next word.
            curX += wordWidth;
        }
    }
}
