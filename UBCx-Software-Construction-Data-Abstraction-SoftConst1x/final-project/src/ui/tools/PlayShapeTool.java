package ui.tools;


import model.Shape;
import players.ShapePlayer;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class PlayShapeTool extends Tool {

	public PlayShapeTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
	}

    // EFFECTS: selects the figure containing point of mouse press
	@Override
	public void mousePressedInDrawingArea(MouseEvent e)  {
        playShapeAt(e.getPoint());
	}

	// MODIFIES: this
    // EFFECTS:  creates a new "Play Shape" button and invokes addToParent() on the
    //           parent passed to this method
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Play Shape");
        button = customizeButton(button);
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  sets the activeTool in button to this when clicked
    @Override
    protected void addListener() {
        button.addActionListener(new PlayShapeToolClickHandler());
    }

    // EFFECTS: creates a ShapePlayer playing the current shape and starts it playing
    private void playShapeAt(Point p) {
        Shape shape = editor.getShapeInDrawing(p);
        if (shape != null){
            final Timer t = new Timer(2, null);
            ActionListener a = new ShapePlayer(editor.getCurrentDrawing(), shape, t);
            t.addActionListener(a);
            t.setInitialDelay(0);
            t.start(); //Note to students: this line invokes DrawingPlayer.actionPerformed repeatedly until the timer is stopped
        }
    }

	private class PlayShapeToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the PlayShape tool
        //          called by the framework when the tool is clicked
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setActiveTool(PlayShapeTool.this);
		}
	}
}
