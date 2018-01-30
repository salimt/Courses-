# Do not import any modules. If you do, the tester may reject your submission.

# Constants for the contents of the maze.

# The visual representation of a wall.
WALL = '#'

# The visual representation of a hallway.
HALL = '.'

# The visual representation of a brussels sprout.
SPROUT = '@'

# Constants for the directions. Use these to make Rats move.

# The left direction.
LEFT = -1

# The right direction.
RIGHT = 1

# No change in direction.
NO_CHANGE = 0

# The up direction.
UP = -1

# The down direction.
DOWN = 1

# The letters for rat_1 and rat_2 in the maze.
RAT_1_CHAR = 'J'
RAT_2_CHAR = 'P'


class Rat:
    """ A rat caught in a maze. """
    # Write your Rat methods here.
    def __init__(self, symbol, row, col):
        """(Rat, str, int, int) -> NoneType
    
        This function creates rat in given position as x and y.
        
        >>> Rat('P', 1, 4)
        """
        self.symbol = symbol
        self.row = row
        self.col = col
        self.num_sprouts_eaten = 0
    
    def set_location(self, row, col):
        """(Rat, int, int) -> NoneType
        
        The first parameter represents a rat, the second represents a row, 
        and the third represents a column. Set the rat's row and col instance 
        variables to the given row and column.
        """
        self.row += row
        self.col += col
        
    def eat_sprout(self):
        """(Rat) -> NoneType
        
        The first parameter represents a rat. 
        Add one to the rat's instance variable num_sprouts_eaten. Yuck.
        """
        self.num_sprouts_eaten += 1
        
    def __str__(self):
        """(Rat) -> str
        
        The parameter represents a rat. Return a string representation of the rat, in this format:
        symbol at (row, col) ate num_sprouts_eaten sprouts.
        """
        return '{0} at ({1}, {2}) ate {3} sprouts.' \
                        .format(self.symbol, self.row, self.col, self.num_sprouts_eaten)

class Maze:
    """ A 2D maze. """
    # Write your Maze methods here.
    def __init__(self, maze, rat_1, rat_2):
        """(Maze, list of list of str, Rat, Rat) -> NoneType
        """
        self.maze = maze
        self.rat_1 = rat_1
        self.rat_2 = rat_2
        self.num_sprouts_left = sum(sprouts.count('@') for sprouts in maze)
        
        
        self.maze[self.rat_1.row][self.rat_1.col] = self.rat_1.symbol
        self.maze[self.rat_2.row][self.rat_2.col] = self.rat_2.symbol
        
    def is_wall(self, row, column):
        """(Maze, int, int) -> bool
        
        The first parameter represents a maze, the second represents a row, and 
        the third represents a column.
        Return True if and only if there is a wall at the given row and column of the maze.
        
        >>> cr2 = Maze([['#', '#', '#', '#', '#', '#', '#'], 
                        ['#', '.', '.', '.', '.', '.', '#'], 
                        ['#', '.', '#', '#', '#', '.', '#'], 
                        ['#', '.', '.', '@', '#', '.', '#'], 
                        ['#', '@', '#', '.', '@', '.', '#'], 
                        ['#', '#', '#', '#', '#', '#', '#']], 
                        Rat('J', 1, 1),
                        Rat('P', 1, 4))
        >>> Maze.is_wall(cr2, 0, 0)
        True
        >>> Maze.is_wall(cr2, 1, 2)
        False
        """
        #print(self.maze[row][column])
        return self.maze[row][column] == WALL
        
    def get_character(self, row, column):
        """(Maze, int, int) -> str
        
        The first parameter represents a maze, the second represents a row, and 
        the third represents a column.

        Return the character in the maze at the given row and column.
        If there is a rat at that location, then its character should be returned rather than HALL.
        """
        #print(Rat(self, 1, 2))
        
        assert 0 <= row < len(self.maze), \
           'row not in the maze.'

        assert 0 <= column < len(self.maze[0]), \
           'col not in the maze.'

        return self.maze[row][column]
        

    def move(self, rat, row, column):
        """(Maze, Rat, int, int) -> bool
        
        The first parameter represents a maze, the second represents a rat, 
        the third represents a vertical direction change (UP, NO_CHANGE or DOWN), 
        and the fourth represents a horizontal direction change (LEFT, NO_CHANGE or RIGHT).
        """
        if not self.is_wall(rat.row + row, rat.col + column):               
            self.maze[rat.row][rat.col] = HALL    
            Rat.set_location(rat, row, column)
            if self.maze[rat.row][rat.col] == SPROUT:
                self.num_sprouts_left -= 1
                rat.eat_sprout()
            
            self.maze[self.rat_1.row][self.rat_1.col] = self.rat_1.symbol
            self.maze[self.rat_2.row][self.rat_2.col] = self.rat_2.symbol
            
        return False

    def __str__(self):
        """(Maze) -> str
        
        The parameter represents a maze. Return a string representation of the maze.
        

        >>> maze = Maze([['#', '#', '#', '#', '#', '#', '#'], \
                         ['#', '.', '.', '.', '.', '.', '#'], \
                         ['#', '.', '#', '#', '#', '.', '#'], \
                         ['#', '.', '.', '@', '#', '.', '#'], \
                         ['#', '@', '#', '.', '@', '.', '#'], \
                         ['#', '#', '#', '#', '#', '#', '#']], \
                        Rat('J', 1, 1), \
                        Rat('P', 1, 4))
        >>> print(maze)
        #######
        #J..P.#
        #.###.#
        #..@#.#
        #@#.@.#
        #######
        J at (1, 1) ate 0 sprouts.
        P at (1, 4) ate 0 sprouts.
        """
        #return str([print(*i) for i in self.maze])
        return '\n'.join(''.join(*zip(*row)) for row in self.maze) + '\n' + \
                                str(self.rat_1) + '\n' + str(self.rat_2)
                                
                         