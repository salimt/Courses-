"""
Frequently used utility functions.
"""

def indices(iterable, elem):
    """
    Inputs:
      iterable - Any iterable
      elem     - Element to look for

    Output:
      A list of indices at which elem occurs in iterable.
      The list will be empty if elem never occurs.
    """
    result = []
    for idx, item in enumerate(iterable):
        if item == elem:
            result.append(idx)
    return result

def remove_dups(lst):
    """
    Inputs:
      lst - A list object

    Outputs:
      Returns a new list with the same elements in lst,
      in the same order, but without duplicates.  Only
      the first element of each replicated element will
      appear in the output.
    """
    result = []
    dups = set()
    for item in lst:
        if item not in dups:
            result.append(item)
            dups.add(item)
    return result
