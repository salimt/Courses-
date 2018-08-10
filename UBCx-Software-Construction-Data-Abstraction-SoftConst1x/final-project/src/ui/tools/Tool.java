package ui.tools;


import ui.DrawingEditor;

import javax.swing.*;
import java.awt.event.MouseEvent;


public abstract class Tool {

	protected JButton button;
	protected DrawingEditor editor;
	private boolean active;


	public Tool(DrawingEditor editor, JComponent parent) {
		this.editor = editor;
		createButton(parent);
		addToParent(parent);
		active = false;
		addListener();
	}

	// MODIFIES: this
	// EFFECTS:  customizes the button used for this tool
	protected JButton customizeButton(JButton button) {
		button.setBorderPainted(true);
		button.setFocusPainted(true);
		button.setContentAreaFilled(true);
		return button;
	}

	// getters
	public boolean isActive() { return active; }

	// EFFECTS: sets this Tool's active field to true
	public void activate() {
		active = true;
	}

	// EFFECTS: sets this Tool's active field to false
	public void deactivate() {
		active = false;
	}

    // EFFECTS: creates button to activate tool
	protected abstract void createButton(JComponent parent);

	// EFFECTS: adds a listener for this tool
	protected abstract void addListener();

	// MODIFIES: parent
	// EFFECTS:  adds the given button to the parent component
	public void addToParent(JComponent parent) {
		parent.add(button);
	}

	// EFFECTS: default behaviour does nothing
	public void mousePressedInDrawingArea(MouseEvent e) {}

	// EFFECTS: default behaviour does nothing
	public void mouseReleasedInDrawingArea(MouseEvent e) {}

	// EFFECTS: default behaviour does nothing
    public void mouseClickedInDrawingArea(MouseEvent e) {}

	// EFFECTS: default behaviour does nothing
    public void mouseDraggedInDrawingArea(MouseEvent e) {}

}
