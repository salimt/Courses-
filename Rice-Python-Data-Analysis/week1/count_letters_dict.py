# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def count_letters(word_list):
    """ (str) -> dict 
    
   Counts number of occurrences of letters in word_list.
    
    >>> count_letters('hello world')
    {'a': 0, 'b': 0, 'c': 0, 'd': 1, 'e': 1, 'f': 0, 'g': 0, 'h': 1, 'i': 0, \
    'j': 0, 'k': 0, 'l': 3, 'm': 0, 'n': 0, 'o': 2, 'p': 0, 'q': 0, 'r': 1, \
    's': 0, 't': 0, 'u': 0, 'v': 0, 'w': 1, 'x': 0, 'y': 0, 'z': 0}
    """
    
    ALPHABET = "abcdefghijklmnopqrstuvwxyz"
    word_list = word_list.lower()
    
    letter_count = {}
    for letter in ALPHABET:
        letter_count[letter] = 0

    for word in word_list:
        for letter in set(list(word)):
            if letter == ' ':
                pass
            else:
                letter_count[letter] += 1

    max_val = max(letter_count.values())
    return [key for key,value in zip(letter_count.keys(), letter_count.values()) if value == max_val]

print(count_letters('hello world'))
print(count_letters('listen strange women lying in ponds distributing swords is \
                    no basis for a system of government supreme executive power \
                    derives from a mandate from the masses not from some farcical\
                    aquatic ceremony'))