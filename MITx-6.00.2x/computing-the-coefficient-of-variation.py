# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import statistics

def cv(L):
    """(list) -> float
    
    Returns:
    coefficient of variation(float) of the elements(int) of the given list.

    >>> cv([10, 4, 12, 15, 20, 5])
    0.503
    """
    std = statistics.pstdev(L)          #Compute Population Standard Deviation
    mean = sum([x for x in L])/len(L)   #Get the mean of the List
    return round(std/mean, 3)           #Compute the coefficient of variation of L to 3 decimal places.

if __name__ == '__main__':
    import doctest
    doctest.testmod()