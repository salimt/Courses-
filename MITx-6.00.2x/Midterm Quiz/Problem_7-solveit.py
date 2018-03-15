# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#Problem 7
#20.0/20.0 points (graded)
#Write a function that meets the specification below:

#You are not allowed to import anything. Do not leave any debugging print stataments. Click "See full output" to see the test cases passed/failed.


def solveit(test):
    """ test, a function that takes an int parameter and returns a Boolean
        Assumes there exists an int, x, such that test(x) is True
        Returns an int, x, with the smallest absolute value such that test(x) is True 
        In case of ties, return any one of them. 
    """
    for i in range(1000):
        if test(i):
            return i
    for i in range(1000, -1000, -1):
        if test(i):
            return i
        
#### This test case prints 49 ####
def f(x):
    return (x+15)**0.5 + x**0.5 == 15
print(solveit(f))

#### This test case prints 0 ####
def f(x):
    return x == 0
print(solveit(f))

#### This test case prints 0 ####
def f(x):
    return x == -4
print(solveit(f))

#### This test case prints 3 ####
def f(x):
    return [1,2,3][-x] == 1 and x != 0
print(solveit(f))
