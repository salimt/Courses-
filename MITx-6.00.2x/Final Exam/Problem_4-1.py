# Problem 4
# Bookmark this page
# Problem 4-1
# 10.0/10.0 points (graded)
# You are given the following function and class and function specifications for the two coding problems on this page (also available in
# this file, die.py):
# die.py

# Restrictions:
# Do not paste import pylab in the box.
# You should only be using the pylab.hist, pylab.title, pylab.xlabel, pylab.ylabel, pylab.show functions from the pylab module.
# Do not leave any debugging print statements when you paste your code in the box.


# Implement this -- Coding Part 1 of 2
def makeHistogram(values, numBins, xLabel, yLabel, title=None):
    """(list of ints, int, str, str, [str]) -> histogram
      - values, a sequence of numbers
      - numBins, a positive int
      - xLabel, yLabel, title, are strings
      - Produces a histogram of values with numBins bins and the indicated labels
        for the x and y axis
      - If title is provided by caller, puts that title on the figure and otherwise
        does not title the figure
    """
    pylab.hist(values, bins = numBins)
    if not title == None:
        pylab.title(title)
    pylab.xlabel(xLabel)
    pylab.ylabel(yLabel)
    pylab.show()
    
correctCorrect
