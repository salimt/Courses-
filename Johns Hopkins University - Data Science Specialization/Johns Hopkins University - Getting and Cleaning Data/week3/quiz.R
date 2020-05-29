### QUESTION 1. Create a logical vector that identifies the households on greater than 10 acres who sold more than $10,000 worth of agriculture products. Assign that logical vector to the variable agricultureLogical. Apply the which() function like this to identify the rows of the data frame where the logical vector is TRUE. which(agricultureLogical) What are the first 3 values that result?

fileURL <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv"
destFile <- tempfile()
download.file(fileURL, destFile, method = "curl")

data <- read.csv(destFile, header = T)

## ACR == 3 - lot size of 10 or more acres
## AGS == 6 - $10000+ of Sales of Agricultural sales

## Creating logical vector
agricultureLogical <- with(data, ACR == 3 & AGS ==6)
## first 3 values of which on that vector
which(agricultureLogical)[1:3]

### ANSWER 1. [1] 125 238 262

### QUESTION 2. Use the parameter native=TRUE to read img file with jpeg package. What are the 30th and 80th quantiles of the resulting data? (some Linux systems may produce an answer 638 different for the 30th quantile)
library(jpeg)

imgURL <- "https://d396qusza40orc.cloudfront.net/getdata%2Fjeff.jpg"
imgFile <- tempfile()
download.file(imgURL, imgFile, method = "curl")

## Read file
img <- readJPEG(imgFile, native = TRUE)
## Get quantiles at 30% and 80%
quantile(img, probs = c(0.3, 0.8))

### ANSWER 2. -15259150 -10575416 

### QUESTION 3. Match the data based on the country shortcode. How many of the IDs match? Sort the data frame in descending order by GDP rank (so United States is last). What is the 13th country in the resulting data frame? 

gdpURL <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv"
eduURL <- "https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FEDSTATS_Country.csv"
gdpFile <- tempfile()
eduFile <- tempfile()

download.file(gdpURL, gdpFile, method = "curl")
download.file(eduURL, eduFile, method = "curl")

## Read 190 ranked countries
gdpData <- read.csv(gdpFile, skip = 5, nrows = 190, stringsAsFactors = F, header = F)
eduData <- read.csv(eduFile, stringsAsFactors = F)

## Subset only needed data, name columns in gdpData and convert GDP Value to numeric
gdpData <- gdpData[, c(1, 2, 4, 5)]
colnames(gdpData) <- c("CountryCode", "Rank", "Country.Name", "GDP.Value")
gdpData$GDP.Value <- as.numeric(gsub(",", "", gdpData$GDP.Value))

## Merge data by country codes
matchedData <- merge(gdpData, eduData, by.x = "CountryCode", by.y = "CountryCode")
## Number of matched countries
dim(matchedData)[1]

## Arrange by GDP rank (descending) and get the name of the 13th country
library(plyr)
arrange(matchedData, desc(Rank))[13, 3]

### ANSWER 3. [1] "St. Kitts and Nevis"

### QUESTION 4. What is the average GDP ranking for the "High income: OECD" and "High income: nonOECD" group?

## Subset "High income: OECD" and calculate the mean GDP Rank
mean(subset(matchedData, Income.Group %in% "High income: OECD", select = c(Rank))$Rank)
## [1] 32.96667

## Subset "High income: nonOECD"
mean(subset(matchedData, Income.Group %in% "High income: nonOECD", select = c(Rank))$Rank)
## [1] 91.91304

#### QUESTION 5. Cut the GDP ranking into 5 separate quantile groups. Make a table versus Income.Group. How many countries are Lower middle income but among the 38 nations with highest GDP?

library(Hmisc)
## Cut Ranks into 5 groups and store as factor variable
matchedData$Rank.Groups = cut2(matchedData$Rank, g = 5)
## Build a table of Income Groups across Rank Groups
table(matchedData$Income.Group, matchedData$Rank.Groups)

##                         [  1, 39) 
## Lower middle income          *5*      
