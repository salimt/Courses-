# -*- coding: utf-8 -*-
"""
@author: salimt
"""

"""
A restaurant recommendation system.

Here are some example dictionaries.  These correspond to the information in
restaurants_small.txt.

Restaurant name to rating:
# dict of {str: int}
{'Georgie Porgie': 87,
 'Queen St. Cafe': 82,
 'Dumplings R Us': 71,
 'Mexican Grill': 85,
 'Deep Fried Everything': 52}

Price to list of restaurant names:
# dict of {str, list of str}
{'$': ['Queen St. Cafe', 'Dumplings R Us', 'Deep Fried Everything'],
 '$$': ['Mexican Grill'],
 '$$$': ['Georgie Porgie'],
 '$$$$': []}

Cuisine to list of restaurant names:
# dict of {str, list of str}
{'Canadian': ['Georgie Porgie'],
 'Pub Food': ['Georgie Porgie', 'Deep Fried Everything'],
 'Malaysian': ['Queen St. Cafe'],
 'Thai': ['Queen St. Cafe'],
 'Chinese': ['Dumplings R Us'],
 'Mexican': ['Mexican Grill']}

With this data, for a price of '$' and cuisines of ['Chinese', 'Thai'], we
would produce this list:

    [[82, 'Queen St. Cafe'], [71, 'Dumplings R Us']]
"""

# The file containing the restaurant data.
FILENAME = 'restaurants_small.txt'

#name_to_rating = {}
#price_to_names = {'$': [], '$$': [], '$$$': [], '$$$$': []}
#cuisine_to_names = {}
#
#fo = open(FILENAME,'r').read().split('\n')
#for line in range(0,len(fo),5):
#    name_to_rating[fo[line]] = fo[line+1]
#for line in range(2,len(fo),5):
#    price_to_names[fo[line]] = fo[line-2]
#for line in range(3,len(fo),5):
#    #print(line, "BABSSSSSSSS")
#    for i in fo[line].split(','):
#        cuisine_to_names[i] = fo[line-3]
#
#print (name_to_rating, price_to_names, cuisine_to_names)

def recommend(file, price, cuisines_list):
    """(file open for reading, str, list of str) -> list of [int, str] list

    Find restaurants in file that are priced according to price and that are
    tagged with any of the items in cuisines_list.  Return a list of lists of
    the form [rating%, restaurant name], sorted by rating%.
    """

    # Read the file and build the data structures.
    # - a dict of {restaurant name: rating%}
    # - a dict of {price: list of restaurant names}
    # - a dict of {cusine: list of restaurant names}
    name_to_rating, price_to_names, cuisine_to_names = read_restaurants(file)


    # Look for price or cuisines first?
    # Price: look up the list of restaurant names for the requested price.
    names_matching_price = price_to_names[price]

    # Now we have a list of restaurants in the right price range.
    # Need a new list of restaurants that serve one of the cuisines.
    names_final = filter_by_cuisine(names_matching_price, cuisine_to_names, cuisines_list)

    # Now we have a list of restaurants that are in the right price range and serve the requested cuisine.
    # Need to look at ratings and sort this list.
    result = build_rating_list(name_to_rating, names_final)

    # We're done!  Return that sorted list.
    return result

def build_rating_list(name_to_rating, names_final):
    """ (dict of {str: int}, list of str) -> list of list of [int, str]

    Return a list of [rating%, restaurant name], sorted by rating%

    >>> name_to_rating = {'Georgie Porgie': 87,
     'Queen St. Cafe': 82,
     'Dumplings R Us': 71,
     'Mexican Grill': 85,
     'Deep Fried Everything': 52}
    >>> names = ['Queen St. Cafe', 'Dumplings R Us']
    [[82, 'Queen St. Cafe'], [71, 'Dumplings R Us']]
    """  
    from operator import itemgetter
    final = []
    if len(names_final) == 1:
        return [int(name_to_rating.get(names_final[0])[:-1]), "".join(names_final)]
    for restaurant_name in names_final:
        if restaurant_name in name_to_rating:
            final.append([int(name_to_rating.get(restaurant_name)[:-1]), restaurant_name])
    final.sort(key=itemgetter(0), reverse=True)
    return final

def filter_by_cuisine(names_matching_price, cuisine_to_names, cuisines_list):
    """ (list of str, dict of {str: list of str}, list of str) -> list of str

    >>> names = ['Queen St. Cafe', 'Dumplings R Us', 'Deep Fried Everything']
    >>> cuis = 'Canadian': ['Georgie Porgie'],
     'Pub Food': ['Georgie Porgie', 'Deep Fried Everything'],
     'Malaysian': ['Queen St. Cafe'],
     'Thai': ['Queen St. Cafe'],
     'Chinese': ['Dumplings R Us'],
     'Mexican': ['Mexican Grill']}
    >>> cuisines = ['Chinese', 'Thai']
    >>> filter_by_cuisine(names, cuis, cuisines)
    ['Queen St. Cafe', 'Dumplings R Us']
    """
    restaurant_names = []
    for cuisine, name in cuisine_to_names.items():
        if cuisine in cuisines_list:
            restaurant_names.append(name)
    return restaurant_names

def read_restaurants(file):
    """ (file) -> (dict, dict, dict)

    Return a tuple of three dictionaries based on the information in the file:

    - a dict of {restaurant name: rating%}
    - a dict of {price: list of restaurant names}
    - a dict of {cusine: list of restaurant names}
    """    
    name_to_rating = {}
    price_to_names = {'$': [], '$$': [], '$$$': [], '$$$$': []}
    cuisine_to_names = {}
    
    fo = open(file,'r').read().split('\n')
    for line in range(0,len(fo),5):
        name_to_rating[fo[line]] = fo[line+1]
    for line in range(2,len(fo),5):
        price_to_names[fo[line]] = fo[line-2]
    for line in range(3,len(fo),5):
        #print(line, "BABSSSSSSSS")
        for i in fo[line].split(','):
            cuisine_to_names[i] = fo[line-3]
    
    return (name_to_rating, price_to_names, cuisine_to_names)

print(recommend(FILENAME, '$', ['Chinese', 'Mexican']))
print(recommend(FILENAME, '$', ['Chinese', 'Thai']))
print(recommend(FILENAME, '$$$', 'Canadian'))

