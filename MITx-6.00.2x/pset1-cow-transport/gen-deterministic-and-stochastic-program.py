# -*- coding: utf-8 -*-
"""
@author: salimt
"""

import random

def deterministicNumber():
    '''(None) -> Int
    
    Deterministically generates and returns an even number between 9 and 21
    '''
    s = random.getstate()
    a = random.randrange(20,9,-2)
    random.setstate(s)
    return a
#print(deterministicNumber())

def stochasticNumber():
    '''(None) -> Int
    
    Stochastically generates and returns a uniformly distributed even number between 9 and 21
    '''
    a = random.randrange(9,21)
    return a if a % 2 == 0 else stochasticNumber()
#print(stochasticNumber())
    

import pylab as plt
import numpy as np
def plot():
    
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