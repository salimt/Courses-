## returns two values, first one is total number of non-missing values 
## for the giving column, the second one is sum of the column without NA
getSum <- function(directory, pollutant=NULL, id, trs=NULL) {
  result = 0
  totalRow = 0
  x <- c("./", directory, "/", formatC(id, width = 3,flag = 0), ".csv")
  path <- paste(x, collapse="")
  # Import the data
  data <- read.csv(file = path)
  if(!is.null(pollutant)) {
    use <- !is.na(data[, pollutant]) ## Find non-missing values
    result <- sum(data[use, pollutant]) ## sum the all column values
  } else {
    use <- complete.cases(data)
    if(!is.null(trs) && sum(use)>=trs){
      return(cor(data[use, "sulfate"], data[use, "nitrate"]))
    } else {
      return(0)
    }

  }
  
  totalRow <- sum(use) ## count the rows without NA

  return(c(result, totalRow))
}

## returns the mean of the pollutant across all of the monitors, 
## ignoring any missing values coded as NA
pollutantmean <- function(directory, pollutant, id=1:332) {
  result = 0
  totalRow = 0
  val = 0
  for (i in id) {
    val <- getSum(directory, pollutant, id=i)
    result <- result + val[1] ## sum the all column values
    totalRow <- totalRow + val[2] ## count the rows without NA
    ## print(val[2])
  }
  print(result/totalRow)
}

## function returns a data frame where the first 
## column is the name of the file and the second column is 
## the number of complete cases
complete <- function(directory, id=1:332) {
  vals = c()
  for (i in 1:length(id)) {
    vals[i] <- getSum(directory, id=id[i])[2]
  }
  f <- matrix(c(id,vals), ncol=2, nrow=length(id))
  rownames(f) <- 1:length(id)
  colnames(f) <- c("id", "nobs")
  print(f)
}

## function returns a vector of correlations for the monitors
## that meet the threshold requirement. If no monitors meet the
## threshold requirement, then the function should return a numeric
## vector of length 0
corr <- function(directory, threshold=0) {


  data <- c()


  for(i in 1:332) {

    data[length(data)+1] <- getSum(directory, id=i, trs=threshold)


  }
  print(data)
}

# pollutantmean("specdata", "sulfate", 1:10)
# pollutantmean("specdata", "nitrate", 70:72)
# pollutantmean("specdata", "sulfate", 34)
# pollutantmean("specdata", "nitrate")
# 
# cc <- complete("specdata", c(6, 10, 20, 34, 100, 200, 310))
# print(cc$nobs)
# 
# cc <- complete("specdata", 54)
# print(cc$nobs)
# 
# # RNGversion("3.5.1")  
# set.seed(42)
# cc <- complete("specdata", 332:1)
# use <- sample(332, 10)
# print(cc[use, "nobs"])
# 
# cr <- corr("specdata")
# cr <- sort(cr)
# RNGversion("3.5.1")
# set.seed(868)
# out <- round(cr[sample(length(cr), 5)], 4)
# print(out)

# cr <- corr("specdata", 129)                
# cr <- sort(cr)                
# n <- length(cr)    
# RNGversion("3.5.1")
# set.seed(197)                
# out <- c(n, round(cr[sample(n, 5)], 4))
# print(out)

# cr <- corr("specdata", 2000)                
# n <- length(cr)                
# cr <- corr("specdata", 1000)                
# cr <- sort(cr)
# print(c(n, round(cr, 4)))

