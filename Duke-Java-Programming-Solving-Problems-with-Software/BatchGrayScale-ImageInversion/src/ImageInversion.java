/**
 * @author: salimt
 */

import edu.duke.*;
import java.io.File;

public class ImageInversion {

    public static ImageResource makeInversion(ImageResource inImage){
        //creates a blank image of the same size
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            //modifies the colors to be the exact opposite within the 0 to 255 range
            pixel.setRed(255-inPixel.getRed());
            pixel.setGreen(255-inPixel.getGreen());
            pixel.setBlue(255-inPixel.getBlue());
        }
        return outImage;
    }

    //selects many pictures converts them negative and saves with name "inverted-FILENAME"
    public static void selectAndConvert() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageSaver.doSave(makeInversion(inImage), new ImageResource(f).getFileName(), "inverted-");
        }
    }
}
