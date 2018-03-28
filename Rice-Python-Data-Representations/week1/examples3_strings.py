"""
Simple string literal examples.
"""

# Strings are enclosed in quotes
name = 'Scott Rixner'
university = "Rice"

print(name)
print(university)

# Multiline strings use triple quotes
address = '''Rice University
Houston, TX
'''

# First Fig by Edna St. Vincent Millay
poem = """My candle burns at both ends;
  It will not last the night;
But ah, my foes, and oh, my friends---
  It gives a lovely light!
"""

print("")

print("Address")
print("=======")
print(address)

print("First Fig")
print("=========")
print(poem)


# Characters
chars = "abc'DEF*&$"
print(chars)
chars2 = '\t"abc"\ndef\''
print(chars2)

# String "arithmetic"
print("Concatenating strings")
name_and_uni = name + " " + university
print(name_and_uni)

print("")
print("")
print("Repeating strings")
lots_o_rice = university * 4
print(lots_o_rice)

# Using str
num = 3.87
strnum = str(num)
print("number: " + strnum)
# print("number: " + num)
