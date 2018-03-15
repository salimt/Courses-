# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import math

def isPrime(num):
    """ (int) -> str
    
    Checks if the given number is prime or not.
    
    >>> isPrime(1)
    Not prime
    >>> isPrime(2)
    Prime
    """
    
    if num == 1 or num == 0:
        return 'Not prime'    
    sq = int(math.sqrt(num))
    for i in range(2, sq+1):
        if num % i == 0 and num != i:
            print(i)
            return 'Not prime'
    return 'Prime'

#x = 10                    #len(listOfints)
#listOfints = [1000000000, #not prime
#              1000000001, #not prime
#              1000000002, #not prime
#              1000000003, #not prime
#              1000000004, #not prime
#              1000000005, #not prime
#              1000000006, #not prime
#              1000000007, #prime
#              1000000008, #not prime
#              1000000009] #prime
#for i in range(x):
#    print(isPrime(listOfints[i]))
    
if __name__ == "__main__":
    x = int(input('How many numbers?: '))
    for num in range(x):
        try:
            print(isPrime(int(input('Number %d: ' %(num+1)))))
        except ValueError: print('Invalid Integer')