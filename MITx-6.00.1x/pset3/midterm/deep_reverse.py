# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def deep_reverse(L):
    """ assumes L is a list of lists whose elements are ints
    Mutates L such that it reverses its elements and also 
    reverses the order of the int elements in every element of L. 
    It does not return anything.
    """
    # Your code here
    a = []  
    f = L[::-1]
    #print(L)
    for row in f:
        a.append(row[::-1])
    print(a)
    L[:] = a
    print(L)
    
L = [[0, 1, 2], [1, 2, 3], [3, 2, 1], [10, -10, 100]]
deep_reverse(L) 
print(L)    