# Problem 4-2
# 20.0/20.0 points (graded)
# Write a function called getAverage(die, numRolls, numTrials), with the following specification:

# A run of numbers counts the number of times the same dice value shows up in consecutive rolls. For example:
# a dice roll of 1 4 3 has a longest run of 1
# a dice roll of 1 3 3 2 has a longest run of 2
# a dice roll of 5 4 4 4 5 5 2 5 has a longest run of 3

# When this function is called with the test case given in the file, it will return 5.312. Your simulation may give slightly different
# values.

# Paste your entire function (including the definition) in the box.

# Restrictions:
# Do not import or use functions or methods from pylab, numpy, or matplotlib.
# Do not leave any debugging print statements when you paste your code in the box.
# If you do not see the return value being printed when testing your function, close the histogram window.


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
    
Correct
