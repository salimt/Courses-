# -*- coding: utf-8 -*-
"""
@author: salimt
"""

#Write a function count_vowels(word) that takes the string word as input and 
#returns the number of occurrences of lowercase vowels (i.e. the lowercase letters "aeiou") 
#in word. Hint: Python has a built-in string method that can count the number of occurrences 
#of a letter in a string.

def count_vowels(word):
    total = 0
    for i in word:
        if i in 'aeiou':
            total += 1
    return total
print(count_vowels("aaassseefffgggiiijjjoOOkkkuuuu"))
print(count_vowels("aovvouOucvicIIOveeOIclOeuvvauouuvciOIsle"))



#Write a function demystify(l1_string) that takes a string composed of the characters 
#"l" and "1" and returns the string formed by replacing each instance of "l" by "a"
# and each instance of "1" by "b".

def demystify(key):
    key = key.replace('1', 'b',)
    key =  key.replace('l', 'a',)
    return key
print(demystify("lll111l1l1l1111lll"))
print(demystify("111l1l11l11lll1lll1lll11111ll11l1ll1l111"))


#Which of the string format expressions below return the string "abracadabra"?

print("{0}{1}{0}".format("abra", "cad"))
print("{}{}{}".format("abra", "cad", "abra"))