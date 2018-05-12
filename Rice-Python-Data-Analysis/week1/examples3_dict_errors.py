"""
Example code for working with dictionary keys
"""

# Three example of dictionaries - note that dictionary keys in Python must be immutable
simple_dict = {"Joe" : 1, "Scott" : 2, "John" : 3}
##print(simple_dict)

#bad_dict = {["Joe", "Warren"] : 1, ["Scott", "Rixner"] : 2, ["John", "Greiner"] : 3}
#print(bad_dict)

good_dict = {("Joe", "Warren") : 1, ("Scott", "Rixner") : 2, ("John", "Greiner") : 3}
#print(good_dict)


# Examples of dictionary lookup
#print(simple_dict["Joe"])
#print(simple_dict["Scott"])
#print(simple_dict["Stephen"])
#print(good_dict[("Joe", "Warren")])
#print(good_dict[("John", "Greiner")])




# Custom code for looking up keys that may not always be present

def lookup(my_dict, my_key, default_value=None):
    """
    Given dictionary my_dict and key my_key, 
    return my_dict[my_key] if my_key is in my_dict
    otherwise return default_value
    """
    if my_key in my_dict:
        return my_dict[my_key]
    else:
        return default_value

#simple_dict = {"Joe" : 1, "Scott" : 2, "John" : 3}
#print(lookup(simple_dict, "Joe", -1))
#print(lookup(simple_dict, "Stephen", -1))
#print(lookup(simple_dict, "Stephen"))







# Built-in Python dictionary method get() in place of lookup()
#simple_dict = {"Joe" : 1, "Scott" : 2, "John" : 3}
#print(simple_dict.get("Joe", -1))
#print(simple_dict.get("Stephen", -1))
#print(simple_dict.get("Stephen"))		# default value if parameter is omitted is None

# Note that we can acheive the same effect in lookup() 
# via default parameter definition of the form "default_value = None"


