# -*- coding: utf-8 -*-
"""
@author: salimt
"""

# === Problem 1
class RectangularRoom(object):
    """
    A RectangularRoom represents a rectangular region containing clean or dirty
    tiles.

    A room has a width and a height and contains (width * height) tiles. At any
    particular time, each of these tiles is either clean or dirty.
    """
    def __init__(self, width, height):
        """
        Initializes a rectangular room with the specified width and height.

        Initially, no tiles in the room have been cleaned.

        width: an integer > 0
        height: an integer > 0
        """
        self.width = width
        self.height = height
        self.cleanedTiles = 0
        self.cleanedPositions = []
    
    def cleanTileAtPosition(self, pos):
        """(self, (number, number)) -> (number, number)
        
        Mark the tile under the position POS as cleaned.

        Assumes that POS represents a valid position inside this room.

        pos: a Position
        >>> room = RectangularRoom(5, 5)
        >>> f = room.cleanTileAtPosition(Position(0.6, 0.3))
        """
        currentPos = (int(Position.getX(pos)), int(Position.getY(pos)))
#        print(RectangularRoom.isPositionInRoom(self, pos), "is it there")
        if (not currentPos in self.cleanedPositions) and RectangularRoom.isPositionInRoom(self, pos):
            self.cleanedPositions.append(currentPos)
            self.cleanedTiles += 1
        
    def isTileCleaned(self, m, n=-1):
        """(self, (number, number), [number]) -> bool
        Return True if the tile (m, n) has been cleaned.

        Assumes that (m, n) represents a valid tile inside the room.

        m: an integer
        n: an integer
        returns: True if (m, n) is cleaned, False otherwise
        
        >>> room = RectangularRoom(5, 5)
        >>> room.cleanTileAtPosition(Position(0.6, 3.3))
        >>> room.isTileCleaned((0, 3))
        True
        >>> room.isTileCleaned(0,3)
        True
        """
        if n == -1:
            for i in self.cleanedPositions:
                if m == i:
                    return True
            return False
        else:
            pos = (m,n)
            for i in self.cleanedPositions:
                if pos == i:
                    return True
            return False
    
    def getNumTiles(self):
        """(self) -> int
        
        Return the total number of tiles in the room.

        returns: an integer
        """
        return self.width * self.height

    def getNumCleanedTiles(self):
        """(self, None) -> Integer
        
        Return the total number of clean tiles in the room.

        returns: an integer
        """
        return self.cleanedTiles

    def getRandomPosition(self):
        """(self, None) -> Boolean
        
        Return a random position inside the room.

        returns: a Position object.
        """
        x, y = 0,0
        for i in range(1):
#            x = self.width * random.random()
#            y = self.width * random.random()
            x = round(random.uniform(0, self.width*random.random()), 1)
            y = round(random.uniform(0, self.height*random.random()), 1)
        return Position(x,y)

    def isPositionInRoom(self, pos):
        """(self, (number, number)) -> Boolean
        
        Return True if pos is inside the room.

        pos: a Position object.
        returns: True if pos is in the room, False otherwise.
        
        >>> room = RectangularRoom(5, 5)
        >>> room.isPositionInRoom((2.49, 4.99))
        True
        >>> room.isPositionInRoom((0.0, 5.0))
        False
        """ 
        if type(pos) == tuple:
#            print((self.width, self.height), (pos[0], pos[1]))
            x, y = pos[0], pos[1]
            if 0 <= x < self.width and 0 <= y < self.height:
                return True
        else:
            if 0 <= Position.getX(pos) < self.width and 0 <= Position.getY(pos) < self.height:
                return True
        return False   