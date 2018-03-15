# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import random

def noReplacementSimulation(numTrials):
    '''(int) -> float
    
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    3 red and 3 green balls. Balls are not replaced once
    drawn. Returns the a decimal - the fraction of times 3 
    balls of the same color were drawn.
    
    Red = 1
    Green = -1 or 0
    '''
    allSame = 0

    # for i in num of trials:
    for ball in range(numTrials):
        bucket = ["red", "red", "red", "green", "green", "green"]
        
        firstBall = random.choice(bucket)
        bucket.remove(firstBall)
        
        secondBall = random.choice(bucket)
        bucket.remove(secondBall)
        
        thirdBall = random.choice(bucket)
        bucket.remove(thirdBall)
        
        if firstBall == secondBall == thirdBall:
            allSame += 1

    return allSame / numTrials

############ SECOND WAY ############## 
#    allSameColour = 0
#    for ball in range(1, numTrials + 1):
#        bucket = [1, 1, 1, -1, -1, -1]
#        nums = 0
#        for i in range(3):
#            chosenBall = random.choice(bucket)
#            bucket.remove(chosenBall)
#            nums += chosenBall
#            if nums == 3 or nums == -3:
#                allSameColour += 1
#    
#    return allSameColour / numTrials

############ THIRD WAY ##############
#    import numpy as np
#
#    allSame = 0
#    bucket = [1, 1, 1, 0, 0, 0]
#    for ball in range(1, numTrials + 1):    
#        summed = sum(np.random.choice(bucket, 3, replace=False))
#        if summed == 3 or summed == 0:
#                allSame += 1
#                
#    return allSame / float(numTrials)

print(noReplacementSimulation(5000))