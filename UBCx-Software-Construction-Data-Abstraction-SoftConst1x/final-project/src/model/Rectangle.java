/**
 * author: salimt
 */

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import sound.MidiSynth;

public class Rectangle extends Shape {

    public Rectangle(Point topLeft, MidiSynth midiSynth) {
        super(topLeft, midiSynth, new Color(19, 15, 85));
        instrument = 98;
    }

    // EFFECTS: return true if the given Point (x,y) is contained within the bounds of this Shape
    @Override
    public boolean contains(Point point) {
        int point_x = point.x;
        int point_y = point.y;

        return containsX(point_x) && containsY(point_y);
    }

    @Override
    public void drawGraphics(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    @Override
    public void fillGraphics(Graphics g) {
        g.fillRect(x, y, width, height);
    }

}



