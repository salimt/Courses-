/**
 * Convert any number of images to a gray scale version by setting all color components of each pixel to the same value.
 * 
 * @author -salimt
 */

import edu.duke.DirectoryResource;
import edu.duke.ImageResource;
import edu.duke.Pixel;

import java.io.File;

public class GrayScaleConverter {

	public static ImageResource makeGray(ImageResource inImage) {
		//creates a blank image of the same size
		ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
		for (Pixel pixel: outImage.pixels()) {
			//look at the corresponding pixel in inImage
			Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
			//compute R+G+B divide that sum by 3 (call it average)
			int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen())/3;
			//set the pixel colors to average
			pixel.setRed(average);
			pixel.setGreen(average);
			pixel.setBlue(average);
		}
		//outImage is your answer
		return outImage;
	}

	//selects many pictures converts them gray scale and saves with name "gray-FILENAME"
	public static void selectAndConvert () {
		DirectoryResource dr = new DirectoryResource();
		for (File f : dr.selectedFiles()) {
			ImageResource inImage = new ImageResource(f);
            ImageSaver.doSave(makeGray(inImage), new ImageResource(f).getFileName(), "gray-");

            // SECOND WAY\\
//            ImageResource original = new ImageResource(f);
//            ImageResource grayscale = makeGray(original);
//            grayscale.setFileName("gray-" + original.getFileName());
//            grayscale.draw();
//            grayscale.save();
		}
	}

	public static void testGray() {
		ImageResource ir = new ImageResource();
		ImageResource gray = makeGray(ir);
		gray.draw();
	}
}
