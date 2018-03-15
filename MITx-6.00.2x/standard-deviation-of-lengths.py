# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def stdDevOfLengths(L):
    """(list) -> float
    
    L: a list of strings

    returns: float, the standard deviation of the lengths of the strings,
      or NaN if L is empty.
     
    >>> stdDevOfLengths(['a', 'z', 'p'])
    0.0
    >>> stdDevOfLengths(['apples', 'oranges', 'kiwis', 'pineapples'])
    1.8708286933869707
    >>> stdDevOfLengths(['fi', 'udtyf', 'rbtayfm', 'qfmedcp', 'aikylpk'])
    1.9595917942265426
    """

    import math as math    
    if len(L) == 0: return float('NaN')
    mean = sum([len(x) for x in L])/len(L)
    return math.sqrt(sum([((len(t)-mean)**2)/len(L) for t in L]))

for i in range(10):
    if type(i) == int or i==5: print('its 5')
        

print(stdDevOfLengths(['fi', 'udtyf', 'rbtayfm', 'qfmedcp', 'aikylpk']))

if __name__ == '__main__':
    import doctest
    doctest.testmod()