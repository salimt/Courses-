Quiz 01
=======

<table>
<thead>
<tr class="header">
<th align="center">Attempts</th>
<th align="center">Score</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td align="center">1/3</td>
<td align="center">10/10</td>
</tr>
</tbody>
</table>

Question 01
-----------

Under the lattice graphics system, what do the primary plotting
functions like xyplot() and bwplot() return?

-   an object of class `lattice`.  
-   an object of class `plot`.  
-   nothing; only a plot is made.  
-   an object of class `trellis`.

### Answer

-   an object of class `trellis`.

Question 02
-----------

What is produced by the following code?

    library(nlme)
    library(lattice)
    xyplot(weight ~ Time | Diet, BodyWeight)

-   A set of 3 panels showing the relationship between weight and time
    for each rat.  
-   A set of 3 panels showing the relationship between weight and time
    for each diet.  
-   A set of 11 panels showing the relationship between weight and diet
    for each time.  
-   A set of 16 panels showing the relationship between weight and time
    for each rat.

### Answer

-   A set of 3 panels showing the relationship between weight and time
    for each diet.

#### Explanation

    library(nlme)
    library(lattice)
    xyplot(weight ~ Time | Diet, BodyWeight)

![](Quiz_02_files/figure-markdown_strict/unnamed-chunk-2-1.png)

Question 03
-----------

Annotation of plots in any plotting system involves adding points,
lines, or text to the plot, in addition to customizing axis labels or
adding titles. Different plotting systems have different sets of
functions for annotating plots in this way. Which of the following
functions can be used to annotate the panels in a multi-panel lattice
plot?

-   lines()  
-   text()  
-   axis()  
-   panel.lmline()

### Answer

-   panel.lmline()

Question 04
-----------

The following code does NOT result in a plot appearing on the screen
device.

    library(lattice)
    library(datasets)
    data(airquality)
    p <- xyplot(Ozone ~ Wind | factor(Month), data = airquality)

Which of the following is an explanation for why no plot appears?

-   There is a syntax error in the call to xyplot().  
-   The xyplot() function, by default, sends plots to the PDF device.  
-   The variables being plotted are not found in that dataset.  
-   The object 'p' has not yet been printed with the appropriate
    print method.

### Answer

-   The object 'p' has not yet been printed with the appropriate
    print method.

#### Explanation

    library(lattice)
    library(datasets)
    data(airquality)
    p <- xyplot(Ozone ~ Wind | factor(Month), data = airquality)
    print(p)

![](Quiz_02_files/figure-markdown_strict/unnamed-chunk-4-1.png)

Question 05
-----------

In the lattice system, which of the following functions can be used to
finely control the appearance of all lattice plots?

-   splom()  
-   par()  
-   trellis.par.set()  
-   print.trellis()

### Answer

-   trellis.par.set()

Question 06
-----------

What is ggplot2 an implementation of?

-   the Grammar of Graphics developed by Leland Wilkinson.  
-   the base plotting system in R.  
-   the S language originally developed by Bell Labs.  
-   a 3D visualization system.

### Answer

-   the Grammar of Graphics developed by Leland Wilkinson.

Question 07
-----------

Load the \`airquality' dataset form the datasets package in R.

    library(datasets)
    data(airquality)

I am interested in examining how the relationship between ozone and wind
speed varies across each month. What would be the appropriate code to
visualize that using ggplot2?

-   `qplot(Wind, Ozone, data = airquality, facets = . ~ factor(Month))`  
-   `qplot(Wind, Ozone, data = airquality, geom = "smooth")`  
-   `airquality = transform(airquality, Month = factor(Month))`  
    `qplot(Wind, Ozone, data = airquality, facets = . ~ Month)`  
-   `qplot(Wind, Ozone, data = airquality)`

### Answer

-   `airquality = transform(airquality, Month = factor(Month))`  
    `qplot(Wind, Ozone, data = airquality, facets = . ~ Month)`

#### Explanation

    library(datasets)
    library(ggplot2)
    data(airquality)
    airquality = transform(airquality, Month = factor(Month))
    qplot(Wind, Ozone, data = airquality, facets = . ~ Month)

![](Quiz_02_files/figure-markdown_strict/unnamed-chunk-6-1.png)

Question 08
-----------

What is a geom in the ggplot2 system?

-   a method for making conditioning plots.  
-   a method for mapping data to attributes like color and size.  
-   a plotting object like point, line, or other shape.  
-   a statistical transformation.

### Answer

-   a plotting object like point, line, or other shape.

Question 09
-----------

When I run the following code I get an error:

    library(ggplot2)
    g <- ggplot(movies, aes(votes, rating))
    print(g)

I was expecting a scatterplot of 'votes' and 'rating' to appear. What's
the problem?

-   ggplot does not yet know what type of layer to add to the plot.  
-   The dataset is too large and hence cannot be plotted to the
    screen.  
-   The object 'g' does not have a print method.  
-   There is a syntax error in the call to ggplot.

### Answer

-   ggplot does not yet know what type of layer to add to the plot.

Question 10
-----------

The following code creates a scatterplot of 'votes' and 'rating' from
the movies dataset in the ggplot2 package. After loading the ggplot2
package with the library() function, I can run

    qplot(votes, rating, data = movies)

How can I modify the the code above to add a smoother to the
scatterplot?

-   `qplot(votes, rating, data = movies, panel = panel.loess)`  
-   `qplot(votes, rating, data = movies) + stats_smooth("loess")`  
-   `qplot(votes, rating, data = movies, smooth = "loess")`  
-   `qplot(votes, rating, data = movies) + geom_smooth()`

### Answer

-   `qplot(votes, rating, data = movies) + geom_smooth()`

#### Explanation

    library(ggplot2)
    qplot(votes, rating, data = movies) + geom_smooth()

    ## geom_smooth: method="auto" and size of largest group is >=1000, so using gam with formula: y ~ s(x, bs = "cs"). Use 'method = x' to change the smoothing method.

![](Quiz_02_files/figure-markdown_strict/unnamed-chunk-9-1.png)
