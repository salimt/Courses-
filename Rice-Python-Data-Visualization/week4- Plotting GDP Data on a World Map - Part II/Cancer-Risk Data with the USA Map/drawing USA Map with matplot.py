"""
Week 1 practice project template for Python Data Visualization
Load a county-level PNG map of the USA and draw it using matplotlib

@author salimt
"""

import math
import csv
import matplotlib.pyplot as plt
import matplotlib as mpl


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
#draw_USA_map("USA_Counties_1000x634.png")   

def read_csv_file(file_name):
    """
    Given a CSV file, read the data into a nested list
    Input: String corresponding to comma-separated  CSV file
    Output: Nested list consisting of the fields in the CSV file
    """
       
    with open(file_name, newline='') as csv_file:       # don't need to explicitly close the file now
        csv_table = []
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            csv_table.append(row)
    return csv_table


def compute_county_cirle(county_population):
    """
    Given county population as integer,
    Compute area of circle proportional to population for use as option to scatter() in matplotlib
    """
    return (math.pi / (200.0 ** 2)) * county_population
    
    

MAX_LOG_RISK = math.log(1.50E-04, 10)    # maximum cancer risk in table
MIN_LOG_RISK = math.log(8.60E-06, 10)    # minimum cancer risk in table

def create_riskmap(colormap):
    """
    Initialize the colormap "jet" from matplotlib,
    Return function that takes risk and returns RGB color for use with scatter() in matplotlib
    """
    risk_norm = mpl.colors.Normalize(vmin = MIN_LOG_RISK, vmax = MAX_LOG_RISK)
    color_mapper = mpl.cm.ScalarMappable(norm = risk_norm, cmap = colormap)
    
    return lambda risk : color_mapper.to_rgba(math.log(risk, 10))


def draw_cancer_risk_map(joined_csv_file_name,map_name):
    """
    Given names of joined CSV file and PNG map of USA, draw cancer-risk data 
    for counties overlaid on USA map
    """    
    
    img = plt.imread(map_name)
    ypixels, xpixels, channels = img.shape
    risk_data = read_csv_file(joined_csv_file_name)
    
    # Optional code to resize plot as fixed size figure -
    DPI = 80.0                  # adjust this constant to resize your plot
    xinch = xpixels / DPI
    yinch = ypixels / DPI
    plt.figure(figsize=(xinch,yinch))
    
    # Plot USA map
    imgplot = plt.imshow(img)
    
    # Compute function that maps cancer risk to RGB colors
    risk_map = create_riskmap(mpl.cm.jet)
    
    for data in risk_data:
        plt.scatter(x = float(data[4]) * xpixels / USA_SVG_SIZE[0], 
                    y = float(data[5]) * ypixels / USA_SVG_SIZE[1], 
                    c = risk_map(float(data[3])), 
                    s = compute_county_cirle(int(data[2])))
        
    plt.savefig('USA-Risk-Map.png')
    plt.show()
    
    
draw_cancer_risk_map("cancer_risk_joined.csv", "USA_Counties_1000x634.png")



