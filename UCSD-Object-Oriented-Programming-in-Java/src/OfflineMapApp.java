import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

/**
 * This example uses a local MBTiles file. Thus, it does not need an Internet connection to load tiles.
 */
public class OfflineMapApp extends PApplet {

	public static String mbTilesString = "blankLight-1-3.mbtiles";

	UnfoldingMap map;

	public void setup() {
		size(800, 600, OPENGL);

		map = new UnfoldingMap(this, new MBTilesMapProvider(mbTilesString));
		MapUtils.createDefaultEventDispatcher(this, map);
		map.setZoomRange(1, 3);
	}

	public void draw() {
		background(0);
		map.draw();
	}

}