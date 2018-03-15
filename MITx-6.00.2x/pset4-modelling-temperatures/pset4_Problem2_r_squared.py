# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#Problem 2: R^2
#10/10 points (graded)
#After we create some regression models, we also want to be able to evaluate our models to figure out how well each model represents our data, and tell good models from poorly fitting ones. One way to evaluate how well the model describes the data is computing the model's R^2 value. R^2 provides a measure of how well the total variation of samples is explained by the model.
#
#Implement the function r_squared. This function will take in:
#
#list, y, that represents the y-coordinates of the original data samples
#estimated, which is a corresponding list of y-coordinates estimated from the regression model
#This function should return the computed R^2 value. You can compute R^2 as follows, where  is the estimated y value for the i-th data point (i.e. predicted by the regression),  is the y value for the ith data point, and  is the mean of the original data samples.
#
#
#If you are still confused about R^2 , its wikipedia(https://en.wikipedia.org/wiki/Coefficient_of_determination) page has a good explanation about its use/how to calculate it.
#
#Note: If you want to use numpy arrays, you should import numpy as np and use np.METHOD_NAME in your code. Unfortunately, pylab does not work with the grader.
#
##Problem 2
#
def r_squared(y, estimated):
    """
    Calculate the R-squared error term.
    Args:
        y: list with length N, representing the y-coords of N sample points
        estimated: a list of values estimated by the regression model
    Returns:
        a float for the R-squared error term
    """
    import numpy

    error = ((numpy.array(estimated) - numpy.array(y))**2).sum()
    meanError = error/len(y)
#    return round(1 - (meanError/numpy.var(y)), 4)
    return 1 - (meanError/numpy.var(y))

#print(r_squared([32.0, 42.0, 31.3, 22.0, 33.0], [32.3, 42.1, 31.2, 22.1, 34.0])) #0.9944