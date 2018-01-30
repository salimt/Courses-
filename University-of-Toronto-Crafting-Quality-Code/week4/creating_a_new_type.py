class CashRegister:
    """A cash register."""

    def __init__(self, loonies, toonies, fives, tens, twenties):
        """ (CashRegister, int, int, int, int, int) -> NoneType

        A CashRegister with loonies, toonies, fives, tens, and twenties.
        
        >>> register = CashRegister(5, 5, 5, 5, 5)
        >>> register.loonies
        5
        >>> register.toonies
        5
        >>> register.fives
        5
        >>> register.tens
        5
        >>> register.twenties
        5
        """

        self.loonies = loonies
        self.toonies = toonies
        self.fives = fives
        self.tens = tens
        self.twenties = twenties

    def get_total(self):
        """ (CashRegister) -> int

        Return the total amount of cash in the register.
        
        >>> register = CashRegister(5, 5, 5, 5, 5)
        >>> register.get_total()
        190
        """

        return self.loonies + self.toonies * 2 + self.fives * 5 + \
               self.tens * 10 + self.twenties * 20
    
    def add(self, count, denomination):
        """ (CashRegister, int, str) -> NoneType

        Add count items of denomination to the register.
        denomination is one of 'loonies', 'toonies',
        'fives', 'tens', and 'twenties'.
        
        >>> register = CashRegister(5, 5, 5, 5, 5)
        >>> register.add(2, 'toonies')
        >>> register.toonies
        7
        >>> register.add(1, 'tens')
        >>> register.tens
        6
        """

        if denomination == 'loonies':
            self.loonies += count
        elif denomination == 'toonies':
            self.toonies += count
        elif denomination == 'fives':
            self.fives += count
        elif denomination == 'tens':
            self.tens += count
        elif denomination == 'twenties':
            self.twenties += count


    def remove(self, count, denomination):
        """ (CashRegister, int, str) -> NoneType

        Remove count items of denomination from the register.
        denomination is one of 'loonies', 'toonies',
        'fives', 'tens', and 'twenties'.
        
        >>> register = CashRegister(5, 5, 5, 5, 5)
        >>> register.remove(2, 'toonies')
        >>> register.toonies
        3
        >>> register.remove(1, 'tens')
        >>> register.tens
        4
        """

        if denomination == 'loonies':
            self.loonies -= count
        elif denomination == 'toonies':
            self.toonies -= count
        elif denomination == 'fives':
            self.fives -= count
        elif denomination == 'tens':
            self.tens -= count
        elif denomination == 'twenties':
            self.twenties -= count


if __name__ == '__main__':
    # A cash register with 5 loonies, 5 toonies, 5 fives, 5 tens, and 5 twenties,
    # for a total of $190.
    register = CashRegister(5, 5, 5, 5, 5)
    print(register.get_total())

    register.add(3, 'toonies')
    register.remove(2, 'twenties')

    print(register.get_total())
