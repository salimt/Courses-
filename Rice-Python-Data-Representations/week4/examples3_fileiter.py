"""
Iterating over files.
"""

# Using readlines()
#  readlines creates a list of strings
#  that you can iterate over

datafile1 = open("the_idiot.txt", "rt")

for line in datafile1.readlines():
    print(line)

datafile1.close()

print("")
print("================================")
print("")

# Direct iteration
#  This is faster for large files,
#  as no list is created

datafile2 = open("the_idiot.txt", "rt")

for line in datafile2:
    print(line)

datafile2.close()


