package players;

import model.Drawing;
import model.Shape;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DrawingPlayer implements ActionListener {

    private Drawing drawing;
    private Timer timer;
    private int playingColumn;

    private List<Shape> lastColumnPlayed;
    private List<Shape> shapesInColumn;

    // EFFECTS: constructs a DrawingPlayer
    public DrawingPlayer(Drawing drawing, Timer timer){
        this.drawing = drawing;
        this.timer = timer;
        playingColumn = 0;
        lastColumnPlayed = new ArrayList<Shape>();
        shapesInColumn = new ArrayList<Shape>();
    }

    // MODIFIES: this
    // EFFECTS:  plays shapes in current column, repaints, increments column,  stops if done
    //           this class is the listener for the timer object, and this method is what the timer calls
    //           each time through its loop.
    @Override
    public void actionPerformed(ActionEvent e) {
        selectAndPlayShapes();
        drawRedLine();
        incrementColumn();
        stopPlayingWhenDone();
    }

    // MODIFIES: this
    // EFFECTS:  moves current x-column to next column; updates figures
    private void incrementColumn() {
        playingColumn += 1;
        lastColumnPlayed = shapesInColumn;
    }

    // MODIFIES: this
    // EFFECTS:  moves playback line to playingColumn to trigger sound and repaint
    private void drawRedLine() {
        drawing.setPlayLineColumn(playingColumn);
        drawing.repaint(); // the Java Graphics framework will call Drawing.paintComponent()
    }

    // MODIFIES: this
    // EFFECTS:  calls Timer.stop() when playingColumn is past the edge of the frame
    private void stopPlayingWhenDone() {
        if (playingColumn > DrawingEditor.WIDTH){
            timer.stop();
        }
    }

    // MODIFIES: this
    // EFFECTS:  selects and plays shape(s) in the playingColumn
    private void selectAndPlayShapes() {
        shapesInColumn = drawing.getShapesAtColumn(playingColumn);

        for (Shape shape : lastColumnPlayed) {
            if (!shapesInColumn.contains(shape)) {
                shape.unselectAndStopPlaying();
            }
        }

        for (Shape shape : shapesInColumn) {
            if (!lastColumnPlayed.contains(shape)) {
                shape.selectAndPlay();
            }
        }
    }
}
