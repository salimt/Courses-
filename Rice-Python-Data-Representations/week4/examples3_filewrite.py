"""
Writing Files.
"""

print("Opening Files")
print("=============")

openfile = open("output.txt", "wt")

# Modes for writing:
#  w - write (erases the file first)
#  a - write (appends to the end of the file)
#  t - text (default)
#  b - binary
#  + - open for read and write

print(type(openfile))
print(openfile)

# Always close files
openfile.close()

print("")
print("Writing")
print("=======")

def checkfile(filename):
    """
    Read and print the contents of the file named filename.
    """
    datafile = open(filename, "rt")
    data = datafile.read()
    datafile.close()
    print(data)

# Write
outputfile = open("output.txt", "wt")
outputfile.writelines(["first line\n", "second line\n"])
outputfile.write("third line\nfourth line\n")
outputfile.close()

print("Initial file contents:")
checkfile("output.txt")


# Overwrite
outputfile2 = open("output.txt", "wt")
outputfile2.write("overwriting contents\n")
outputfile2.close()

print("Overwritten file contents:")
checkfile("output.txt")


# Append
outputfile2 = open("output.txt", "at")
outputfile2.write("appending to contents\n")
outputfile2.close()

print("Appended file contents:")
checkfile("output.txt")
