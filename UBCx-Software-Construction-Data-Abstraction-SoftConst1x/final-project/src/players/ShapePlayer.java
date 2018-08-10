package players;

import model.Drawing;
import model.Shape;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapePlayer implements ActionListener {
    private Shape shape;
    private Drawing drawing;
    private Timer t = null;
    private int playingColumn;

    public ShapePlayer(Drawing drawing, Shape shape, Timer t){
        this.shape = shape;
        this.drawing = drawing;
        this.t = t;
        playingColumn = 0;
    }

    // MODIFIES: this
    // EFFECTS:  plays shape(s) in the current column, repaints, increments
    //           column, and stops if done
    @Override
    public void actionPerformed(ActionEvent e) {
        playColumn();
        incrementColumn();
        stopPlayingWhenDone();
    }

    // MODIFIES: this
    // EFFECTS:  moves current x-column to the next column
    private void incrementColumn() {
        playingColumn += 1;
    }

    // MODIFIES: this
    // EFFECTS:  shapes in the current playingColumn are selected and played
    //           the frame is repainted
    private void playColumn() {
        shape.setPlayLineCoord(playingColumn);
        shape.selectAndPlay();
        drawing.repaint();
    }

    // MODIFIES: this
    // EFFECTS:  stops t when the playingColumn is past the edge of the frame
    private void stopPlayingWhenDone() {
        if (playingColumn > shape.getWidth()){
            shape.unselectAndStopPlaying();
            shape.setPlayLineCoord(0);
            drawing.repaint();
            t.stop();
        }
    }
}
