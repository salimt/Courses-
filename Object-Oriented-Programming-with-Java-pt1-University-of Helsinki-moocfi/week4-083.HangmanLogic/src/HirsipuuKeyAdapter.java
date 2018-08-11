// do not touch this!

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HirsipuuKeyAdapter extends KeyAdapter {

    private HangmanLogic logic;

    public HirsipuuKeyAdapter(HangmanLogic logiikka) {
        this.logic = logiikka;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (this.logic.numberOfFaults() > this.logic.losingFaultAmount()) {
            return;
        }

        String salattuSana = this.logic.hiddenWord();
        if (!salattuSana.contains("_")) {
            return;
        }

        super.keyPressed(ke);
        String key = "" + ke.getKeyChar();
        key = key.toUpperCase();
        this.logic.guessLetter(key);
    }
}
