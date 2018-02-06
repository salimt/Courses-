def num_buses(n):
    """ (int) -> int

    Precondition: n >= 0

    Return the minimum number of buses required to transport n people.
    Each bus can hold 50 people.

    >>> num_buses(75)
    2
    """
    import math
    if n < 0: return False #raise ValueError ('Invalid Value')
    return math.ceil(n/50)

#print(num_buses(100), num_buses(101), num_buses(75))


def stock_price_summary(price_changes):
    """ (list of number) -> (number, number) tuple

    price_changes contains a list of stock price changes. Return a 2-item
    tuple where the first item is the sum of the gains in price_changes and
    the second is the sum of the losses in price_changes.

    >>> stock_price_summary([0.01, 0.03, -0.02, -0.14, 0, 0, 0.10, -0.01])
    (0.14, -0.17)
    """
    pos = sum([x for x in price_changes if x > 0])
    neg = sum([x for x in price_changes if x < 0])
    return round(pos, 2), round(neg, 2)
 
#print(stock_price_summary([0.01, 0.03, -0.02, -0.14, 0, 0, 0.10, -0.01]))


def swap_k(L, k):
    """ (list, int) -> NoneType

    Precondtion: 0 <= k <= len(L) // 2

    Swap the first k items of L with the last k items of L.

    >>> nums = [1, 2, 3, 4, 5, 6]
    >>> swap_k(nums, 2)
    >>> nums
    [5, 6, 3, 4, 1, 2]
    """
    if len(L) == 0 or len(L) == 1: return L
    for i in range(k):
        L[i], L[i-k] = L[i-k], L[i]
    return L
        
#print(swap_k([1,2] ,1))
    
#print(swap_k([1, 2, 3, 4, 5, 6], 2))

if __name__ == '__main__':
    import doctest
    doctest.testmod()