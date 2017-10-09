# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def satisfiesF(L):
    """
    Assumes L is a list of strings
    Assume function f is already defined for you and it maps a string to a Boolean
    Mutates L such that it contains all of the strings, s, originally in L such
            that f(s) returns True, and no other elements. Remaining elements in L
            should be in the same order.
    Returns the length of L after mutation
    """
    # Your function implementation here
    a = []
    for i in L:
        i = str(i)
        if f(i) == True:
            a.extend(i)
    #print(len(a))
    L[:] = a
    return len(a)
    #print(f(s))
    
#run_satisfiesF(L, satisfiesF)
    
def f(s):
    return 'a' in s
      
L = ['a', 'b', 'a', '2', 2, '']
print (satisfiesF(L))
print (L)

#satisfiesF(L, satisfiesF)