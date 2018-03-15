# -*- coding: utf-8 -*-
"""
@author: salimt
"""

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