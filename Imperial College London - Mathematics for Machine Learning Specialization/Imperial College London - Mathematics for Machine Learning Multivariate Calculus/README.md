# Imperial College London - Mathematics for Machine Learning: Multivariate Calculus

<img src="https://i.imgur.com/1DbDipG.png">

![Imperial College London](http://i.imgur.com/Qktqnu1.png) INSTRUCTORS
#### Instructors: David Dye, Samuel J. Cooper and A. Freddie Page

We take a look at how we can use calculus to build approximations to functions, as well as helping us to quantify how accurate we should expect those approximations to be. We also spend some time talking about where calculus comes up in the training of neural networks, before finally showing you how it is applied in linear regression models.

## Syllabus

### Multivariate calculus
- Building on the foundations of the previous module, we now generalise our calculus tools to handle multivariable systems. This means we can take a function with multiple inputs and determine the influence of each of them separately. It would not be unusual for a machine learning method to require the analysis of a function with thousands of inputs, so we will also introduce the linear algebra structures necessary for storing the results of our multivariate calculus analysis in an orderly fashion.

### Multivariate chain rule and its applications
- Having seen that multivariate calculus is really no more complicated than the univariate case, we now focus on applications of the chain rule. Neural networks are one of the most popular and successful conceptual structures in machine learning. They are build up from a connected web of neurons and inspired by the structure of biological brains. The behaviour of each neuron is influenced by a set of control parameters, each of which needs to be optimised to best fit the data. The multivariate chain rule can be used to calculate the influence of each parameter of the networks, allow them to be updated during training.

### Taylor series and linearisation
- The Taylor series is a method for re-expressing functions as polynomial series. This approach is the rational behind the use of simple linear approximations to complicated functions. In this module, we will derive the formal expression for the univariate Taylor series and discuss some important consequences of this result relevant to machine learning. Finally, we will discuss the multivariate case and see how the Jacobian and the Hessian come in to play.

### Intro to optimisation
- If we want to find the minimum and maximum points of a function then we can use multivariate calculus to do this, say to optimise the parameters (the space) of a function to fit some data. First we’ll do this in one dimension and use the gradient to give us estimates of where the zero points of that function are, and then iterate in the Newton-Raphson method. Then we’ll extend the idea to multiple dimensions by finding the gradient vector, Grad, which is the vector of the Jacobian. This will then let us find our way to the minima and maxima in what is called the gradient descent method. We’ll then take a moment to use Grad to find the minima and maxima along a constraint in the space, which is the Lagrange multipliers method.

### Regression
- In order to optimise the fitting parameters of a fitting function to the best fit for some data, we need a way to define how good our fit is. This goodness of fit is called chi-squared, which we’ll first apply to fitting a straight line - linear regression. Then we’ll look at how to optimise our fitting function using chi-squared in the general case using the gradient descent method. Finally, we’ll look at how to do this easily in Python in just a few lines of code, which will wrap up the course.