def linear_search(L, v):
    """ (list, object) -> int

    Return the index of the first occurrence of v in L, or
    return -1 if v is not in L.

    >>> linear_search([2, 3, 5, 3], 2)
    0
    >>> linear_search([2, 3, 5, 3], 5)
    2
    >>> linear_search([2, 3, 5, 3], 8)
    -1
    """

    i = 0

    while i != len(L) and v != L[i]:
        i = i + 1

    if i == len(L):
        return -1
    else:
        return i
