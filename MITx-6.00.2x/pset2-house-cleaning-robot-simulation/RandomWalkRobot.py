# -*- coding: utf-8 -*-
"""
@author: salimt
"""

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