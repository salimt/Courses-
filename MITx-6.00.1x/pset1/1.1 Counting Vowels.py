# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#s is given by mit's ide
vowelscounter = 0
for letter in s:
    if (letter=="a") or (letter=="e") or (letter=="o") or (letter=="i") or (letter=="u"):
        vowelscounter += 1
    else:
        continue
print("Number of vowels: " + str(vowelscounter))
