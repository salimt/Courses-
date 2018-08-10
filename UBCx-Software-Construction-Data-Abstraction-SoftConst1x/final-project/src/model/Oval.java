/**
 * author: salimt
 */

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import sound.MidiSynth;

public class Oval extends Shape {

    public Oval(Point topLeft, MidiSynth midiSynth) {
        super(topLeft, midiSynth, new Color(56, 0, 5));
        instrument = 94;
    }


    // EFFECTS: return true if this Oval contains the given point p, else return false
    @Override
    public boolean contains(Point p) {
        final double TOL = 1.0e-6;
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;
        double diff = 0.0;

        if (halfWidth > 0.0) {
            diff = diff + sqrDiff(x + halfWidth, p.x) / (halfWidth * halfWidth);
        } else {
            diff = diff + sqrDiff(x + halfWidth, p.x);
        }
        if (halfHeight > 0.0) {
            diff = diff + sqrDiff(y + halfHeight, p.y) / (halfHeight * halfHeight);
        } else {
            diff = diff + sqrDiff(y + halfHeight, p.y);
        }
        return  diff <= 1.0 + TOL;
    }

    // Compute square of difference
    // EFFECTS: returns the square of the difference of num1 and num2
    private double sqrDiff(double num1, double num2) {
        return (num1 - num2) * (num1 - num2);
    }


    //EFFECTS: draws the shape
    @Override
    public void drawGraphics(Graphics g) {
        g.drawOval(x, y, width, height);
    }

    //EFFECTS: fills the shape
    @Override
    public void fillGraphics(Graphics g) {
        g.fillOval(x, y, width, height);
    }

}
