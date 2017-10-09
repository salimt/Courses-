# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def genPrimes():
    n = 2
    primes = []
    while True:
        for p in primes:
            if n % p == 0:
                break
        else:
            primes.extend([n])
            yield n
        n += 1
        
primes = genPrimes()
# primes.__next__()