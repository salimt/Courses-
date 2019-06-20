package model.random;

import model.random.RandomNumber;

import java.util.Random;

import static model.Game.SIDE_LENGTH;

public class BingoNumber extends RandomNumber {

    private char letter;

    public BingoNumber(){
        super();
        generateLetter();
    }

    //EFFECTS: returns letter of call
    public char getLetter(){
        return letter;
    }

    //EFFECTS: randomly generates one of B, I, N, G or O
    private void generateLetter(){
        Random r = new Random();
        switch (r.nextInt(SIDE_LENGTH) % SIDE_LENGTH) {
            case 0:
                letter = 'B';
                break;
            case 1:
                letter = 'I';
                break;
            case 2:
                letter = 'N';
                break;
            case 3:
                letter = 'G';
                break;
            default:
                letter = 'O';
                break;
        }
    }

}
