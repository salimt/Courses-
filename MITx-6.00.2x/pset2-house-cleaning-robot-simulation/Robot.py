# -*- coding: utf-8 -*-
"""
@author: salimt
"""

# === Problem 2
class Robot(object):
    """
    Represents a robot cleaning a particular room.

    At all times the robot has a particular position and direction in the room.
    The robot also has a fixed speed.

    Subclasses of Robot should provide movement strategies by implementing
    updatePositionAndClean(), which simulates a single time-step.
    """
    def __init__(self, room, speed):
        """(self, RectangularRoom, float) -> NoneType
        
        Initializes a Robot with the given speed in the specified room. The
        robot initially has a random direction and a random position in the
        room. The robot cleans the tile it is on.

        room:  a RectangularRoom object.
        speed: a float (speed > 0)
        """
        self.room = room
        self.speed = speed
        self.robotPos = Position(0,0)
        self.robotDir = 0
        self.room.cleanTileAtPosition(self.robotPos) #clean the initial tile
        
    def getRobotPosition(self):
        """(self) -> (num, num)
        
        Return the position of the robot.

        returns: a Position object giving the robot's position.
        """
        
        if self.robotPos == (0,0):
            self.robotPos = self.room.getRandomPosition()
            return self.robotPos
        else:
            return self.robotPos        
        
    def getRobotDirection(self):
        """(self) -> int
        
        Return the direction of the robot.

        returns: an integer d giving the direction of the robot as an angle in
        degrees, 0 <= d < 360.   
        """
        if self.robotDir == 0:
            self.robotDir = int(360 * random.uniform(0, 1))
            return self.robotDir
        else:
            return self.robotDir

    def setRobotPosition(self, position):
        """(self, (num, num)) -> NoneType
        
        Set the position of the robot to POSITION.

        position: a Position object.

        >>> robot = Robot(RectangularRoom(5,8), 1.0)
        >>> robot.setRobotPosition((3,4))
        >>> robot.getRobotPosition()
        (3, 4)
        
        """
        if self.room.isPositionInRoom(position):
            self.robotPos = position

    def setRobotDirection(self, direction):
        """(self, (num, num)) -> NoneType
        
        Set the direction of the robot to DIRECTION.

        direction: integer representing an angle in degrees        
        """
        self.robotDir = direction

    def updatePositionAndClean(self):
        """
        Simulate the passage of a single time-step.

        Move the robot to a new position and mark the tile it is on as having
        been cleaned.
        """
        raise NotImplementedError # don't change this!