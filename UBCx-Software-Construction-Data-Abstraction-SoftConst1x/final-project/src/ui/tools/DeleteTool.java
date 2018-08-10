package ui.tools;

import model.Shape;
import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class DeleteTool extends Tool {

	private Shape shapeToDelete;

	public DeleteTool(DrawingEditor editor, JComponent parent) {
		super(editor, parent);
		shapeToDelete = null;
	}

    // MODIFIES: this
    // EFFECTS:  constructs a delete button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
	@Override
	protected void createButton(JComponent parent) {
		button = new JButton("Delete");
		addToParent(parent);
	}

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
	@Override
	protected void addListener() {
		button.addActionListener(new DeleteToolClickHandler());
	}

    // MODIFIES: this
    // EFFECTS:  Sets the shape at the current mouse position as the shape to delete,
	//           selects the shape and plays it
	@Override
	public void mousePressedInDrawingArea(MouseEvent e) {
		shapeToDelete = editor.getShapeInDrawing(e.getPoint());
		if (shapeToDelete != null) {
			shapeToDelete.selectAndPlay();
		}
	}

    // MODIFIES: this
    // EFFECTS:  unselects the shape being deleted, and removes it from the drawing
	@Override
	public void mouseReleasedInDrawingArea(MouseEvent e) {
		if (shapeToDelete != null) {
		    shapeToDelete.unselectAndStopPlaying();
			editor.removeFromDrawing(shapeToDelete);
			shapeToDelete = null;
		}
	}

    // MODIFIES: this
	// EFFECTS:  Sets the shape at the current mouse position as the shape to delete,
	//           selects the shape and plays it
	@Override
	public void mouseDraggedInDrawingArea(MouseEvent e) {
        shapeToDelete = editor.getShapeInDrawing(e.getPoint());
        if (shapeToDelete != null) {
            shapeToDelete.selectAndPlay();
        }
	}

	private class DeleteToolClickHandler implements ActionListener {

		// EFFECTS: sets active tool to the delete tool
		//          called by the framework when the tool is clicked
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.setActiveTool(DeleteTool.this);
		}
	}
}
