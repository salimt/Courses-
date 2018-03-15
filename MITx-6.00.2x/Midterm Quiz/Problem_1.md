# Problem 1
 Bookmark this page
## Problem 1-1
1/1 point (graded)  
  The following function is stochastic:
```python
def f(x):
    # x is an integer
    return int(x + random.choice([0.25, 0.5, 0.75]))
```
False correct

## Problem 1-2
1/1 point (graded)  
In Python, we can use <code>random.seed(100)</code> at the beginning of a program to generate the same sequence of random numbers each time we run a program.

True correct

## Problem 1-3
1/1 point (graded)  
A brute force solution to the 0/1 knapsack problem will always produce an optimal solution.

True correct

## Problem 1-4
1/1 point (graded)  
The following function is stochastic.  
```python
import random

def A():
    mylist = []
    r = 1

    if random.random() > 0.99:
        r = random.randint(1, 10)
    for i in range(r):
        random.seed(0)
        if random.randint(1, 10) > 3:
            number = random.randint(1, 10)
            mylist.append(number)
    print(mylist)
```

True correct

## Problem 1-5
0/1 point (graded)  
Consider an undirected graph with non-negative weights that has an edge between each pair of nodes. The shortest distance between any two nodes is always the path that is the edge between the two nodes.

True incorrect
