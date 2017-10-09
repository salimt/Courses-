# -*- coding: utf-8 -*-
"""
Created on Mon Sep  4 19:41:19 2017

@author: ACER
"""

def flatten(aList):
    ''' 
    aList: a list 
    Returns a copy of aList, which is a flattened version of aList 
    '''
    flatList = []
    for item in aList:
        if type(item) == list:
            flatList.extend(flatten(item))
            #print(flatList)
        else:
            print(item)
            flatList.append(item)
            #print(flatList)
    return flatList

    
    
L = [[1,'a',['cat'],2],[[[3]],'dog'],4,5]
print(flatten(L))