# -*- coding: utf-8 -*-
"""
@author: salimt
"""

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