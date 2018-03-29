"""
Week 4 practice project solution for Python Data Representation
Update syntax for print in CodeSkulptor Docs
from "print ..." syntax in Python 2 to "print(...)" syntax for Python 3
"""

# HTML tags that bounds example code
PREFIX = "<pre class='cm'>"
POSTFIX = "</pre>"
PRINT = "print"


def update_line(line):
    """
    Takes a string line representing a single line of code
    and returns a string with print updated
    """

    # Strip left white space using built-in string method lstrip()
    stripline = line.lstrip()
    
    # If line is print statement,  use the format() method to add insert parentheses
    if stripline[:len(PRINT)] == PRINT:
        spaces = ' ' * line.find(PRINT)
        content = stripline[len(PRINT) + 1:]
        return '{}print({})'.format(spaces, content)
    
    # Note that solution does not handle white space/comments after print statememt

    return line

# Some simple tests
print(update_line(""))
print(update_line("foobar()"))  
print(update_line("print 1 + 1"))      
print(update_line("    print 2, 3, 4"))

# Expect output
##
##foobar()
##print(1 + 1)
##    print(2, 3, 4)


def update_pre_block(pre_block):
    """
    Take a string that correspond to a <pre> block in html and parses it into lines.  
    Returns string corresponding to updated <pre> block with each line
    updated via process_line()
    """
    
    lines = pre_block.split("\n")
    updated_block = update_line(lines[0])
    for line in lines[1:]:
        updated_block += "\n"
        updated_block += update_line(line)
    return updated_block

# Some simple tests
print(update_pre_block(""))
print(update_pre_block("foobar()"))
print(update_pre_block("if foo():\n    bar()"))
print(update_pre_block("print\nprint 1+1\nprint 2, 3, 4"))
print(update_pre_block("    print a + b\n    print 23 * 34\n        print 1234"))

# Expected output
##
##foobar()
##if foo():
##    bar()
##print()
##print(1+1)
##print(2, 3, 4)
##    print(a + b)
##    print(23 * 34)
##        print(1234)

def update_file(input_file_name, output_file_name):
    """
    Open and read the file specified by the string input_file_name
    Proces the <pre> blocks in the loaded text to update print syntax)
    Write the update text to the file specified by the string output_file_name
    """
    # open file and read text in file as a string
    with open(input_file_name) as doc_file:
        doc_text = doc_file.read()
        
    # split text in <pre> blocks and update using update_pre_block()
    parts = doc_text.split(PREFIX)
    updated_text = parts[0]
    for part in parts[1:]:
        updated_text += PREFIX
        [pre_block, filler] = part.split(POSTFIX, 1)
        updated_text += update_pre_block(pre_block)
        updated_text += POSTFIX
        updated_text += filler

    # Write the answer in the specified output file
    with  open(output_file_name, "w") as processed_file:
        processed_file.write(updated_text)

# A couple of test files
update_file("table.html", "table_updated.html")
update_file("docs.html", "docs_updated.html")

# Import some code to check whether the computed files are correct
import examples3_file_diff as file_diff
file_diff.compare_files("table_updated.html", "table_updated_solution.html")
file_diff.compare_files("docs_updated.html", "docs_updated_solution.html")

# Expected output
##table_updated.html and table_updated_solution.html are the same
##docs_updated.html and docs_updated_solution.html are the same
