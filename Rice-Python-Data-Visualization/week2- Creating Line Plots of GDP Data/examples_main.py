"""
Example of importing my own modules.
"""

import random
import examples3_utils as utils

def most_ones(seq0, seq1):
    """
    Determine which sequence has more number 1's in it.
    Returns 0 if it is seq0 and 1 if it is seq1.
    """
    cnt0 = len(utils.indices(seq0, 1))
    cnt1 = len(utils.indices(seq1, 1))
    if cnt0 >= cnt1:
        return 0
    else:
        return 1

print(most_ones(range(42), [0, 1, 2, 3, 2, 1, 0]))
print(most_ones([1] * 5, [1, 2, 1, 2, 1, 2, 1]))

def random_unique(minnum, maxnum):
    """
    Return a list with random values between minnum and
    maxnum with no duplicates.
    """
    lst = [random.randint(minnum, maxnum) for num in range(50)]
    result = utils.remove_dups(lst)
    return result

print(random_unique(0, 5))
print(random_unique(-3, 14))
