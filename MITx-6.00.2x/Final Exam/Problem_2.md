# Problem 2
Bookmark this page

 Consider the following code:

```python
import random, pylab
xVals = []
yVals = []
wVals = []
for i in range(1000):
    xVals.append(random.random())
    yVals.append(random.random())
    wVals.append(random.random())
xVals = pylab.array(xVals)
yVals = pylab.array(yVals)
wVals = pylab.array(wVals)
xVals = xVals + xVals
zVals = xVals + yVals
tVals = xVals + yVals + wVals
```
For each of the following questions, select the best answer from the set of choices.

## Problem 2-1
2.0/2.0 points (graded)  
The values in tVals are most closely:

Distributed with a Gaussian distribution correct

## Problem 2-2
2.0/2.0 points (graded)  
The values in xVals are most closely:

Uniformly distributed correct

For each of the following expressions using the code above, match the following calls to <code>pylab.plot</code> with one of the graphs shown below.

You can click on the following images to view a larger size.

![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/7fc44cee4d4360ca476f37cc17420e7c/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-1.png)
**Graph 1**
![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/e8bfc1a418fb313e6deba1c81792146f/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-2.png)
**Graph 2**
![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/7ec8fb1c2a81490f82b6c24a51779dd9/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-3.png)
**Graph 3**
![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/5806673cab9dc3323bbf59f1c57e7e0b/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-4.png)
**Graph 4**
![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/02cc9672d760926964ef1a26d5a84e11/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-5.png)
**Graph 5**
![](https://d37djvu3ytnwxt.cloudfront.net/assets/courseware/v1/cdc227c2a165a5484e566f05c20200ca/asset-v1:MITx+6.00.2x_7+1T2017+type@asset+block/files_exam2_files_3-6.png)
**Graph 6**

## Problem 2-3
2.0/2.0 points (graded)  
<code>pylab.plot(xVals, zVals)</code>

Graph 5 correct

## Problem 2-4
2.0/2.0 points (graded)  
<code>pylab.plot(xVals, yVals)</code>

Graph 4
correct


## Problem 2-5
2.0/2.0 points (graded)  
<code>pylab.plot(xVals, sorted(yVals))</code>  

Graph 3
correct

## Problem 2-6
2.0/2.0 points (graded)  
<code>pylab.plot(sorted(xVals), yVals)</code>

Graph 2
correct

## Problem 2-7
2.0/2.0 points (graded)  
<code>pylab.plot(sorted(xVals), sorted(yVals))</code>

Graph 1 correct
