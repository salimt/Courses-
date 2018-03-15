###########################
# 6.00.2x Problem Set 1: Space Cows 

from ps1_partition import get_partitions
import time

#================================
# Part A: Transporting Space Cows
#================================

def load_cows(filename):
    """
    Read the contents of the given file.  Assumes the file contents contain
    data in the form of comma-separated cow name, weight pairs, and return a
    dictionary containing cow names as keys and corresponding weights as values.

    Parameters:
    filename - the name of the data file as a string

    Returns:
    a dictionary of cow name (string), weight (int) pairs
    """

    cow_dict = dict()

    f = open(filename, 'r')
    
    for line in f:
        line_data = line.split(',')
        cow_dict[line_data[0]] = int(line_data[1])
    return cow_dict


# Problem 1
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


# Problem 2
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

        
# Problem 3
def compare_cow_transport_algorithms():
    """
    Using the data from ps1_cow_data.txt and the specified weight limit, run your
    greedy_cow_transport and brute_force_cow_transport functions here. Use the
    default weight limits of 10 for both greedy_cow_transport and
    brute_force_cow_transport.
    
    Print out the number of trips returned by each method, and how long each
    method takes to run in seconds.

    Returns:
    Does not return anything.
    """
    # TODO: Your code here
    start = time.time()
    cows = load_cows("ps1_cow_data.txt")
    print(greedy_cow_transport(cows, 10))
    end = time.time()
    print(end - start)
   
    start = time.time()
    cows = load_cows("ps1_cow_data.txt")
    print(brute_force_cow_transport(cows, 10))
    end = time.time()
    print(end - start)
    
#print(compare_cow_transport_algorithms())

"""
Here is some test data for you to see the results of your algorithms with. 
Do not submit this along with any of your answers. Uncomment the last two
lines to print the result of your problem.
"""

cows = load_cows("ps1_cow_data.txt")
#limit=100
#print(cows)
#print(greedy_cow_transport(cows, 10))
#print(brute_force_cow_transport(cows, limit))

