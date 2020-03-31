# ## Read the outcome data
# outcome <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
# 
# ## Hospital 30-Day Readmission Rates from Heart Attack
# outcome[, 11] <- as.numeric(outcome[, 11])
# 
# ## introduced NAs are okay
# hist(outcome[, 11], main="Hospital 30-Day Death (Mortality) Rates from Heart Attack", 
#                     xlab="number of heart attacks")



##############################################################

best <- function(state, outcome) {
        ## Read outcome data
        data <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
        vec <- c("heart attack", "heart failure", "pneumonia")
       
         ## Check that state and outcome are valid
        if(state %in% state.abb && outcome %in% vec) {
                ## Return hospital name in that state with lowest 30-day death
                ## rate
                data <- data[data$State == state,] ## choose the hospitals in the states
                if(outcome=="heart attack") {
                        return(data[which.min(data[, 11]),][,2])
                        
                } else if (outcome=="heart failure") {
                        return(data[which.min(data[, 17]),][,2])
                } else {
                        return(data[which.min(data[, 23]),][,2])
                }
        }
}


##############################################################

## function should check the validity of its arguments. 
## If an invalid state value is passed to rankhospital,
## the function should throw an error via the stop function 
## with the exact message \invalid state". If an invalid
## outcome value is passed to rankhospital, the function should 
## throw an error via the stop function with
## the exact message \invalid outcome".
rankhospital <- function(state, outcome, num = "best") {
        ## Read outcome 
        data <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
        
        vec <- c("heart attack", "heart failure", "pneumonia")
        
        ## Check that state and outcome are valid
        if(state %in% state.abb && outcome %in% vec) {
                data <- data[data$State == state,] ## choose the hospitals in the states
                if(num=="best") {num<-1}
                ## Return hospital name in that state with the given rank
                ## 30-day death rate
                getHospital <- function(columnNum) {
                        data[, columnNum] <- as.numeric(data[, columnNum])
                        data <- data[complete.cases(data), ]
                        if(num=="worst") {num<-nrow(data)}
                        mat <- cbind(data[,c(2)], data[,c(columnNum)])
                        if(num > length(mat[,1])){
                                return(NA)
                        } 
                        return(mat[order(as.numeric(mat[,2]), mat[,1]),][num,1])
                }
                
                if (outcome=="heart attack") { getHospital(11) } 
                else if (outcome=="heart failure") { getHospital(17) } 
                else { getHospital(23) }
        }
}


##############################################################

##  function should check the validity of its arguments. 
##  If an invalid outcome value is passed to rankall,
##  the function should throw an error via the stop function with the 
##  exact message \invalid outcome". The num  variable can take 
##  values \best", \worst", or an integer indicating the ranking (smaller numbers
##  are better). If the number given by num is larger than the number of 
##  hospitals in that state, then the function should return NA.
rankall <- function(outcome, num = "best") {
        ## Read outcome 
        data <- read.csv("outcome-of-care-measures.csv", colClasses = "character")
        
        vec <- c("heart attack", "heart failure", "pneumonia")
        hospitalNames <- c()
        
        ## Check that outcome is valid
        if(outcome %in% vec) {
                if(num=="best") {num<-1}
                ## For each state, find the hospital of the given rank
                ## Return a data frame with the hospital names and the
                ## (abbreviated) state name
                for(state in state.abb){
                        hospitalNames[length(hospitalNames)+1] <- rankhospital(state, outcome, num)
                } 
                mat <- cbind(hospitalNames, state.abb)
                colnames(mat) <- c("hospital", "state")
                return(mat[order(as.numeric(mat[,1]), mat[,2]),])
        } 
}



best("SC", "heart attack")
best("NY", "pneumonia")
best("AK", "pneumonia")
rankhospital("NC", "heart attack", "worst")
rankhospital("WA", "heart attack", 7)
rankhospital("TX", "pneumonia", 10)
rankhospital("NY", "heart attack", 7)

r <- rankall("heart attack", 4)
print(r[r[,2]=="HI"])

r <- rankall("pneumonia", "worst")
print(r[r[,2]=="NJ"])

r <- rankall("heart failure", 10)
print(r[r[,2]=="NV"])

