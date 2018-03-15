# -*- coding: utf-8 -*-
"""
@author: salimt
"""
# Problem 5-1
# 1/1 point (graded)
# There are 2 parts to this problem on this page!

# Consider a list of positive (there is at least one positive) and negative numbers. You are asked to find the maximum sum of a
# contiguous subsequence. For example,
# - in the list [3, 4, -1, 5, -4], the maximum sum is 3+4-1+5 = 11
# - in the list [3, 4, -8, 15, -1, 2], the maximum sum is 15-1+2 = 16
# One algorithm goes through all possible subsequences and compares the sums of each contiguous subsequence with the largest sum it has
# seen. What is the time complexity of this algorithm in terms of the length of the list, N?

# O(n^2) Correct

#Problem 4
#20.0/20.0 points (graded)
#Consider a list of positive (there is at least one positive) and negative numbers. You are asked to find the maximum sum of a contiguous subsequence. For example,

#in the list [3, 4, -1, 5, -4], the maximum sum is 3+4-1+5 = 11
#in the list [3, 4, -8, 15, -1, 2], the maximum sum is 15-1+2 = 16
#Write a function that meets the specification below.


def max_contig_sum(L):
    """ L, a list of integers, at least one positive
    Returns the maximum sum of a contiguous subsequence in L """

############# This is getting the biggest powerset of L
    
#    def powerset(s):
#        x = len(s)
#        masks = [1 << i for i in range(x)]
#        for i in range(1 << x):
#            yield [ss for mask, ss in zip(masks, s) if i & mask]
#            
#    max_value = 0
#    for i in list(powerset(L)):
#        if sum(i) > max_value:
#            max_value = sum(i)
#    return max_value
    
############ This is getting the maximum contiguous subsequence
    
    max_value = 0
    value = 0
    for i in range(len(L)):
        value = value + L[i]
        if value < 0:
            value = 0
        if max_value < value:
            max_value = value
    return max_value
        
print(max_contig_sum([10, 9, 8, -1])) #27
print(max_contig_sum([1, -1])) #1
print(max_contig_sum([5, -7, 1])) #5