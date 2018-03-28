"""
Tuple examples.
"""

# Lists and tuples are both sequences
print("Sequences")
print("=========")
lst = [1, 5, 7, 3]
tup = (1, 5, 7, 3)

print(lst, tup)
print(lst[2])
print(tup[2])
print(tup[:2])
print(tup[2:3])

# Tuples are immutable
lst[0] = 9
print(lst)
# tup[0] = 9
# print(tup)

print("")
print("Tuple Methods")
print("=============")

print(tup.index(7))
print(tup.count(4))

print("")
print("Iteration")
print("=========")

for item in tup:
    print(item)

print("")
print("Conversion")
print("==========")

lst2 = [8, 6, 4, 8, 2]
print(lst2)
tup2 = tuple(lst2)
print(tup2)
# tup2[3] = 7
lst3 = list(tup2)
print(lst3)
lst3[2] = 7
print(lst2, tup2, lst3)
