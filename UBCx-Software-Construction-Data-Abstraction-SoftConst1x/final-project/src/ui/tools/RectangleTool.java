/**
 * author: salimt
 */

package ui.tools;

import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import model.Rectangle;
import ui.DrawingEditor;

public class RectangleTool extends ShapeTool {

    public RectangleTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    public String getLabel() {
        return "Rectangle";
    }

    public void makeShape(MouseEvent e) {
        shape = new Rectangle(e.getPoint(), editor.getMidiSynth());
    }
}