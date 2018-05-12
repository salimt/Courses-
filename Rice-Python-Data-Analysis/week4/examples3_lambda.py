"""
Examples of creating and using anonymous functions.
"""

import random

# Easily create a list of numbers
data = list(range(10))
print("range data:", data)

def square(val):
    return val ** 2

# Square all numbers in the list
squares = list(map(square, data))
print("squares:", squares)

# Double all numbers in the list
doubles = list(map(lambda num: num * 2, data))
print("doubles:", doubles)

# Create a list of random numbers (list comprehension)
randnums = [random.randrange(2, num+3) for num in range(10)]
print("random numbers:", randnums)

# Create a list of tuples
tups = list(map(lambda num1, num2: (num1, num2), data, randnums))
print("tuples:", tups)

# Create a list of the min values in the tuples
mins = list(map(lambda pair: min(pair[0], pair[1]), tups))
print("minimums:", mins)

# Create a list only of tuples where the second item is less than the first
newtups = list(filter(lambda pair: pair[1] < pair[0], tups))
print("filtered:", newtups)
