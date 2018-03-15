# MIT 6.00.2x
## Solutions for MIT's Introduction to Computational Thinking and Data Science
![Massachusetts Institute of Technology](http://i.imgur.com/mUhUlgk.jpg)

![Massachusetts Institute of Technology](http://i.imgur.com/Qktqnu1.png) INSTRUCTORS
#### Instructors: Eric Grimson and John Guttag

## Course Description

6.00.2x will teach you how to use computation to accomplish a variety of goals and provides you with a brief introduction to a variety of topics in computational problem solving . This course is aimed at students with some prior programming experience in Python and a rudimentary knowledge of computational complexity. You will spend a considerable amount of time writing programs to implement the concepts covered in the course. For example, you will write a program that will simulate a robot vacuum cleaning a room or will model the population dynamics of viruses replicating and drug treatments in a patient's body.

## Topics covered include:

- Advanced programming in Python 3
- Knapsack problem, Graphs and graph optimization
- Dynamic programming
- Plotting with the pylab package
- Random walks
- Probability, Distributions
- Monte Carlo simulations
- Curve fitting
- Statistical fallacies

### Problem Set 2
This was perhaps the most fun assignment of the entire course. As an introduction to models and simulations, I compared the performance of two different kinds of vacuum cleaning robots via the 
[Monte Carlo method](https://en.wikipedia.org/wiki/Monte_Carlo_method)
(which involves using randomness to solve problems that may be deterministic in principle). 
The model was then used to analyze the impacts of room dimensions and the presence of multiple robots running concurrently on the total time taken to clean the room.

<img src="https://i.imgur.com/YoBS1PZ.png" width=420>
<img src="https://i.imgur.com/tRnTRyb.png" width=420>

### Problem Set 3
This problem set required me to build a simplified model used to analyze the lifecycle and evolution of viruses in patient's body. 
Using this model, I understood how quickly different viruses spread around the body, and how they may evolve 
to develop resistances to certain drugs. I additionally plotted graphs to understand the various distributions further. Once again, 
you can find them inside the project folder.  

`simple_virus_growth.png` shows growth without any drugs. For a patient with a maximum population of 1000 units of virus, the population stabilizes around 500. The number will change depending on the probability that a virus clears or reproduces at a given time step.    

`drug_virus_growth.png` shows the more complex and realistic case when a virus is able to develop a resistance to certain drugs. 
For a total of 300 time steps, the total units of viruses, and total units of resistant viruses have been plotted. Drugs are added
at the 150th time step and, almost immediately, the number of non-resistant viruses drops until most of the viruses left are all
resistant. 

<img src="https://i.imgur.com/I9eOAS3.png" width="420">
<img src="https://i.imgur.com/eXtv4mn.png" width="420">

### Problem Set 4
Essentially an extension of problem set 3. I built upon the existing virus, drug, and patient models to run new simulations and 
and understand more about what was happening. In particular, I used these simulations to determine the best time during the virus
lifecycle to diagnose the patient with drugs. Inside the plots folder, you can see the final results after running the several trials 
of the growth. The delay indicates the number of time steps between infection and diagnosis. As you might expect, the final virus population is least when the patient is diagnosed earlier. 

<img src="https://i.imgur.com/fIyv35d.png" width=420>
<img src="https://i.imgur.com/aVmJJwg.png" width=420>
<img src="https://i.imgur.com/Os8k6CP.png" width=420>
<img src="https://i.imgur.com/FzNhlrJ.png" width=420>

### Problem Set 5
This focused on graph algorithms and optimization. `mit_map.txt` contains data about the paths between buildings on the MIT campus.
Below is the visual representation of `mit_map.txt` The first two numbers one each line are codes for buildings. 
The third number indicates the total distance between the two buildings, and the fourth indicates the outdoor distance. 
The goal was to implement a graph data structure, and then use breadth-first and depth-first search to find the shortest distance given some constraints (the total outdoor distance should be less than 550m, example).

<img src="https://i.imgur.com/eazIu7L.gif">

###### photos by jashans98