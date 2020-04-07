# Human Activity Recognition Using Smartphones Dataset
#### Version 1.0

## Summary

The experiments have been carried out with a group of 30 volunteers within an age bracket of 19-48 years. Each person performed six activities (WALKING, WALKING_UPSTAIRS, WALKING_DOWNSTAIRS, SITTING, STANDING, LAYING) wearing a smartphone (Samsung Galaxy S II) on the waist. Using its embedded accelerometer and gyroscope, we captured 3-axial linear acceleration and 3-axial angular velocity at a constant rate of 50Hz. The experiments have been video-recorded to label the data manually. The obtained dataset has been randomly partitioned into two sets, where 70% of the volunteers was selected for generating the training data and 30% the test data. 

## Dataset

The script simply works in a way that merges 7 different messy datasets into one tidy txt file. Firstly, reads the giving sets below:

- 'features.txt': List of all features

- 'activity_labels.txt': Links the class labels with their activity name

- 'train/X_train.txt': Training set

- 'train/y_train.txt': Training labels

- 'test/X_test.txt': Test set

- 'test/y_test.txt': Test labels

- 'train/subject_train.txt': Each row identifies the subject who performed the activity for each window sample. Its range is from 1 to 30. 

After using features as column names and other sets as values, we do take the the columns that includes the giving activities' mean and standard deviation for the given subjects, we do store them into our new set to save at the end. For each record it is provided:

- Triaxial acceleration from the accelerometer (total acceleration) and the estimated body acceleration.
- Triaxial Angular velocity from the gyroscope. 
- A 561-feature vector with time and frequency domain variables. 
- Its activity label. 
- An identifier of the subject who carried out the experiment.