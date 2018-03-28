"""
Reading files.
"""

print("Opening Files")
print("=============")

# Open takes a filename and a mode
openfile = open("the_idiot.txt", "rt")

# Modes for reading:
#  r - read (default)
#  t - text (default)
#  b - binary

print(type(openfile))
print(openfile)

# Must close file after opening it
openfile.close()

print("")
print("Errors")
print("======")

# nofile = open("nosuchfile.txt")

print("")
print("Reading")
print("=======")

datafile = open("the_idiot.txt", "rt")

data = datafile.read()

print("type:", type(data))
print("length:", len(data))
print("")
print(data)

datafile.close()
