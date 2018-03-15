# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def brute_force_cow_transport(cows,limit=10):
    """
    Finds the allocation of cows that minimizes the number of spaceship trips
    via brute force.  The brute force algorithm should follow the following method:

    1. Enumerate all possible ways that the cows can be divided into separate trips
    2. Select the allocation that minimizes the number of trips without making any trip
        that does not obey the weight limitation
            
    Does not mutate the given dictionary of cows.

    Parameters:
    cows - a dictionary of name (string), weight (int) pairs
    limit - weight limit of the spaceship (an int)
    
    Returns:
    A list of lists, with each inner list containing the names of cows
    transported on a particular trip and the overall list containing all the
    trips
    """
    # TODO: Your code here
    copyCows, minLen, temp = cows.copy(), 10, []
    sorted_x = sorted(copyCows.items(), key=lambda x: x[1], reverse=True) #sort dict as list of tuples
    for item in (get_partitions(sorted_x)):
        for x in item:
           n = len(x)
           aye = (sum(int(x[y][1]) for y in range(n)))
           if n < minLen and aye == limit:
                finalList = []
                for i in item:
                    finalList.append(list(list(zip(*i))[0]))
                return finalList
                minLen = n
           
           elif n < minLen and aye <= limit:
                if len(temp)-1 <= len(item) and (x not in temp):
                    temp.append(x)
                    if len(temp)-1 == len(item):
                        finalList = []
                        for i in temp:                           
                            finalList.append(list(list(zip(*i))[0]))
    return finalList



#print(brute_force_cow_transport({'Milkshake': 40, 'MooMoo': 50, 'Lotus': 40, \
#                                 'Miss Bella': 25, 'Horns': 25, 'Boo': 20}, 100))
#[Out]: [['MooMoo', 'Horns', 'Miss Bella'], ['Milkshake', 'Lotus', 'Boo']]
#print(brute_force_cow_transport({'Betsy': 65, 'Buttercup': 72, 'Daisy': 50}, 75))
#[Out]: [['Buttercup'], ['Daisy'], ['Betsy']]