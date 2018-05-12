# -*- coding: utf-8 -*-
"""
@author: salimt
"""

NUM_ROWS = 3
NUM_COLS = 5

# construct a matrix
my_matrix = {}
for row in range(NUM_ROWS):
    row_dict = {}
    for col in range(NUM_COLS):
        row_dict[col] = row * col
    my_matrix[row] = row_dict
    
print(my_matrix)
 
# print the matrix
for row in range(NUM_ROWS):
    for col in range(NUM_COLS):
        print(my_matrix[row][col], end = " ")
    print()