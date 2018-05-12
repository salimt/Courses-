"""
More advanced sorting examples.
"""

import random

# Easily create a shuffled list of numbers
data = list(range(10))
random.shuffle(data)
print("shuffled data:", data)

# Sort the list of numbers
data.sort()
print("ascending sort:", data)
data.sort(reverse=True)
print("descending sort:", data)

# Create a list of tuples
datatups = [(item, random.randrange(3, 15)) for item in data]
print("data tuples:", datatups)

# Sort the list
datatups.sort()
print("sorted data tuples:", datatups)

datatups.sort(key=lambda pair: pair[1])
print("sorted by second item:", datatups)

datatups.sort(key=lambda pair: pair[0] * pair[1], reverse=True)
print("sorted by product:", datatups)

# Shuffle it again
random.shuffle(datatups)
print("shuffled tuples:", datatups)

# Use sorted to sort the list
newdata = sorted(datatups, key=lambda pair: pair[1], reverse=True)
print("tuples after sorted:", datatups)
print("returned from sorted:", newdata)

