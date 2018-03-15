import random, pylab

# You are given this function
def getMeanAndStd(X):
    mean = sum(X)/float(len(X))
    tot = 0.0
    for x in X:
        tot += (x - mean)**2
    std = (tot/len(X))**0.5
    return mean, std

# You are given this class
class Die(object):
    def __init__(self, valList):
        """ valList is not empty """
        self.possibleVals = valList[:]
    def roll(self):
        return random.choice(self.possibleVals)

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
 
#random.seed(0)             
# Implement this -- Coding Part 2 of 2
def getAverage(die, numRolls, numTrials):
    """(list of ints, int, int) -> histogram, float
      - die, a Die
      - numRolls, numTrials, are positive ints
      - Calculates the expected mean value of the longest run of a number
        over numTrials runs of numRolls rolls
      - Calls makeHistogram to produce a histogram of the longest runs for all
        the trials. There should be 10 bins in the histogram
      - Choose appropriate labels for the x and y axes.
      - Returns the mean calculated
      
      >>>getAverage(Die([1,2,3,4,5,6,6,6,7]), 1, 1000)
      1.0
      >>>getAverage(Die([1]), 10, 1000)
      10.0
    """
    listofLongestRuns = []
    
    for trial in range(numTrials):
        dieSamples = random.choices(die.possibleVals, k=numRolls)
#        dieSamples = [die.roll() for _ in range(numRolls)]
        if numRolls == 1:               #if numRolls==1 there could be no longer than 1 run
            listofLongestRuns.append(1) #so we directly append it into the list
        else:                           #otherwise we go thru the list
            maxRun, currentRun = 0, 1
            for i in range(len(dieSamples)-1):
                if dieSamples[i] == dieSamples[i+1]:
                    currentRun += 1
                else:
                    currentRun = 1
                if currentRun > maxRun:
                    maxRun = currentRun
            if maxRun > 0:
                listofLongestRuns.append(maxRun)               
    makeHistogram(listofLongestRuns, numBins = 10, xLabel = 'Length of longest run', yLabel = 'frequency', title = 'Histogram of longest runs')                
    if len(listofLongestRuns) == 0:
        return 0
    return round(sum(listofLongestRuns) / len(listofLongestRuns), 3)
                
        
        
    
###### TEST CASES
#print(getAverage(Die([1]), 10, 1000))
#print(getAverage(Die([1,2,3,4,5,6,6,6,7]), 50, 100))
#print(getAverage(Die([1,2,3,4,5,6,6,6,7]), 1, 1000))
#x = getAverage(Die([1,2,3,4,5,6,6,6,7]), 500, 10000)
#print(makeHistogram([], 1, "A", "B", "C"))
#print(makeHistogram([1,2], 4, "Aaa", "Bbb"))
#print(makeHistogram([21,20,19,1,2,2,2,5,6,6,9,10], 5, "Aaaaa", "Bbbbb", "Ccccc"))