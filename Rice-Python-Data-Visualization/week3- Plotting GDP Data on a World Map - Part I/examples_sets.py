"""
Using sets.
"""

print("Set Literals")
print("============")

numbers = {3, 2, 1, 4}
print(numbers)

letters = {"a", "b", "a", "c", "b"}
print(letters)

empty = {}
print(empty, type(empty))

empty2 = set()
print(empty2, type(empty2))

set1 = set([3, 1, 1, 3, 6, 5])
print(set1)

set2 = set(range(5))
print(set2)

print("")
print("Adding/Removing Elements")
print("========================")

set1.add(10)
print(set1)

element = set1.pop()
print(element)
print(set1)

set1.discard(3)
print(set1)

print("")
print("Set Iteration")
print("=============")

for item in letters:
    print(item)
    
