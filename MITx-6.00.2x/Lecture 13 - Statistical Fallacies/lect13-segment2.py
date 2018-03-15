# -*- coding: utf-8 -*-
"""
Created on Sun Oct  9 17:19:46 2016

@author: guttag
"""

import pylab

#set line width
pylab.rcParams['lines.linewidth'] = 4
#set font size for titles 
pylab.rcParams['axes.titlesize'] = 20
#set font size for labels on axes
pylab.rcParams['axes.labelsize'] = 20
#set size of numbers on x-axis
pylab.rcParams['xtick.labelsize'] = 16
#set size of numbers on y-axis
pylab.rcParams['ytick.labelsize'] = 16
#set size of ticks on x-axis
pylab.rcParams['xtick.major.size'] = 7
#set size of ticks on y-axis
pylab.rcParams['ytick.major.size'] = 7
#set size of markers
pylab.rcParams['lines.markersize'] = 10
#set number of examples shown in legends
pylab.rcParams['legend.numpoints'] = 1

import random
random.seed(0)
numCasesPerYear = 36000
numYears = 3
stateSize = 10000
communitySize = 10
numCommunities = stateSize//communitySize
numTrials = 100
numGreater = 0
for t in range(numTrials):
    locs = [0]*numCommunities
    for i in range(numYears*numCasesPerYear):
        locs[random.choice(range(numCommunities))] += 1
    if max(locs) >= 143:
        numGreater += 1
prob = round(numGreater/numTrials, 4)
print('Est. probability of at least one region having \
at least 143 cases =', prob)












#print('Average number of cases per community =',
#      (numYears*numCasesPerYear)/numCommunities)
#print('Maximum number of cases in community X =',
#      max(locs))
#pylab.hist(locs)
#pylab.xlabel('Region')
#pylab.ylabel('Number of New Cancer Cases')

