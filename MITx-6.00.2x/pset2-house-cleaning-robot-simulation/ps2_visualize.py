# Problem Set 2:
# Visualization code for simulated robots.
# See the problem set for instructions on how to use this code.

import math
import time

from tkinter import *

class RobotVisualization:
    def __init__(self, num_robots, width, height, delay = 0.2):
        "Initializes a visualization with the specified parameters."
        # Number of seconds to pause after each frame
        self.delay = delay

        self.max_dim = max(width, height)
        self.width = width
        self.height = height
        self.num_robots = num_robots

        # Initialize a drawing surface
        self.master = Tk()
        self.w = Canvas(self.master, width=500, height=500)
        self.w.pack()
        self.master.update()

        # Draw a backing and lines
        x1, y1 = self._map_coords(0, 0)
        x2, y2 = self._map_coords(width, height)
        self.w.create_rectangle(x1, y1, x2, y2, fill = "white")

        # Draw gray squares for dirty tiles
        self.tiles = {}
        for i in range(width):
            for j in range(height):
                x1, y1 = self._map_coords(i, j)
                x2, y2 = self._map_coords(i + 1, j + 1)
                self.tiles[(i, j)] = self.w.create_rectangle(x1, y1, x2, y2,
                                                             fill = "gray")

        # Draw gridlines
        for i in range(width + 1):
            x1, y1 = self._map_coords(i, 0)
            x2, y2 = self._map_coords(i, height)
            self.w.create_line(x1, y1, x2, y2)
        for i in range(height + 1):
            x1, y1 = self._map_coords(0, i)
            x2, y2 = self._map_coords(width, i)
            self.w.create_line(x1, y1, x2, y2)

        # Draw some status text
        self.robots = None
        self.text = self.w.create_text(25, 0, anchor=NW,
                                       text=self._status_string(0, 0))
        self.time = 0
        self.master.update()

    def _status_string(self, time, num_clean_tiles):
        "Returns an appropriate status string to print."
        percent_clean = round(100 * num_clean_tiles / (self.width * self.height))
        return "Time: %04d; %d tiles (%d%%) cleaned" % \
            (time, num_clean_tiles, percent_clean)

    def _map_coords(self, x, y):
        "Maps grid positions to window positions (in pixels)."
        return (250 + 450 * ((x - self.width / 2.0) / self.max_dim),
                250 + 450 * ((self.height / 2.0 - y) / self.max_dim))

    def _draw_robot(self, position, direction):
        "Returns a polygon representing a robot with the specified parameters."
        x, y = position.getX(), position.getY()
        d1 = direction + 165
        d2 = direction - 165
        x1, y1 = self._map_coords(x, y)
        x2, y2 = self._map_coords(x + 0.6 * math.sin(math.radians(d1)),
                                  y + 0.6 * math.cos(math.radians(d1)))
        x3, y3 = self._map_coords(x + 0.6 * math.sin(math.radians(d2)),
                                  y + 0.6 * math.cos(math.radians(d2)))
        return self.w.create_polygon([x1, y1, x2, y2, x3, y3], fill="red")

    def update(self, room, robots):
        "Redraws the visualization with the specified room and robot state."
        # Removes a gray square for any tiles have been cleaned.
        for i in range(self.width):
            for j in range(self.height):
                if room.isTileCleaned(i, j):
                    self.w.delete(self.tiles[(i, j)])
        # Delete all existing robots.
        if self.robots:
            for robot in self.robots:
                self.w.delete(robot)
                self.master.update_idletasks()
        # Draw new robots
        self.robots = []
        for robot in robots:
            pos = robot.getRobotPosition()
            x, y = pos.getX(), pos.getY()
            x1, y1 = self._map_coords(x - 0.08, y - 0.08)
            x2, y2 = self._map_coords(x + 0.08, y + 0.08)
            self.robots.append(self.w.create_oval(x1, y1, x2, y2,
                                                  fill = "black"))
            self.robots.append(
                self._draw_robot(robot.getRobotPosition(), robot.getRobotDirection()))
        # Update text
        self.w.delete(self.text)
        self.time += 1
        self.text = self.w.create_text(
            25, 0, anchor=NW,
            text=self._status_string(self.time, room.getNumCleanedTiles()))
        self.master.update()
        time.sleep(self.delay)

    def done(self):
        "Indicate that the animation is done so that we allow the user to close the window."
        mainloop()

