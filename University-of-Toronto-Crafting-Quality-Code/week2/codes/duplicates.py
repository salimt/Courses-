def remove_shared(L1, L2):
    """ (list list)

    Remove items from L1 that are in both L1 and L2.

    >>> list_1 = [1, 2, 3, 4, 5, 6]
    >>> list_2 = [2, 4, 5, 7]
    >>> remove_shared(list_1, list_2)
    >>> list_1
    [1, 3, 6]
    >>> list_2
    [2, 4, 5, 7]
    """

    for v in L2:
        if v in L1:
            L1.remove(v)


if __name__ == '__main__':
    import doctest
    doctest.testmod()
