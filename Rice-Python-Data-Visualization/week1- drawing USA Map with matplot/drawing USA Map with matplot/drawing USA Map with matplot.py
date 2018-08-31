"""
Week 1 practice project template for Python Data Visualization
Load a county-level PNG map of the USA and draw it using matplotlib

@author salimt
"""

import matplotlib.pyplot as plt
import matplotlib.image as mpimg

# Houston location

USA_SVG_SIZE = [555, 352]
HOUSTON_POS = [302, 280]


def draw_USA_map(map_name):
    """
    Given the name of a PNG map of the USA (specified as a string),
    draw this map using matplotlib
    """
     
    # Load map image, note that using 'rb'option in open() is critical since png files are binary
    img = mpimg.imread(map_name)
    
    #  Get dimensions of USA map image
    ypixels, xpixels, channels = img.shape
#    print(width, height
    
    # Plot USA map
    imgplot = plt.imshow(img)
    
    # Plot green scatter point in center of map
    plt.scatter(xpixels/2, ypixels/2, c = 'b')

     
    # Plot red scatter point on Houston, Tx - include code that rescale coordinates for larger PNG files
    plt.scatter(x = HOUSTON_POS[0] * xpixels / USA_SVG_SIZE[0], y = HOUSTON_POS[1] * ypixels / USA_SVG_SIZE[1], c = "r")   
    plt.show()

#draw_USA_map("USA_Counties_555x352.png")
draw_USA_map("USA_Counties_1000x634.png")   

