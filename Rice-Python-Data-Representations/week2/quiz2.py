# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def strange_sum(L):
    for i in L:
        if i % 3 == 0:
            L.remove(i)
    return sum(L)

print(strange_sum([1, 2, 3, 4, 5, 1, 2, 3, 4, 5]))
print(strange_sum(list(range(123)) + list(range(77))))


n = 4
test_string = "xxx" + " " * n + "xxx"
print(test_string, list(test_string))
split_list = test_string.split(" ")
print(split_list, len(split_list))

print(list(" "*4))