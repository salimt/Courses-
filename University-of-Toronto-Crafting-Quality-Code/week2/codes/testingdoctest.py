# -*- coding: utf-8 -*-
"""
@author: salimt
"""

'''
In[37]:  import doctest
In[38]:  doctest.testmod()
Out[39]: TestResults(failed=0, attempted=2)
'''

import doctest
doctest.testmod()

def get_divisors(num, possible_divisors):
    """(int, list of int) -> list of int
    
    Return a list of the values from divisors
    that are divisors of num.
    
    >>> get_divisors(8, [1,2,3])
    [1, 2]
    >>> get_divisors(4, [-2,0,2])
    [-2, 2]
    """
    divisors = []
    for item in possible_divisors:
        if item != 0 and num % item == 0:
            divisors.append(item)
    return divisors

def collect_vowels(s):
    """ (str) -> str

    Return the vowels (a, e, i, o, and u) from s.

    >>> collect_vowels('Happy Anniversary!')
    'aAiea'
    >>> collect_vowels('xyz')
    ''
    """

    vowels = ''

    for char in s:
        if char in 'aeiouAEIOU':
            vowels = vowels + char

    return vowels

def count_vowels(s):
    """ (str) -> int

    Return the number of vowels (a, e, i, o, and u) in s.

    >>> count_vowels('Happy Anniversary!')
    5
    >>> count_vowels('xyz')
    0
    """

    num_vowels = 0

    for char in s:
        if char in 'aeiouAEIOU':
            num_vowels = num_vowels + 1

    return num_vowels
