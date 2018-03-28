# -*- coding: utf-8 -*-
"""
@author: salimt
"""


def fib():
    """(None) -> int
    
    Given a list fib = [0, 1], write a loop that appends the sum of the last two
    items in fib to the end of fib. What is the value of the last item in fib after
    twenty iterations of this loop?
    
    """
    l = [0,1]
    for i in range(20):
        l.append(l[-1] + l[-2])
    return l[-1]

print(fib())



"""
Implement the Sieve of Eratosthenes
https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
"""

def compute_primes(bound):
    """
    Return a list of the prime numbers in range(2, bound)
    """
    
    answer = list(range(2, bound))
    for divisor in range(2, bound):
        for i in answer:
            if i % divisor == 0 and not i == divisor:
                answer.remove(i)

    return answer

print(compute_primes(10))
print(len(compute_primes(200)))
print(len(compute_primes(2000)))



def compute_primes2(bound):
    """
    Return a list of the prime numbers in range(2, bound)
    """
    
    answer = list(range(2, bound))
    for divisor in range(2, bound):
        for stride in range(2 * divisor, bound, divisor):
            if stride in answer:
                answer.remove(stride)
    return answer

print(len(compute_primes2(200)))
print(len(compute_primes2(2000)))