"""
Examples code for experimenting with options to the csv.read() and csv.write() methods
"""

import csv


# Function that prints 2D table to console

def print_table(table):
    """
    Echo a nested list to the console
    """
    for row in table:
        print(row)


# Options for reading a CSV file

def read_csv_file(file_name, file_delimeter):
    """
    Given a CSV file path and a delimiter as strings,
    read the data into a 2D table and return the table
    """
       
    with open(file_name, newline='') as csv_file:       # don't need to explicitly close the file now
        csv_table = []
        csv_reader = csv.reader(csv_file, delimiter=file_delimeter)
        for row in csv_reader:
            csv_table.append(row)
    return csv_table


def csv_delimiter_examples():
    """
    Run some example of reading CSV files using different delimiter options
    """
    number_table = read_csv_file("number_table.csv", " ")
    print_table(number_table)
    print()
    name_table = read_csv_file("name_table.csv", ",")
    print_table(name_table)



csv_delimiter_examples()




# Options for writing a CSV file

def write_csv_file(csv_table, file_name, file_delimiter, quoting_value):
    """
    Given a nested list csv_table, write the data into a
    CSV file with the name file_name
    """
    
    with open(file_name, 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file, delimiter=file_delimiter, quoting=quoting_value)
        for row in csv_table:
            csv_writer.writerow(row)
            
def csv_quoting_examples():
    """
    Run some example of writing 2D tables as CSV files using various quoting options
    """
    name_table = read_csv_file("name_table.csv", ",")
    name_table.append([1, 2, 3])
    write_csv_file(name_table, "name_table_minimal.csv", ",", csv.QUOTE_MINIMAL)
    write_csv_file(name_table, "name_table_all.csv", ",", csv.QUOTE_ALL)
    write_csv_file(name_table, "name_table_nonnumeric.csv", ",", csv.QUOTE_NONNUMERIC)
    #write_csv_file(name_table, "name_table_none.csv", ",", csv.QUOTE_NONE)        # no escapechar is set for lots of quotes

    

#csv_quoting_examples()
