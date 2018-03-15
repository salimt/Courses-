# -*- coding: utf-8 -*-
"""
@author: salimt
"""
import random

class Item(object):
    def __init__(self, n, v, w):
        self.name = n
        self.value = float(v)
        self.weight = float(w)
    def getName(self):
        return self.name
    def getValue(self):
        return self.value
    def getWeight(self):
        return self.weight
    def __str__(self):
        return '<' + self.name + ', ' + str(self.value) + ', '\
                     + str(self.weight) + '>'
          
            
def buildItems():
    return [Item(n,v,w) for n,v,w in (('clock', 175, 10),
                                      ('painting', 90, 9),
                                      ('radio', 20, 4),
                                      ('vase', 50, 2),
                                      ('book', 10, 1),
                                      ('computer', 200, 20))]
    
def buildRandomItems(n):
    return [Item(str(i),10*random.randint(1,10),random.randint(1,10))
            for i in range(n)]
    
          
    
# generate all combinations of N items
def powerSet(items):
    N = len(items)
    # enumerate the 2**N possible combinations
    for i in range(2**N):
        combo = []
        for j in range(N):
            # test bit jth of integer i
            if (i >> j) % 2 == 1:
                combo.append(items[j])
        yield combo   
    
    

def yieldAllCombos(items):
    """
        Generates all combinations of N items into two bags, whereby each 
        item is in one or zero bags.

        Yields a tuple, (bag1, bag2), where each bag is represented as a list 
        of which item(s) are in each bag.
    """
    # Your code here
# generate all combinations of N items
    N = len(items)
    # enumerate the 2**N possible combinations
    for i in range(3**N):
        bag1 = []
        bag2 = []
#        no1 = []
#        no2 = []
        for j in range(N):
            # test bit jth of integer i
            if (i // 3**j) % 3 == 1:
#                no1.append((i, j))
                bag1.append(items[j])
            if (i // 3**j) % 3 == 2:
                bag2.append(items[j])
#                no2.append((i, j))
#        print(no1, no2)
#        print(bag1, bag2)
#        yield (bag1, bag2)
        

def ps(xs):
    result = [[]]
    for x in xs:
        newsubsets = []
        for subset in result:
            newsubsets.append(subset + [x])
        result.extend(newsubsets)
    return result

def ps_listcomprehension(xs):
    result = [[]]
    for x in xs:
        newsubsets = [subset + [x] for subset in result]
        result.extend(newsubsets)
    return result
        
    
print(ps([1,2]))      
print(yieldAllCombos([1,2]))                   
#items = buildItems()
#combos = yieldAllCombos(items)
#print(combos)
