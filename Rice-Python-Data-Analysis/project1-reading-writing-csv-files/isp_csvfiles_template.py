# -*- coding: utf-8 -*-
"""
@author: salimt


Project for Week 3 of "Python Data Analysis".
Read and write CSV files using a dictionary of dictionaries.

Be sure to read the project description page for further information
about the expected behavior of the program.
"""

import csv

def read_csv_fieldnames(filename, separator, quote):
    """
    Inputs:
      filename  - name of CSV file
      separator - character that separates fields
      quote     - character used to optionally quote fields
    Ouput:
      A list of strings corresponding to the field names in
      the given CSV file.
    """
    
    with open(filename, newline='') as csvfile:
              
        reader = csv.DictReader(csvfile,
                                delimiter=separator,
                                quotechar=quote)
        
        return reader.fieldnames
                   
#print(read_csv_fieldnames("table1.csv", ",", ";"))

def read_csv_as_list_dict(filename, separator, quote):
    """
    Inputs:
      filename  - name of CSV file
      separator - character that separates fields
      quote     - character used to optionally quote fields
    Output:
      Returns a list of dictionaries where each item in the list
      corresponds to a row in the CSV file.  The dictionaries in the
      list map the field names to the field values for that row.
    """   
    
    list_of_values = []
    
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile,
                                delimiter=separator,
                                quotechar=quote)
        for row in reader:
            values = {}
            for name in row:
                values[name] = row[name]
            list_of_values.append(values)
            
    return list_of_values               


#print(read_csv_as_list_dict("table1.csv", ",", ";"))

def read_csv_as_nested_dict(filename, keyfield, separator, quote):
    """
    Inputs:
      filename  - name of CSV file
      keyfield  - field to use as key for rows
      separator - character that separates fields
      quote     - character used to optionally quote fields
    Output:
      Returns a dictionary of dictionaries where the outer dictionary
      maps the value in the key_field to the corresponding row in the
      CSV file.  The inner dictionaries map the field names to the
      field values for that row.
    """
    
    dict_of_values = {}
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile,
                                delimiter=separator,
                                quotechar=quote)
        for row in reader:
            temp_dict = {}
            for name in row:
                temp_dict[name] = row[name]
            dict_of_values[row[keyfield]] = temp_dict
            
    return dict_of_values

#print(read_csv_as_nested_dict('table1.csv', 'Field1', ',', '"'))

def write_csv_from_list_dict(filename, table, fieldnames, separator, quote):
    """
    Inputs:
      filename   - name of CSV file
      table      - list of dictionaries containing the table to write
      fieldnames - list of strings corresponding to the field names in order
      separator  - character that separates fields
      quote      - character used to optionally quote fields
    Output:
      Writes the table to a CSV file with the name filename, using the
      given fieldnames.  The CSV file should use the given separator and
      quote characters.  All non-numeric fields will be quoted.
    """
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames,
                                         delimiter=separator,
                                         quotechar=quote,
                                         quoting=csv.QUOTE_NONNUMERIC)        
        writer.writeheader()
        for value in table:
            writer.writerow({fieldnames[0]: value[fieldnames[0]],
                             fieldnames[1]: value[fieldnames[1]],
                             fieldnames[2]: value[fieldnames[2]],
                             fieldnames[3]: value[fieldnames[3]],
                             fieldnames[4]: value[fieldnames[4]]})       

#print(write_csv_from_list_dict('output1.csv', [{'a': 10, 'c': 12, 'b': 11, 'd': 13, 'e': 14}, \
#                                               {'a': 20, 'c': 22, 'b': 21, 'd': 23, 'e': 24}, \
#                                               {'a': 30, 'c': 32, 'b': 31, 'd': 33, 'e': 34}, \
#                                               {'a': 40, 'c': 42, 'b': 41, 'd': 43, 'e': 44}], \
#                                              ['a', 'b', 'c', 'd', 'e'], ',', '"'))