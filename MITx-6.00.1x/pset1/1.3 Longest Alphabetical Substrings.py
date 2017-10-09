# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#s is given by mit's ide
maxcount = 0
counter = 0
result = 0
for letter in range(len(s)-1):
    if s[letter] <= s[letter+1]:
        counter += 1
        if counter > maxcount:
            maxcount = counter
            result = letter + 1
    else:
        counter = 0
starts = result - maxcount
#print(starts, result)
print("Longest substring in alphabetical order is:", s[starts:result + 1])
