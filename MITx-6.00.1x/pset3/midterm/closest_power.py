# -*- coding: utf-8 -*-
"""
@author: salimt
"""


def closest_power(base, num):
    """
    base: base of the exponential, integer > 1
    num: number you want to be closest to, integer > 0
    Find the integer exponent such that base**exponent is closest to num.
    Note that the base**exponent may be either greater or smaller than num.
    In case of a tie, return the smaller value.
    Returns the exponent.
    """

    exponent = 0
    while base**exponent <= num:
        exponent += 1
    #print(exponent)

    if abs(base**exponent - num) >= abs(base**(exponent-1) - num):
        return exponent-1

    return exponent

print(closest_power(2, 384.0)) # 8
print(closest_power(10, 550.0)) # 2
print(closest_power(3, 12))  # 2
print(closest_power(4, 12))  # 2
print(closest_power(4, 1))  # 0