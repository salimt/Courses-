"""
Splitting and Joining Strings
"""

print("Splitting")
print("=========")

# From "A Girl I knew" by J. D. Salinger
sentence = "She wasn't doing a thing that I could see, except standing there leaning on the balcony railing, holding the universe together."

print(sentence)

# String split
words = sentence.split()
print(words)

# Explicit separator
words2 = sentence.split(" ")
print(words2)
phrases = sentence.split(",")
print(phrases)
parts = sentence.split("the")
print(parts)

print("")
print("Joining")
print("=======")

items = ["flowers", "puddle", "mouse pad", "outlet", "bread", "house"]

print(items)

# Join together
print("".join(items))
print(" ".join(items))
print(",".join(items))
print(", ".join(items))
