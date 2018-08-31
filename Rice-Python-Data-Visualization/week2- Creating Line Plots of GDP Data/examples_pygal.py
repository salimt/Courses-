"""
Example of drawing line plots with Pygal.
"""

import pygal

def draw_line(title, xvals, yvals):
    """
    Draw line plot with given x and y values.
    """
    lineplot = pygal.Line(height=400)
    lineplot.title = title
    lineplot.x_labels = xvals
    lineplot.add("Data", yvals)
    lineplot.render_in_browser()

xvals = [0, 1, 3, 5, 6, 7, 9, 11, 12, 15]
yvals = [4, 3, 1, 2, 2, 4, 5, 2, 1, 4]

draw_line("My Line Plot", xvals, yvals)

def draw_xy(title, xvals, yvals):
    """
    Draw xy plot with given x and y values.
    """
    coords = [(xval, yval) for xval, yval in zip(xvals, yvals)]
    xyplot = pygal.XY(height=400)
    xyplot.title = title
    xyplot.add("Data", coords)
    xyplot.render_in_browser()

draw_xy("My XY Plot", xvals, yvals)
