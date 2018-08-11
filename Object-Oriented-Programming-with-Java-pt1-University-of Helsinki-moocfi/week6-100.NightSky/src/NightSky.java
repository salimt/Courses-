/**
 * author: salimt
 */

public class NightSky {

    private double density;
    private int width;
    private int height;
    private int totalStars;


    //creates a NightSky object with the given star density.
    //Width gets the value 20 and height the value 10.
    public NightSky(double density) {
        this.density = density;
        this.width = 20;
        this.height = 10;
    }

    //creates a NightSky object with the given
    //width and height. Density gets the value 0.1.
    public NightSky(int width, int height) {
        this.width = width;
        this.height = height;
        this.density = 0.1;
    }

    public NightSky(double density, int width, int height) {
        this.density = density;
        this.width = width;
        this.height = height;
    }


    //EFFECTS: prints only 1 line with stars
    public void printLine() {
        totalStars = 0;             //resets
        for (int j = 0; j < width; j++) {
            if (Math.random() <= density) { System.out.print("*"); totalStars += 1; }
            else { System.out.print(" "); }
        }System.out.println();
    }


    //EFFECTS: prints with height and width
    public void print() {
        totalStars = 0;             //resets
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() <= density) { System.out.print("*"); totalStars += 1; }
                else{ System.out.print(" "); }
            }
            System.out.println();
        }
    }

    //return the total number of stars in the sky
    public int starsInLastPrint(){
        return totalStars;
    }

}
