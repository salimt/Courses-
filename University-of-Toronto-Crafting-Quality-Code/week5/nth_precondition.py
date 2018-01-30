def every_nth(L, n=1):
    """ (list, int) -> list

    Precondition: 0 <= n < len(L)

    Return a list containing every nth item of L,
    starting at index 0.
    
    >>> every_nth([1, 2, 3, 4, 5, 6], n=2)
    [1, 3, 5]
    >>> every_nth([1, 2, 3, 4, 5, 6], 3)
    [1, 4]
    >>> every_nth([1, 2, 3, 4, 5, 6])
    [1, 2, 3, 4, 5, 6]
    """

    assert 0 <= n < len(L), \
           '{} is out of range.'.format(n)
    
    result = []

    for i in range(0, len(L), n):
        result.append(L[i])

    return result


if __name__ == '__main__':
    import doctest
    doctest.testmod()
