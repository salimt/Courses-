# -*- coding: utf-8 -*-
"""
@author: salimt
"""
#Problem 3
#5/5 points (graded)
#We have learned how to obtain a numerical metric for evaluation. Visualizing our data samples along with fitting curves can also help us figure out the goodness of obtained models. In this problem, we will integrate the numerical metrics and visualization for a comprehensive evaluation.
#
#Implement function evaluate_models_on_training. This function takes as input your data samples (x and y) and the list of models (which are lists of coefficients obtained from generate_models) that you want to apply to your data.
#
#This function should generate a figure for each model. In this figure, you are to plot your data along with your best fit curve, and report on the goodness of the fit with the R^2 value. When you are writing this function try to make your graph match the following format:
#
#Plot the data points as individual blue dots
#Plot your model as a red solid line
#Include a title and label your axes
#Your title should include the value of your model and the R^2 degree of this model. Your title could be longer than your graph. To fix that you can add "\n", which adds a newline to your string, in your title when you concatenate several pieces of information (e.g., title = string_a + "\n" + string_b ).
#After you finish writing the function, you have all the components needed to start generating data samples from the raw temperature records and investigate the trend. Run the following code at the bottom ps4.py.
#
#    
## Problem 3
#y = []
#x = INTERVAL_1
#for year in INTERVAL_1:
#    y.append(raw_data.get_daily_temp('BOSTON', 1, 10, year))
#models = generate_models(x, y, [1])
#evaluate_models_on_training(x, y, models)
#  
#This code just randomly picks a day from a year (i.e., Jan 10th in this case), and sees whether we can find any trend in the temperature changing over the years. We surmise, due to global warming, that the temperature of this specific date should increase over time. This code generates your data samples; each sample represents a year from 1961 to 2005 (i.e., the years in INTERVAL_1) and the temperature of Jan 10th for Boston in that year (provided helper class is helpful for this). The code fits your data to a linear line with generate_models and plots the regression results with evaluate_models_on_training.
#
#What is the R^2 value? (use 3 decimal places)
#0.005 correct  
#
## Problem 3
#
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


### Begining of program
raw_data = Climate('data.csv')

# Problem 3
y = []
x = INTERVAL_1
for year in INTERVAL_1:
    y.append(raw_data.get_daily_temp('BOSTON', 1, 10, year))
models = generate_models(x, y, [1])
evaluate_models_on_training(x, y, models)
#print(evaluate_models_on_training(x, y, models))