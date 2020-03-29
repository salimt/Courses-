Quiz 3
======

Question 1
----------
Take a look at the 'iris' dataset that comes with R. The data can be loaded with the code:

    library(datasets)
    data(iris)
  
A description of the dataset can be found by running

	?iris

There will be an object called 'iris' in your workspace. In this dataset, what is the mean of 'Sepal.Length' for the species virginica? (Please only enter the numeric result and nothing else.)

### Answer
6.588

### Explanation

    > library(datasets)
    > data(iris)
    > ?iris
    > mean(iris[iris$Species == "virginica",]$Sepal.Length)
    [1] 6.588


Question 2
----------
Continuing with the 'iris' dataset from the previous Question, what R code returns a vector of the means of the variables 'Sepal.Length', 'Sepal.Width', 'Petal.Length', and 'Petal.Width'?

### Answer
apply(iris[, 1:4], 2, mean)

### Explanation

    > library(datasets)
    > data(iris)
    > apply(iris[, 1:4], 2, mean)
    Sepal.Length  Sepal.Width Petal.Length  Petal.Width 
        5.843333     3.057333     3.758000     1.199333 

Question 3
----------
Load the 'mtcars' dataset in R with the following code

    library(datasets)
    data(mtcars)
    
There will be an object names 'mtcars' in your workspace. You can find some information about the dataset by running

    ?mtcars
    
How can one calculate the average miles per gallon (mpg) by number of cylinders in the car (cyl)?

### Answer
sapply(split(mtcars$mpg, mtcars$cyl), mean)

### Explanation

    > library(datasets)
    > data(mtcars)
    > ?mtcars
    > sapply(split(mtcars$mpg, mtcars$cyl), mean)
           4        6        8 
    26.66364 19.74286 15.10000 


Question 4
----------
Continuing with the 'mtcars' dataset from the previous Question, what is the absolute difference between the average horsepower of 4-cylinder cars and the average horsepower of 8-cylinder cars?

### Answer
126.5779

### Explanation

    > library(datasets)
    > data(mtcars)
    > mean(mtcars[mtcars$cyl == "8",]$hp) - mean(mtcars[mtcars$cyl == "4",]$hp)
    [1] 126.5779


Question 5
----------
If you run

    debug(ls)
    
what happens when you next call the 'ls' function?

### Answer
Execution of 'ls' will suspend at the beginning of the function and you will be in the browser.
