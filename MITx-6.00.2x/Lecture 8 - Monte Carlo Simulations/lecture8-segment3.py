# -*- coding: utf-8 -*-
"""
Created on Sat Sep 17 10:12:16 2016

@author: guttag
"""
import pylab

#set line width
pylab.rcParams['lines.linewidth'] = 4
#set font size for titles 
pylab.rcParams['axes.titlesize'] = 20
#set font size for labels on axes
pylab.rcParams['axes.labelsize'] = 20
#set size of numbers on x-axis
pylab.rcParams['xtick.labelsize'] = 16
#set size of numbers on y-axis
pylab.rcParams['ytick.labelsize'] = 16
#set size of ticks on x-axis
pylab.rcParams['xtick.major.size'] = 7
#set size of ticks on y-axis
pylab.rcParams['ytick.major.size'] = 7
#set size of markers, e.g., circles representing points
pylab.rcParams['lines.markersize'] = 10
#set number of times marker is shown when displaying legend
pylab.rcParams['legend.numpoints'] = 1

import random, numpy

def throwNeedles(numNeedles):
    inCircle = 0
    for Needles in range(1, numNeedles + 1, 1):
        x = random.random()
        y = random.random()
        if (x*x + y*y)**0.5 <= 1.0:
            inCircle += 1
    return 4*(inCircle/float(numNeedles))

def getEst(numNeedles, numTrials):
    estimates = []
    for t in range(numTrials):
        piGuess = throwNeedles(numNeedles)
        estimates.append(piGuess)
    sDev = numpy.std(estimates)
    curEst = sum(estimates)/len(estimates)
    print('Est. = ' + str(curEst) +\
          ', Std. dev. = ' + str(round(sDev, 6))\
          + ', Needles = ' + str(numNeedles))
    return (curEst, sDev)

def estPi(precision, numTrials):
    numNeedles = 1000
    sDev = precision
    while sDev >= precision/1.96:
        curEst, sDev = getEst(numNeedles, numTrials)
        numNeedles *= 2
    return curEst

random.seed(0)
estPi(0.005, 100)


def integrate(f, a, b, step):
    yVals, xVals = [], []
    xVal = a
    while xVal <= b:
        xVals.append(xVal)
        yVals.append(f(xVal))
        xVal += step
    pylab.plot(xVals, yVals)
    pylab.title('sin(x)')
    pylab.xlim(a, b)
    xUnders, yUnders, xOvers, yOvers = [],[],[],[]
    for i in range(500):
        xVal = random.uniform(a, b)
        yVal = random.uniform(0, 1)
        if yVal < f(xVal):
            xUnders.append(xVal)
            yUnders.append(yVal)
        else:
            xOvers.append(xVal)
            yOvers.append(yVal)
    pylab.plot(xUnders, yUnders, 'ro')
    pylab.plot(xOvers, yOvers, 'ko')
    pylab.xlim(a, b)
    ratio = len(xUnders)/(len(xUnders) + len(yUnders))
    print(ratio)
    print(ratio*b)
    
def one(x):
    return 0.9
    
#integrate(one, 0, math.pi, 0.001)