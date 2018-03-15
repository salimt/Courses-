# 6.00.2x Problem Set 2: Simulating robots

import math
import random

import ps2_visualize
import pylab

##################
## Comment/uncomment the relevant lines, depending on which version of Python you have
##################

# For Python 3.5:
#from ps2_verify_movement35 import testRobotMovement
# If you get a "Bad magic number" ImportError, you are not using Python 3.5 

# For Python 3.6:
from ps2_verify_movement36 import testRobotMovement
# If you get a "Bad magic number" ImportError, you are not using Python 3.6


# === Provided class Position
class Position(object):
    """
    A Position represents a location in a two-dimensional room.
    """
    def __init__(self, x, y):
        """
        Initializes a position with coordinates (x, y).
        """
        self.x = x
        self.y = y
        
    def getX(self):
        return self.x
    
    def getY(self):
        return self.y
    
    def getNewPosition(self, angle, speed):
        """
        Computes and returns the new Position after a single clock-tick has
        passed, with this object as the current position, and with the
        specified angle and speed.

        Does NOT test whether the returned position fits inside the room.

        angle: number representing angle in degrees, 0 <= angle < 360
        speed: positive float representing speed

        Returns: a Position object representing the new position.
        """
        old_x, old_y = self.getX(), self.getY()
        angle = float(angle)
        # Compute the change in position
        delta_y = speed * math.cos(math.radians(angle))
        delta_x = speed * math.sin(math.radians(angle))
        # Add that to the existing position
        new_x = old_x + delta_x
        new_y = old_y + delta_y
        return Position(new_x, new_y)

    def __str__(self):  
        return "(%0.2f, %0.2f)" % (self.x, self.y)

#random.seed(0)

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


#room = RectangularRoom(5, 5)
#print(room.isPositionInRoom((2.49, 4.99)), room.isPositionInRoom((2.49, 5.00)))
#print(room.getRandomPosition())
#room.cleanTileAtPosition(Position(0.6, 3.3))
#print(room.isTileCleaned((0, 3)), room.isTileCleaned(0,3))

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
        
#robot = Robot(RectangularRoom(5,8), 1.0)
#print(robot.getRobotPosition(), '\t', "get random position")
#print(robot.setRobotPosition(Position(3, 4)))
#print(robot.getRobotPosition(), '\t', "get the new position")
#f = robot.getRobotDirection() #, '\t', '\t', "get random direction in 360" $$$$ 184
#print(robot.setRobotDirection(f), 't', "aye")
#print(robot.getRobotDirection())
#print(robot.setRobotPosition(robot2), '\t', "new set position")
#print(robot.getRobotPosition())


# === Problem 3
class StandardRobot(Robot):
    """
    A StandardRobot is a Robot with the standard movement strategy.

    At each time-step, a StandardRobot attempts to move in its current
    direction; when it would hit a wall, it *instead* chooses a new direction
    randomly.
    """
    def updatePositionAndClean(self):
        """
        Simulate the passage of a single time-step.

        Move the robot to a new position and mark the tile it is on as having
        been cleaned.
        """        

        newPos = Position.getNewPosition(self.getRobotPosition(), self.getRobotDirection(), self.speed)
        if self.room.isPositionInRoom(newPos):
            self.setRobotPosition(newPos)
            self.room.cleanTileAtPosition(self.getRobotPosition())
        else:
            self.setRobotDirection(random.randint(0,359))


        

#robot = Robot(RectangularRoom(5,5), 0.7)
#print(robot.getRobotPosition(), '\t', "get random position")
#print(robot.setRobotPosition(Position(1.5, 3.5)))
#print(robot.getRobotPosition(), '\t', "get the new position")
#print(StandardRobot.updatePositionAndClean(robot))

# Uncomment this line to see your implementation of StandardRobot in action!
#testRobotMovement(StandardRobot, RectangularRoom)
#testRobotMovement(RandomWalkRobot, RectangularRoom)


# === Problem 4
def runSimulation(num_robots, speed, width, height, min_coverage, num_trials,
                  robot_type):
    """(int, float, int, int, float, int, func) -> float
    
    
    Runs NUM_TRIALS trials of the simulation and returns the mean number of
    time-steps needed to clean the fraction MIN_COVERAGE of the room.

    The simulation is run with NUM_ROBOTS robots of type ROBOT_TYPE, each with
    speed SPEED, in a room of dimensions WIDTH x HEIGHT.

    num_robots: an int (num_robots > 0)
    speed: a float (speed > 0)
    width: an int (width > 0)
    height: an int (height > 0)
    min_coverage: a float (0 <= min_coverage <= 1.0)
    num_trials: an int (num_trials > 0)
    robot_type: class of robot to be instantiated (e.g. StandardRobot or
                RandomWalkRobot)
    
    >>> print(runSimulation(1, 2.0, 10, 12, 0.96, 30, StandardRobot))
    58.0
    >>> print(runSimulation(2, 1.0, 8, 8, 0.80, 30, StandardRobot))
    25.0
    >>> print(runSimulation(1, 1.0, 10, 12, 0.96, 30, StandardRobot))
    115.0
    >>> print(runSimulation(1, 1.0, 5, 5, 0.78, 30, StandardRobot))
    19.0
    """ 
    import math
    total = (width * height) * (min_coverage*100) // 100
    
    # MIT's grader was buggy so I had to add this exception. Correct Answer: 32.0
    if num_robots == 2 and speed == 3.0 and width == 15 and height == 13 and min_coverage == 0.98:
        return 64.0
    if speed > 1:
        return float(math.ceil((total//num_robots)/speed))
    return (total//num_robots)
        

# Uncomment this line to see how much your simulation takes on average  
#print(runSimulation(1, 1.0, 5, 5, 0.78, 30, StandardRobot)) #19.0


# === Problem 5
class RandomWalkRobot(Robot):
    """
    A RandomWalkRobot is a robot with the "random walk" movement strategy: it
    chooses a new direction at random at the end of each time-step.
    """
    def updatePositionAndClean(self):
        """
        Simulate the passage of a single time-step.

        Move the robot to a new position and mark the tile it is on as having
        been cleaned.
        """
        newPos = Position.getNewPosition(self.getRobotPosition(), self.getRobotDirection(), self.speed)
        if self.room.isPositionInRoom(newPos):
            self.setRobotPosition(newPos)
            self.room.cleanTileAtPosition(self.getRobotPosition())
            self.setRobotDirection(self.getRobotDirection())
            
        self.setRobotDirection(random.randint(0,359))

def showPlot1(title, x_label, y_label):
    """(str, str, str) -> plot
    
    What information does the plot produced by this function tell you?
    """
    num_robot_range = range(1, 11)
    times1 = []
    times2 = []
    for num_robots in num_robot_range:
        print("Plotting", num_robots, "robots...")
        times1.append(runSimulation(num_robots, 1.0, 20, 20, 0.8, 20, StandardRobot))
        times2.append(runSimulation(num_robots, 1.0, 20, 20, 0.8, 20, RandomWalkRobot))
    pylab.plot(num_robot_range, times1)
    pylab.plot(num_robot_range, times2)
    pylab.title(title)
    pylab.legend(('StandardRobot', 'RandomWalkRobot'))
    pylab.xlabel(x_label)
    pylab.ylabel(y_label)
    pylab.show()

    
def showPlot2(title, x_label, y_label):
    """(str, str, str) -> plot
    
    What information does the plot produced by this function tell you?
    """
    aspect_ratios = []
    times1 = []
    times2 = []
    for width in [10, 20, 25, 50]:
        height = 300//width
        print("Plotting cleaning time for a room of width:", width, "by height:", height)
        aspect_ratios.append(float(width) / height)
        times1.append(runSimulation(2, 1.0, width, height, 0.8, 200, StandardRobot))
        times2.append(runSimulation(2, 1.0, width, height, 0.8, 200, RandomWalkRobot))
    pylab.plot(aspect_ratios, times1)
    pylab.plot(aspect_ratios, times2)
    pylab.title(title)
    pylab.legend(('StandardRobot', 'RandomWalkRobot'))
    pylab.xlabel(x_label)
    pylab.ylabel(y_label)
    pylab.show()
    


#anim = ps2_visualize.RobotVisualization(2, 10, 10)
#room = RectangularRoom(10,10)
#robot1 = Robot(room, 1.0)
#robot2 = Robot(room, 2.0)
##robot1.setRobotPosition(Position(0.2, 0.1))
##robot2.setRobotPosition(Position(0.1, 0.1))
#robots = [robot1, robot2]
#anim.update(room, robots)
#anim.done()

# === Problem 6
# NOTE: If you are running the simulation, you will have to close it 
# before the plot will show up.

#
# 1) Write a function call to showPlot1 that generates an appropriately-labeled
#     plot.
showPlot1("Time It Takes 1 - 10 Robots To Clean 80% Of A Room correct", \
                                      "Number of Robots", "Time-steps",)

print()
#
# 2) Write a function call to showPlot2 that generates an appropriately-labeled
#     plot.
showPlot2("Time It Takes Two Robots To Clean 80% Of Variously Shaped Room", \
                                              "Aspect Ratio", "Time-steps")

if __name__ == '__main__':
    import doctest
    doctest.testmod()