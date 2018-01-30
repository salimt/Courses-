# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def is_palindrome_v1(s):
    """ (str) -> bool
    
    Return True if and only if s is a palindrome.
    
    >>> is_palindrome_v1('noon')
    True
    >>> is_palindrome_v1('racecar')
    True
    >>> is_palindrome_v1('dented')
    False
    """
    return s[:] == s[::-1]    
#print(is_palindrome_v1('racecar'), is_palindrome_v1('dented'))

def is_palindrome_v2(s):
    """ (str) -> bool
    
    Return True if and only if s is a palindrome.
    
    >>> is_palindrome_v1('noon')
    True
    >>> is_palindrome_v1('racecar')
    True
    >>> is_palindrome_v1('dented')
    False
    """
    n = len(s)
    return s[:n//2] == s[n-1:n-n//2-1:-1]
#print(is_palindrome_v2('racecar'), is_palindrome_v2('dented'))

def is_palindrome_v3(s):
    """ (str) -> bool
    
    Return True if and only if s is a palindrome.
    
    >>> is_palindrome_v1('noon')
    True
    >>> is_palindrome_v1('racecar')
    True
    >>> is_palindrome_v1('dented')
    False
    """
    counter = 0
    for ch in range(len(s)):
        if s[ch] == s[-ch-1]:
            counter += 1
    return counter == len(s)
    
   #i = 0
   #j = len(s) - 1
   # while i < j and s[i] == s[j]:
   #     i += 1
   #     j -= 1
   # return  j <= i   
#print(is_palindrome_v3('racecar'), is_palindrome_v3('dented'))  