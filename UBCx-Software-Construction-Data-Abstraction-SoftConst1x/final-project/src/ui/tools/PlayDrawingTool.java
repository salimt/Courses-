package ui.tools;


import players.DrawingPlayer;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayDrawingTool extends Tool {

	public PlayDrawingTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
	}

    // MODIFIES: this
    // EFFECTS:  creates a new "Play The Whole Drawing" button and invokes addToParent() on the
    //           parent passed to this method
	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("Play The Whole Drawing");
		button = customizeButton(button);
		addToParent(parent);
	}

    // MODIFIES: this
    // EFFECTS:  constructs an ActionListener which is then added to this object's JButton
	@Override
	protected void addListener() {
		button.addActionListener(new PlayDrawingToolClickHandler());
	}

	// EFFECTS: initiates the playing of the DrawingPlayer
    private void playDrawing() {
        final Timer t = new Timer(2, null);
        ActionListener a = new DrawingPlayer(editor.getCurrentDrawing(), t);
        t.addActionListener(a);
        t.setInitialDelay(0);
        t.start(); //Note to students: this line magically invokes DrawingPlayer.actionPerformed repeatedly until the timer is stopped
    }


    private class PlayDrawingToolClickHandler implements ActionListener {

		// EFFECTS: sets active tool to the PlayDrawing Tool
		//          called by the framework when the tool is clicked
		@Override
		public void actionPerformed(ActionEvent e) {
            playDrawing();
		}
	}
}
