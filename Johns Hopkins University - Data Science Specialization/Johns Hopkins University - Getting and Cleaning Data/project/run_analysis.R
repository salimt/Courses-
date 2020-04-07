X_train <- read.table("./data/train/X_train.txt")               ## read sthe training set
y_train <- read.table("./data/train/y_train.txt")               ## reads the training labels
subject_train <- read.table("./data/train/subject_train.txt")   ## reads the subject who performed the activity [1-30]


X_test <- read.table("./data/test/X_test.txt")              ## readsthe test set
y_test <- read.table("./data/test/y_test.txt")              ## reads the test labels    
subject_test <- read.table("./data/test/subject_test.txt")  


features <- read.table("./data/features.txt")   ## reads the list of all features
mergedSets <- rbind(X_train, X_test) 
mergedSets$labels <- rbind(y_train, y_test) 
mergedSets$subjects <- rbind(subject_train, subject_test) 
colnames(mergedSets) <- features$V2   ## gives cols the names


df2 <- mergedSets[,grepl("mean()|std()", names(mergedSets))]
df2 <- cbind(subjects = rbind(subject_train, subject_test), labels = rbind(y_train, y_test), df2)
colnames(df2)[1]<-"subject"
colnames(df2)[2]<-"activity"
activity_names <- read.table("./data/activity_labels.txt")   ## reads the class labels with their activity name


library(qdapTools)
df2[,2] <- lookup(df2[,2], activity_names, key.reassign = NULL,
       missing = NA)   ## looks up for activities according to given num

## print(str(df2))

## library("writexl")      
## write_xlsx(df2,"./data/tidy-data.xlsx")    ## writes the new data into xlsx format
write.table(df2,"./data/tidy-data.txt", row.name=FALSE) ## writes the new data into txt format




