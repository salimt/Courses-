# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#s is given by mit's ide
#print(len(s), word)
bobcounter = 0
for i  in range(len(s) - 2):
    if (s[i]=='b' and s[i+1]=='o' and s[i+2]=='b'):
        bobcounter += 1
    else:
        continue
print("Number of times bob occurs is: " + str(bobcounter))
