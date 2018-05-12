"""
Example code for printing the contents of a dictionary to the console
"""


NAME_DICT = {"Warren" : "Joe", "Rixner" : "Scott", "Greiner" : "John"}

def run_dict_methods():
    """
    Run some simple examples of calls to dictionary methods
    """
    
    # Note that these methods return an iterable object (similar to range())
    print(NAME_DICT.keys())
    print(NAME_DICT.values())
    print(NAME_DICT.items())
    print()
    
    # These objects can be converted to lists
    print(list(NAME_DICT.keys()))
    print(list(NAME_DICT.values()))
    print(list(NAME_DICT.items()))

run_dict_methods()




def print_dict_keys(my_dict):
    """
    Print the contents of a dictionary to the console
    in a readable form using the keys() method
    """
    print("Printing dictionary", my_dict, "in readable form")
    for key in my_dict:                                # note my_dict.keys() works here too
        print("Key =", key, "has value =", my_dict[key])
        
        
def print_dict_items(my_dict):
    """
    Print the contents of a dictionary to the console
    in a readable form using the items() method
    """
    print("Printing dictionary", my_dict, "in readable form")
    for (key, value) in my_dict.items():
        print("Key =", key, "has value =", value)


def run_print_dict_examples():
    """
    Run some examples of printing dictionaries to the console
    """
    print()
    print_dict_keys(NAME_DICT)
    print()
    print_dict_items(NAME_DICT)
    
#run_print_dict_examples()