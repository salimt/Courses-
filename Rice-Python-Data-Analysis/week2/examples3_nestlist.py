"""
Tabular data as a nested list.
"""

# Programming language popularity, from www.tiobe.com/tiobe-index
popularity = [["Language", 2017, 2012, 2007, 2002, 1997, 1992, 1987],
              ["Java", 1, 2, 1, 1, 15, 0, 0],
              ["C", 2, 1, 2, 2, 1, 1, 1],
              ["C++", 3, 3, 3, 3, 2, 2, 5],
              ["C#", 4, 4, 7, 13, 0, 0, 0],
              ["Python", 5, 7, 6, 11, 27, 0, 0],
              ["Visual Basic .NET", 6, 17, 0, 0, 0, 0, 0],
              ["PHP", 7, 6, 4, 5, 0, 0, 0],
              ["JavaScript", 8, 9, 8, 7, 23, 0, 0],
              ["Perl", 9, 8, 5, 4, 4, 10, 0]]

format_string = "{:<20}  {:>4}  {:>4}  {:>4}  {:>4}  {:>4}  {:>4}  {:>4}"

# Display langauges table
headers = popularity[0]
header_row = format_string.format(*headers)
print(header_row)
print("-" * len(header_row))

for language in popularity[1:]:
    print(format_string.format(*language))

print("")
    
# Finding/selecting items

# What was Python's popularity in 1997?
print("Python's popularity in 1997:", popularity[5][5])

def find_col(table, col):
    """
    Return column index with col header in table
    or -1 if col is not in table
    """
    return table[0].index(col)

def find_row(table, row):
    """
    Return row index with row header in table
    or -1 if row is not in table
    """
    for idx in range(len(table)):
        if table[idx][0] == row:
            return idx
    return -1
    
idx1997 = find_col(popularity, 1997)
idxpython = find_row(popularity, "Python")
print("Python's popularity in 1997:", popularity[idxpython][idx1997])

