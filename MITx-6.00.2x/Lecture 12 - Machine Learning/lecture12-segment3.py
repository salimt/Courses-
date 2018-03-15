# -*- coding: utf-8 -*-
"""
Created on Mon Sep 19 11:45:20 2016

@author: johnguttag
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

import random, pylab

def minkowskiDist(v1, v2, p):
    """Assumes v1 and v2 are equal-length arrays of numbers
       Returns Minkowski distance of order p between v1 and v2"""
    dist = 0.0
    for i in range(len(v1)):
        dist += abs(v1[i] - v2[i])**p
    return dist**(1/p)

class Example(object):
    
    def __init__(self, name, features):
        #Assumes features is an array of floats
        self.name = name
        self.features = features
            
    def getFeatures(self):
        return self.features[:]
        
    def getName(self):
        return self.name
        
    def dimensionality(self):
        return len(self.features)
    
    def setColor(self, color):
        self.color = color
    
    def distance(self, other):
        return minkowskiDist(self.features, other.getFeatures(), 2)

class Cluster(object):
    
    def __init__(self, examples):
        """Assumes examples a non-empty list of Examples"""
        self.examples = examples
        self.centroid = self.computeCentroid()
        
    def update(self, examples):
        """Assume examples is a non-empty list of Examples
           Replace examples; return amount centroid has changed"""
        oldCentroid = self.centroid
        self.examples = examples
        self.centroid = self.computeCentroid()
        self.oldCentroid = oldCentroid
        return oldCentroid.distance(self.centroid)
    
    def computeCentroid(self):
        vals = pylab.array([0.0]*self.examples[0].dimensionality())
        for e in self.examples: #compute mean
            vals += e.getFeatures()
        centroid = Example('centroid', vals/len(self.examples))
        return centroid

    def getCentroid(self):
        return self.computeCentroid()

    def variability(self):
        totDist = 0.0
        for e in self.examples:
            totDist += (e.distance(self.centroid))**2
        return totDist
        
    def members(self):
        for e in self.examples:
            yield e

    def plotCluster(self, color):
        xVals, yVals = [], []
        for e in self.examples:
            xVals.append(e.getFeatures()[0])
            yVals.append(e.getFeatures()[1])
        pylab.plot(xVals, yVals, color + 'o')
        pylab.plot([self.oldCentroid.getFeatures()[0]],
                   [self.oldCentroid.getFeatures()[1]],
                   color + '*', markersize = 20)
        
    def __str__(self):
        names = []
        for e in self.examples:
            names.append(e.getName())
        names.sort()
        result = 'Cluster with centroid '\
               + str(self.centroid.getFeatures()) + ' contains:\n  '
        for e in names:
            result = result + e + ', '
        return result[:-2] #remove trailing comma and space

def kmeans(examples, k, verbose = False):
    #Get k randomly chosen initial centroids, create cluster for each
    initialCentroids = random.sample(examples, k)
    clusters = []
    for e in initialCentroids:
        clusters.append(Cluster([e]))
        
    #Iterate until centroids do not change
    converged = False
    numIterations = 0
    while not converged:
        numIterations += 1
        #Create a list containing k distinct empty lists
        newClusters = []
        for i in range(k):
            newClusters.append([])
            
        #Associate each example with closest centroid
        for e in examples:
            #Find the centroid closest to e
            smallestDistance = e.distance(clusters[0].getCentroid())
            index = 0
            for i in range(1, k):
                distance = e.distance(clusters[i].getCentroid())
                if distance < smallestDistance:
                    smallestDistance = distance
                    index = i
            #Add e to the list of examples for appropriate cluster
            newClusters[index].append(e)
            
        for c in newClusters: #Avoid having empty clusters
            if len(c) == 0:
                raise ValueError('Empty Cluster')
        
        #Update each cluster; check if a centroid has changed
        converged = True
        for i in range(k):
            if clusters[i].update(newClusters[i]) > 0.0:
                converged = False
        if verbose:
            colors = ['b', 'r', 'k', 'm', 'y']
            color = 0
            pylab.figure()
            print('Iteration #' + str(numIterations))
            for c in clusters:
                print('Cluster color =', color)
                print(c)
                c.plotCluster(colors[color])
                color += 1
            print('') #add blank line
    return clusters

centers = [(2, 3), (4, 6), (7, 4), (7,7)]
examples = []
random.seed(0)
for c in centers:
    for i in range(5):
        xVal = (c[0] + random.gauss(0, .5))
        yVal = (c[1] + random.gauss(0, .5))
        name = str(c) + '-' + str(i)
        example = Example(name, pylab.array([xVal, yVal]))
        examples.append(example)

xVals, yVals = [], []
for e in examples:
    xVals.append(e.getFeatures()[0])
    yVals.append(e.getFeatures()[1])

random.seed(2)
kmeans(examples, 4, True)   