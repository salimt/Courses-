# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#Problem 4-1
#5/5 points (graded)
#Let's try another way to get data points and see whether we can find some evidence for global warming. We surmise, due to global warming, the average temperature should increase over time. Thus, we are going to plot the results of a linear regression on the average annual temperature of Boston.
#
#In a similar manner to Problem 3, fill in the missing piece to the following code. The code should generate your data samples. Each sample represents a year from 1961 to 2005 and the average annual temperature in Boston in that year (again, the provided helper class is helpful). Fit your data to a linear line with generate_models and plot the regression results with evaluate_models_on_training.
#
## Problem 4: FILL IN MISSING CODE TO GENERATE y VALUES
#x1 = INTERVAL_1
#x2 = INTERVAL_2
#y = []
## MISSING LINES
#models = generate_models(x1, y, [1])    
#evaluate_models_on_training(x1, y, models)
#  
#Which of the following is the correct missing code?
#
#for year in INTERVAL_1:
#    y.append(numpy.mean(raw_data.get_yearly_temp('BOSTON', year)))
#
#Correct (5/5 points)
#
#Problem 4-2
#5/5 points (graded)
#What is the R^2 value? (use 3 decimal places)
#
#0.085 correct  

# Problem 3
def evaluate_models_on_training(xVals, yVals, model):
    """
    For each regression model, compute the R-square for this model with the
    standard error over slope of a linear regression line (only if the model is
    linear), and plot the data along with the best fit curve.

    For the plots, you should plot data points (x,y) as blue dots and your best
    fit curve (aka model) as a red solid line. You should also label the axes
    of this figure appropriately and have a title reporting the following
    information:
        degree of your regression model,
        R-square of your model evaluated on the given data points
    Args:
        x: a list of length N, representing the x-coords of N sample points
        y: a list of length N, representing the y-coords of N sample points
        models: a list containing the regression models you want to apply to
            your data. Each model is a numpy array storing the coefficients of
            a polynomial.
    Returns:
        None
    """
    import numpy

    xVals = numpy.array(xVals)
    yVals = numpy.array(yVals)
    xVals = xVals*9.81   #get force
    pylab.plot(xVals, yVals, 'bo',
               label = 'Measured points')                 
    model = numpy.polyfit(xVals, yVals, 1)
    xVals = xVals + [2]
#    yVals = yVals + []
    estYVals = numpy.polyval(model, xVals)
    pylab.plot(xVals, estYVals, 'r',
               label = 'Linear fit, r**2 = '
               + str(round(r_squared(yVals, estYVals), 5)))          
#    model = pylab.polyfit(xVals, yVals, 2)
#    estYVals = pylab.polyval(model, xVals)
#    pylab.plot(xVals, estYVals, 'g--',
#               label = 'Quadratic fit, r**2 = '
#               + str(round(r_squared(yVals, estYVals), 5)))
    pylab.title('A Linear Spring')
#    pylab.title('Measured Displacement of Spring')
    pylab.xlabel('Years')
    pylab.ylabel('Temperatures')
    pylab.legend(loc = 'best')
    
# Problem 4: FILL IN MISSING CODE TO GENERATE y VALUES
x1 = INTERVAL_1
x2 = INTERVAL_2
y = []
import numpy
# MISSING LINES
for year in INTERVAL_1:
    y.append(numpy.mean(raw_data.get_yearly_temp('BOSTON', year)))
# MISSING LINES
models = generate_models(x, y, [1])    
evaluate_models_on_training(x, y, models)