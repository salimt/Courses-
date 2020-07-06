function [C, sigma] = dataset3Params(X, y, Xval, yval)
%DATASET3PARAMS returns your choice of C and sigma for Part 3 of the exercise
%where you select the optimal (C, sigma) learning parameters to use for SVM
%with RBF kernel
%   [C, sigma] = DATASET3PARAMS(X, y, Xval, yval) returns your choice of C and 
%   sigma. You should complete this function to return the optimal C and 
%   sigma based on a cross-validation set.
%

% You need to return the following variables correctly.
C = 1;
sigma = 0.3;

% ====================== YOUR CODE HERE ======================
% Instructions: Fill in this function to return the optimal C and sigma
%               learning parameters found using the cross validation set.
%               You can use svmPredict to predict the labels on the cross
%               validation set. For example, 
%                   predictions = svmPredict(model, Xval);
%               will return the predictions on the cross validation set.
%
%  Note: You can compute the prediction error using 
%        mean(double(predictions ~= yval))
%

% Selected values of lambda (you should not change this)
#lambda_vec = [0.01 0.03 0.1 0.3 1 3 10 30]';

% Selected values of C and sigma (you should not change this)
#C_set = [0.01 0.03 0.1 0.3 1 3 10 30]';
#sigma_set = [0.01 0.03 0.1 0.3 1 3 10 30]';
train_set = [0.01 0.03 0.1 0.3 1 3 10 30]';
error_t = 1;

for i = 1:length(train_set)
      for j = 1:length(train_set)
            
            model = svmTrain(X, y, train_set(i), ...
                              @(x1, x2) gaussianKernel(x1, x2, train_set(j)));
            predictions = svmPredict(model, Xval);
            prediction_error = mean(double(predictions ~= yval));
            if (prediction_error < error_t)
                  error_t = prediction_error;
                  C = train_set(i);
                  sigma = train_set(j);
            endif

      endfor

endfor



% =========================================================================

end
