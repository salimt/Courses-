# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import numpy as np
import itertools

def find_combination(choices, total):
    """(list of ints, int) -> array
    
    choices: a non-empty list of ints
    total: a positive int
 
    Returns result, a numpy.array of length len(choices) 
    such that
        * each element of result is 0 or 1
        * sum(result*choices) == total
        * sum(result) is as small as possible
    In case of ties, returns any result that works.
    If there is no result that gives the exact total, 
    pick the one that gives sum(result*choices) closest 
    to total without going over.
    
    >>> find_combination([1, 81, 3, 102, 450, 10], 9)
    array([1, 0, 1, 0, 0, 0])
    >>> find_combination([10, 100, 1000, 3, 8, 12, 38], 1171)
    array([1, 1, 1, 1, 1, 1, 1])
    >>> find_combination([1,1,3,5,3], 5)
    array([0, 0, 0, 1, 0])
    """

    result = [seq for i in range(len(choices), 0, -1) for seq in itertools.combinations(choices, i) if sum(seq) == total]
    
    if not result:
        result = [seq for i in range(len(choices), 0, -1) for seq in itertools.combinations(choices, i) if sum(seq) < total]
            
    minLen, maxLen = len(choices), 0
    minList, maxList = [], [] 
    
    for i in result:
        if len(i) <= minLen:
            minList = i
            minLen = len(i)
        if len(i) >= maxLen:
            maxList = i
            maxLen = len(i)
        
    for i in range(len(choices)):
        if (sum(maxList) > sum(minList) and choices[i] in maxList) or (choices[i] in minList):
            choices[i] = 1
        else:
            choices[i] = 0
    return np.array(choices)

print(find_combination([1], 10))                             #array([1])
print(find_combination([1, 81, 3, 102, 450, 10], 9))         #array([1, 0, 1, 0, 0, 0])
print(find_combination([10, 100, 1000, 3, 8, 12, 38], 1171)) #array([1, 1, 1, 1, 1, 1, 1])
print(find_combination([1,1,3,5,3], 5))                      #array([0, 0, 0, 1, 0])
print(find_combination([1,2,2,3], 4))                        #array([0, 1, 1, 0]) or array([1, 0, 0, 1])