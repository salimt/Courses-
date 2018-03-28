"""
Mutating Lists.
"""

print("Updating Items")
print("==============")

lst = list(range(5))
print(lst)

lst[1] = -7
lst[3] = 17
print(lst)

print("")
print("Adding Items")
print("============")

lst.append(42)
print(lst)

lst.insert(2, 75)
print(lst)

lst2 = [-56, 27, 8]
lst.extend(lst2)
print(lst)
lst.append(lst2)
print(lst)

print("")
print("Removing Items")
print("==============")

lst.pop()
print(lst)
lst.pop(3)
print(lst)
