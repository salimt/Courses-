package model;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Drawing extends JPanel {

    private static final int MUSIC_LINES_SPACE = 30;

	private List<Shape> shapes;
	private int playLineColumn;

	public Drawing() {
		super();
		shapes = new ArrayList<Shape>();
		setBackground(Color.white);
	}

	// getters
    public List<Shape> getShapes() { return this.shapes; }
    public int getPlayLineColumn() { return this.playLineColumn; }

    // setters
	public void setPlayLineColumn(int plc) { playLineColumn = plc; }

    // EFFECTS: return true if the given Shape s is contained in Drawing
    public boolean containsShape(Shape s) {
		return shapes.contains(s);
	}

    // EFFECTS: paints grid, playback line, and all figures in drawing
	//          Note to students: calls to repaint gets here via the Java graphics framework
    @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawHorizontalNotesLines(g);
		for (Shape shape : shapes) {
            shape.draw(g);
        }
	}

	// EFFECTS: draws grid with lines GRIDSPACE apart, and draws red line at its current position
    private void drawHorizontalNotesLines(Graphics g) {
        Color save = g.getColor();
        g.setColor(new Color(227,227,227));
        for (int y = MUSIC_LINES_SPACE; y < getHeight(); y += MUSIC_LINES_SPACE) {
            g.drawLine(0, y, getWidth(), y);
        }
        if (playLineColumn > 0 && playLineColumn < getWidth()) {
            g.setColor(Color.RED);
            g.drawLine(playLineColumn, 0, playLineColumn, getHeight());
        }
        g.setColor(save);
    }

    // MODIFIES: this
    // EFFECTS:  adds the given shape to the drawing
	public void addShape(Shape shape) {
		shapes.add(shape);
	}

    // MODIFIES: this
    // EFFECTS:  removes shape from the drawing
	public void removeShape(Shape shape) {
		shapes.remove(shape);
		repaint();
	}

	// EFFECTS: returns the Shape at a given Point in Drawing, if any
	public Shape getShapesAtPoint(Point point) {
		for (Shape shape : shapes) {
			if (shape.contains(point))
				return shape;
		}
		return null;
	}

	// EFFECTS: returns all Shapes at given column corresponding to an x-coordinate
	public List<Shape> getShapesAtColumn(int x) {
	    List<Shape> shapesAtColumn = new ArrayList<Shape>();
		for (Shape shape : shapes) {
			if (shape.containsX(x))
				shapesAtColumn.add(shape);
		}
		return shapesAtColumn;
	}


}