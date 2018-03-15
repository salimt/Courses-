# -*- coding: utf-8 -*-
"""
@author: salimt
"""

import pylab as plt
import numpy as np

def plot():
    """Returns Linear vs. Quadratic vs. Cubic vs. Exponential comparison plot.
    """    
    x = np.linspace(0, 25)
#    print(x)
    plt.plot(x, x, label='linear')
    plt.plot(x, x**2, label='quadratic')
    plt.plot(x, x**3, label='cubic')
    plt.plot(x, 1.5**x, label='exponential')
    
    plt.xlabel('x axis')
    plt.ylabel('y axis')
    
    plt.title("Linear vs. Quadratic vs. Cubic vs. Exponential")
    
    plt.legend()
    
    plt.show()

print(plot())