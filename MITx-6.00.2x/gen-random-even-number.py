# -*- coding: utf-8 -*-
"""
@author: salimt
"""

import random

def genEven():
    '''(None) -> Int
    
    Returns a random even number x, where 0 <= x < 100
    '''
    return random.randrange(0, 100, 2)

print(genEven())