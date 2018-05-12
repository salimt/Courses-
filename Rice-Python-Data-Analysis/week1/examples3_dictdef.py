"""
Dictionary creation.
"""

print("Dictionary Literals")
print("===================")

# Dictionary literals
empty = {}
print(empty)

simple = {1: 2}
print(simple)

squares = {1: 1, 2: 4, 3: 9, 4: 16}
print(squares)

cipher = {'p': 'o', 'y': 'h', 't': 'n',
          'h': 't', 'o': 'y', 'n': 'p'} 
print(cipher)

goodinstructors = {'Rixner': True, 'Warren': False}
print(goodinstructors)

cities = {'China': ['Shanghai', 'Beijing'],
          'USA': ['New York', 'Los Angeles'],
          'Spain': ['Madrid', 'Barcelona'],
          'Australia': ['Sydney', 'Melbourne'],
          'Texas': ['Houston', 'San Antonio']}
print(cities)

print("")
print("Creating Dictionaries")
print("=====================")

empty2 = dict()
print(empty2)

data = [(1, 'one'), (2, 'two'), (3, 'three')]
names = dict(data)
print(names)

cipher2 = dict(cipher)
print(cipher2)

