# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import random

def drawing_without_replacement_sim(numTrials):
    '''(int) -> float
    
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    4 red and 4 green balls. Balls are not replaced once
    drawn. Returns a float - the fraction of times 3 
    balls of the same color were drawn in the first 3 draws.
    '''
    bucket = ['R', 'R', 'R', 'R', 'G', 'G', 'G', 'G']
    total = 0
    for trial in range(numTrials):
        for draw in range(3):
#            sample = (random.choices(bucket, k=3))
            sample = random.sample(bucket,3)
            if sample[0] == sample[1] == sample[2]:
                total += 1
                
    return round(total/(numTrials*3), 3)
print(drawing_without_replacement_sim(100))


import random, pylab
xVals = []
yVals = []
wVals = []
for i in range(1000):
    xVals.append(random.random())
    yVals.append(random.random())
    wVals.append(random.random())
xVals = pylab.array(xVals)
yVals = pylab.array(yVals)
wVals = pylab.array(wVals)
xVals = xVals + xVals
zVals = xVals + yVals
tVals = xVals + yVals + wVals
pylab.plot(xVals)
pylab.show()