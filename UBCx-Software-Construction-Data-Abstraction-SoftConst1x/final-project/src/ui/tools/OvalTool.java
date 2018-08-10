/**
 * author: salimt
 */

package ui.tools;

import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import model.Oval;
import ui.DrawingEditor;

public class OvalTool extends ShapeTool {

    public OvalTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    public String getLabel() {
        return "Oval";
    }

    @Override
    public void makeShape(MouseEvent e) {
        shape = new Oval(e.getPoint(), editor.getMidiSynth());
    }
}