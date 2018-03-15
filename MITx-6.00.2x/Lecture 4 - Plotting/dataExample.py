# -*- coding: utf-8 -*-
"""
Created on Sun Jun 12 13:33:39 2016

@author: ericgrimson
"""

import pylab as plt

def fib(n):
    if n == 1 or n == 2:
        return 1
    else:
        return fib(n-1) + fib(n-2)
    
inputs = []
results = []
for i in range(5):
    print(fib(i))


def displayFibs(n):
    (xvals, yvals) = gatherFacts(n)
    plt.figure('fibs')
    plt.plot(xvals, yvals, label = 'fibonacci')
    
