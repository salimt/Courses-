## Week 9

### Highlights

1. Anomaly detection algorithm (Unsupervised learning)
- Make use of the Gaussian distribution if you suspect that the dataset comes from a Gaussian distribution, compute the Gaussian distribution parameters for each feature
- Treat all parameters as independent events, get p(x) from the multiply combination of all features' Gaussian distribution

2. To evaluate an anomaly detection algorithm
- Assume we have labeled data (evaluate in a supervised way)
- Fit the model on the training set
- Evaluate on the cross validation set/test set according to the F1 score
- Pick the epsilon that gives the best F1 score

3. Anomaly detection algorithm VS supervised learning algorithm
- Anomaly detection algorithm: Small number of positive examples and a large number of negative examples; Supervised learning algorithm: Large number of positive and negative examples
- Anomaly detection algorithm: many types of anomalies (a totally new type of anomaly is possible); Supervised learning algorithm: future positive examples are likely to be the same type that the training data have

4. Applications of Anomaly detection algorithm
- Fraud detection
- Manufacturing (aircraft quality)
- Monitor machines in a data center

5. Multivariate Gaussian Distribution
- P(x) ~ Multivariate Gaussian(mu, sigma) where sigma is the covariance matrix, such that we will be able to capture an ellipse shape of cluster (towards any direction)

6. Recommender System
- Content Based Algorithm (we know the content of the product)
  - Some features defining type of product & some parameters (theta) defining each customer
  - To learn theta: find the theta that gives the smallest cost function J (a seperate linear regression for each user) using gradient descent
- Collaborative Filtering Algorithm

Type 1:

    - Randomly initiate certain theta
    - Compute features according the initialized theta using cost function and gradient descent
    - Using the features, compute a better set of theta using cost function and gradient descent
    - Going back and forth (Only we have large set of rated movies)

Type 2:

    - Randomly initiate certain theta and feature values
    - Minimize the cost function that contains both theta and feature using gradient descent (that is to minimize these two simultaneously)
    - Given a user, and a movie with certain feature value, predict rating.

Type 3:

    - Given a product A with feature x<sub>a</sub>, find a product B with feature x<sub>b</sub> that the distance from x<sub>a</sub> to x<sub>b</sub> is small
    - Predict product B as a similar product as product A

7. A prepocessing technique: Mean normalization
- User who has never rated any product in Recommender System
- According to the cost function, we will have 0 for all product for the user who hasn't rated any product.
- Using mean normalization, for each movie, all users' rating sum is tuned to be 0 (each entry - mean of the row)
- Apply the usual Collaborative Filtering algorithm
- Add the mean back to obtain the actual theta value

### Quiz 1: Anomaly Detection

1.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz1-1.png)

**Explanation:**
> Number of types for transactions and diseases is countable and predictable. Thus should use a clustering algorithm

2.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz1-2.png)

**Explanation:**
> Increase the threshold so that we will have a large range for detecting anomalies.

3.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz1-3.png)

4.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz1-4.png)

**Explanation:**
> A) Correct.

> B) Usually we will have a skewed dataset for anomaly detection, thus accuracy may not be a good matrix to represent the model performance.

> C) Usually we have lots of normal training examples, and a few anomaly examples.

> D) Correct.

5.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz1-5.png)

### Quiz 2:

1.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz2-1.png)

2.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz2-2.png)

3.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz2-3.png)

4.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz2-4.png)

5.

![](https://github.com/LiMengyang990726/Coursera-Machine-Learning/blob/master/Pictures/Week9Quiz2-5.png)