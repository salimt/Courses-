# -*- coding: utf-8 -*-
"""
@author: salimt

Project for Week 4 of "Python Data Representations".
Find differences in file contents.

Be sure to read the project description page for further information
about the expected behavior of the program.

Results
Score: 100.0/100 Perfect score! All tests pass. Great job!
"""


IDENTICAL = -1

def singleline_diff(line1, line2):
    """
    Inputs:
      line1 - first single line string
      line2 - second single line string
    Output:
      Returns the index where the first difference between
      line1 and line2 occurs.

      Returns IDENTICAL if the two lines are the same.
    """
    if len(line1) <= len(line2):
        min_line = line1
        max_len = line2
    else:
        min_line = line2
        max_len = line1  

        
    for index in range(len(max_len)):
        if index == len(min_line):
            return index
        elif not min_line[index] == max_len[index]:
            return index
    return IDENTICAL

     
#print(singleline_diff('', ''))         #-1
#print(singleline_diff('abc', 'abcd')) #3
#print(singleline_diff('a', 'b'))      #0

def singleline_diff_format(line1, line2, idx):
    """
    Inputs:
      line1 - first single line string
      line2 - second single line string
      idx   - index of first difference between the lines
    Output:
      Returns a three line formatted string showing the location
      of the first difference between line1 and line2.

      If either input line contains a newline or carriage return,
      then returns an empty string.

      If idx is not a valid index, then returns an empty string.
    """
    

    if idx > len(line1) or idx < 0 or idx > len(line2):
        return ""
    return line1 + '\n' + '='*idx + '^\n' + line2 + '\n'
    

#print(singleline_diff_format('', 'a', 0))
#print( singleline_diff_format('Python is fast!!!', 'Python is fun!!!', 11))
#print(singleline_diff_format('abc', 'abd', 2))
#print(singleline_diff_format('', '', 3))
#print(singleline_diff_format('abc', 'abcd', 3)) 
#print(singleline_diff_format('a', 'b', 0)) 


def multiline_diff(lines1, lines2):
    """
    Inputs:
      lines1 - list of single line strings
      lines2 - list of single line strings
    Output:
      Returns a tuple containing the line number (starting from 0) and
      the index in that line where the first difference between lines1
      and lines2 occurs.

      Returns (IDENTICAL, IDENTICAL) if the two lists are the same.
    """
    diff = False
    line_number = 0
    
    if len(lines1) <= len(lines2):
        min_line = lines1
        max_len = lines2
    else:
        min_line = lines2
        max_len = lines1 
    
    if not lines1 == lines2:
        line_number = singleline_diff(lines1, lines2)
        diff = True
    
    if len(max_len) > len(min_line):
        return (line_number, 0)
   
    if not lines1 == lines2:
        idx = singleline_diff(lines1[line_number], lines2[line_number])
    else:
        return (IDENTICAL, IDENTICAL)
    
    if diff:
        return (line_number, idx)
    
    

#print(multiline_diff([], [])) #(-1, -1)
#print(multiline_diff(['line2', 'line2'], ['line1', 'line2']))         #(0, 4)
#print(multiline_diff(['line1', 'line2'], ['line1', 'line2']))         #(-1, -1)
#print(multiline_diff(['line1', 'line2'], ['line1', 'line2', 'line3'])) #expected (2, 0)
#print(multiline_diff(['a'], ['b']))
#print(multiline_diff(['a'], ['a']))

def get_file_lines(filename):
    """
    Inputs:
      filename - name of file to read
    Output:
      Returns a list of lines from the file named filename.  Each
      line will be a single line string with no newline ('\n') or
      return ('\r') characters.

      If the file does not exist or is not readable, then the
      behavior of this function is undefined.
    """
    
    file = open(filename, "rt")
    lines = [line[:-1] if '\n' in line else line[:] for line in file.readlines()]
    return lines

#path = os.getcwd() + '\\' + 'file1.txt'
#path = os.getcwd() + '\\' + 'file8.txt'
#print(get_file_lines(path))

def file_diff_format(filename1, filename2):
    """
    Inputs:
      filename1 - name of first file
      filename2 - name of second file
    Output:
      Returns a four line string showing the location of the first
      difference between the two files named by the inputs.

      If the files are identical, the function instead returns the
      string "No differences\n".

      If either file does not exist or is not readable, then the
      behavior of this function is undefined.
    """
    
    file1 = open(filename1, "rt")
    file2 = open(filename2, "rt")
    
    first_line = file1.readlines()
    second_line = file2.readlines()
    
    diff = multiline_diff(first_line, second_line)
    line, idx = diff[0], diff[1]
    
    
    if line == -1 and idx == -1:
        return "No differences\n"
    if first_line == [] or second_line == []:
        return 'Line ' + str(line) + ':' + '\n' + "".join(first_line) + '\n' + \
                                        '='*idx + '^\n' + "".join(second_line) + '\n'      
    return 'Line ' + str(line) + ':' + '\n' + first_line[line] + '='*idx + '^\n' + second_line[line]
    

#print(file_diff_format('file6.txt', 'file7.txt'))
#print(file_diff_format('file1.txt', 'file2.txt'))
#print(file_diff_format('file8.txt', 'file9.txt'))