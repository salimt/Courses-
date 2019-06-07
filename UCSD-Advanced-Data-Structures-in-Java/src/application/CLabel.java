package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Label which has an object property associated with it
 *
 * @author UCSD MOOC development team
 *
 * @param <T> Type of object associated with label
 */

public class CLabel<T> extends Label {
	private ObjectProperty<T> item = new SimpleObjectProperty<T>(this, "item");
    private static final Paint RED = Color.web("#9E092F");
    private static final Paint GREEN = Color.web("#099E78");

    // CONSTRUCTORS
	public CLabel() {
		super();
	}

	public CLabel(String text, T item) {
		super(text);

    if(item != null)
        setItem(item);
    else
    	setItem(null);
	}

	public CLabel(String text, Node graphic, T item) {
		super(text, graphic);
    if(item != null)
        setItem(item);
    else
    	setItem(null);

	}


	/**
	 * Used to update item when new item is set.
	 *
	 * @param item
	 * @param empty
	 */
	protected void updateView(T item, boolean empty) {
		if(item != null) {
			setText(item.toString());
			setTextFill(GREEN);
		}
		else {
			// TODO -- could change this to be more generic
			setText("Choose Point");
			setTextFill(RED);
		}
	}

    public final ObjectProperty<T> itemProperty(){ return item; }


	public T getItem(){
		return item.get();
	}

	public void setItem(T newItem) {
		item.set(newItem);
    updateView(item.get(), true);
	}
}
