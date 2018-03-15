# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def greedy_cow_transport(cows,limit=10):
    """
    Uses a greedy heuristic to determine an allocation of cows that attempts to
    minimize the number of spaceship trips needed to transport all the cows. The
    returned allocation of cows may or may not be optimal.
    The greedy heuristic should follow the following method:

    1. As long as the current trip can fit another cow, add the largest cow that will fit
        to the trip
    2. Once the trip is full, begin a new trip to transport the remaining cows

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
    cowsFitted, copyCows = [], cows.copy()
    sorted_x = sorted(copyCows.items(), key=lambda x: x[1]) #sort dict as list of tuples
    f = len(sorted_x)
    while f != 0:
        total = limit
        tempList = []
        for w in sorted_x[::-1]: #
            indx = sorted_x.index(w)            #getting the index of the element in list
            if w[1] <= total:                   #checks if there is space
                total -= w[1]                   #subtract the weight of cow from the total pool
                tempList.append(w[0])           #append cow's name into list
                sorted_x.remove(sorted_x[indx]) #remove the cow from the list
        if tempList:
            cowsFitted.append(tempList)
        f -= 1
    return cowsFitted
                
#print(greedy_cow_transport({'Betsy': 65, 'Buttercup': 72, 'Willow': 35, 'Rose': 50, \
#                            'Abby': 38, 'Coco': 10, 'Lilly': 24, 'Patches': 12, \
#                            'Daisy': 50, 'Dottie': 85}, 100))
#[Out]: [['Dottie', 'Patches'], ['Buttercup', 'Lilly'], ['Betsy', 'Willow'], \
#                               ['Daisy', 'Rose'], ['Abby', 'Coco']]