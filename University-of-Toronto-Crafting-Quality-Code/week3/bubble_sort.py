def bubble_sort(L):
    """ (list) -> NoneType

    Sort the items of L from smallest to largest.

    >>> L = [7, 3, 5, 2]
    >>> bubble_sort(L)
    >>> L
    [2, 3, 5, 7]
    """

    # The index of the last unsorted item.
    end = len(L) - 1

    while end != 0:

        # Bubble once through the unsorted section to move the largest item
        # to index end.
        for i in range(end):
            if L[i] > L[i + 1]:
                L[i], L[i + 1] = L[i + 1], L[i]

        end = end - 1

    
if __name__ == '__main__':
    import doctest
    doctest.testmod()
