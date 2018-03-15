# Problem 8 Part B
Bookmark this page

 Follow the next steps of the simulation to answer the remaining questions.

**Step 4**: Assume <code>MAXRABBITPOP = 1000, CURRENTRABBITPOP = 500, CURRENTFOXPOP = 30, numSteps = 200</code>. Plot two curves, one for the rabbit population and one for the fox population. You won't be submitting the plots. They are for your own understanding.

**Step 5**: Use <code>polyfit</code> to find the coefficients of a 2nd degree polynomial for the rabbit curve and the same for the fox curve. Then use polyval to evaluation the 2nd degree polynomial and plot it, e.g.
```python
     coeff = polyfit(range(len(rabbitPopulationOverTime)), rabbitPopulationOverTime, 2)

     plot(polyval(coeff, range(len(rabbitPopulationOverTime))))
```

Of course your variables and plotting commands may not look identical to the above code; the above code is shown just to give you an idea of what we mean.

Once you have finished Steps 4 and 5, continue on to answer the following questions.


## Problem 8-2
1/1 point (graded)  
At some point in time, there are more foxes than rabbits.

True correct


## Problem 8-3
0/1 point (graded)  
The polyfit curve for the rabbit population is:

A straight line incorrect


## Problem 8-4
0/1 point (graded)  
The polyfit curve for the fox population is:

A concave up curve (looks like a n shape) correct


## Problem 8-5
1/1 point (graded)  
Changing the initial conditions from 500 rabbits and 30 foxes to 50 rabbits and 300 foxes changes the general shapes of both the polyfit curves for the rabbit population and fox population.

False correct


## Problem 8-6
1/1 point (graded)  
Let's say we make a change in the original simulation. That is, we are going to change one detail in the original simulation, but everything else will remain the same as it was explained in Problem 8 - Part A.

Now, if a fox fails in hunting, it has a 90 percent chance of dying (instead of a 10 percent chance, as in the original simulation).

Changing the probability of an unsuccessful fox dying from 10% to 90% changes the general shapes of both the polyfit curves for the rabbit population and fox population.


False correct
