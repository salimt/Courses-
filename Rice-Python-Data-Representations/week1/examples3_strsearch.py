"""
String searching examples.
"""

sentence = "When I tell you pick up the " + \
           "left rock, it will be the " + \
           "right one, and then only " + \
           "the right rock will be left."

# Finding strings within strings
print("Finding lefts")
firstleft = sentence.find("left")
print(firstleft, sentence[firstleft])
lastleft = sentence.rfind("left")
print(lastleft, sentence[lastleft])

print("")
print("Finding rights")
firstright = sentence.index("right")
print(firstright, sentence[firstright])
lastright = sentence.rindex("right")
print(lastright, sentence[lastright])

print("")
print("Finding Rixner")
firstrixner1 = sentence.find("Rixner")
print(firstrixner1)
# firstrixner2 = sentence.index("Rixner")
# print(firstrixner2)

# Counting strings within strings
print("")
print("Counting substrings")
print("Number of lefts:", sentence.count("left"))
print("Number of apples:", sentence.count("apple"))

# Checking start/ends
print("")
print(sentence.startswith("When"))
print(sentence.endswith("The end."))
