# -*- coding: utf-8 -*-
"""
Created on Sat Aug 20 11:09:36 2016

@author: guttag
"""
import random
 
def rollDie():
    """returns a random int between 1 and 6"""
    return random.choice([1,2,3,4,5,6])
 
def testRoll(n = 10):
    result = ''
    for i in range(n):
        result = result + str(rollDie())
    print(result)

#testRoll(5)

random.seed(0)

def runSim(goal, numTrials):
    total = 0
    for i in range(numTrials):
        result = ''
        for j in range(len(goal)):
            result += str(rollDie())
        if result == goal:
            total += 1
    print('Actual probability =',
          round(1/(6**len(goal)), 8)) 
    estProbability = round(total/numTrials, 8)
    print('Estimated Probability  =',
          round(estProbability, 8))
    
#runSim('11111', 1000)

def fracBoxCars(numTests):
    numBoxCars = 0.0
    for i in range(numTests):
        if rollDie() == 6 and rollDie() == 6:
            numBoxCars += 1
    return numBoxCars/numTests
    
print('Frequency of double 6 =',
      str(fracBoxCars(100000)*100) + '%')
