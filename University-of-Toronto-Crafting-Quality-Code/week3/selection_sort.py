def get_index_of_smallest(L, i):
    """ (list, int) -> int

    Return the index of the smallest item in L[i:].

    >>> get_index_of_smallest([2, 7, 3, 5], 1)
    2
    """

    # The index of the smallest item so far.
    index_of_smallest = i

    for j in range(i + 1, len(L)):
        if L[j] < L[index_of_smallest]:
            index_of_smallest = j

    return index_of_smallest

    
def selection_sort(L):
    """ (list) -> NoneType

    Sort the items of L from smallest to largest.

    >>> L = [3, 7, 2, 5]
    >>> selection_sort(L)
    >>> L
    [2, 3, 5, 7]
    """

    
    for i in range(len(L)):

        # Find the index of the smallest item in L[i:] and swap that
        # item with the item at index i.

        index_of_smallest = get_index_of_smallest(L, i)
        L[index_of_smallest], L[i] = L[i], L[index_of_smallest]



if __name__ == '__main__':
    import doctest
    doctest.testmod()
