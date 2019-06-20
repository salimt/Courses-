/**
 * @author salimt
 */

package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import twitter4j.Status;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NewMarker extends MapMarkerCircle implements MapMarker {
    public static final double defaultMarkerSize = 15.0;
    public static final Color defaultColor = Color.red;

    public Color color;
    private Status status;
    private BufferedImage img;
    private Coordinate coord;

    public NewMarker(Layer layer, Coordinate coord, Status s, String imgURL, Color color) {
        super(layer, null, coord, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        this.status = s;
        this.img = util.Util.imageFromURL(imgURL);
        this.color = color;
        this.coord = coord;

        setColor(Color.BLACK);
        setBackColor(defaultColor);
    }

    @Override
    public void paint(Graphics g, Point position, int radio) {
        g.setColor(color);
        int x = (int) position.getX();
        int y = (int) position.getY();
        g.fillOval(x-4, y-4, 24, 24);
        g.drawImage(this.img, x, y, 16, 16, null);
        this.paintText(g, position);
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

}
