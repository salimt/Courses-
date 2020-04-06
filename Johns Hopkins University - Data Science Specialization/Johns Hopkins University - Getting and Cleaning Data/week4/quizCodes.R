# The American Community Survey distributes downloadable data about United States communities. Download the 2006 microdata survey about housing for the state of Idaho using download.file() from here:
#         
#         https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2Fss06hid.csv
# 
# and load the data into R. The code book, describing the variable names is here:
#         
#         https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FPUMSDataDict06.pdf
# 
# Apply strsplit() to split all the names of the data frame on the characters "wgtp". What is the value of the 123 element of the resulting list?

data <- read.csv("getdata_data_ss06hid.csv")
strsplit(names(data), "wgtp")[[123]] ## ""   "15"


# Load the Gross Domestic Product data for the 190 ranked countries in this data set:
#         
#         https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv
# 
# Remove the commas from the GDP numbers in millions of dollars and average them. What is the average?
#         
#         Original data sources:
#         
#         http://data.worldbank.org/data-catalog/GDP-ranking-table


# In the data set from Question 2 what is a regular expression that would 
# allow you to count the number of countries whose name begins with "United"? 
#         Assume that the variable with the country names in it is named countryNames. 
# How many countries begin with United?
        
gdpData <- read.csv("getdata_data_GDP.csv", skip = 4, nrows = 190)
gdpData$X.4 <- as.numeric(gsub(",","",gdpData$X.4))
print(mean(gdpData$X.4)) ## 377652.4
grep("^United", gdpData$X.3) # 1  6 32 -> 3

# Load the Gross Domestic Product data for the 190 ranked countries in this data set:
#         
#         https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FGDP.csv
# 
# Load the educational data from this data set:
#         
#         https://d396qusza40orc.cloudfront.net/getdata%2Fdata%2FEDSTATS_Country.csv
# 
# Match the data based on the country shortcode. Of the countries for which the end of the fiscal year is available, how many end in June?
#         
#         Original data sources:
#         
#         http://data.worldbank.org/data-catalog/GDP-ranking-table
# 
# http://data.worldbank.org/data-catalog/ed-stats


countryStats <- read.csv("getdata_data_EDSTATS_Country.csv")
mergedSet <- merge(gdpData, countryStats, by.x="X", by.y="CountryCode")
grep("^Fiscal year end: June", mergedSet$Special.Notes) ## 13

# You can use the quantmod (http://www.quantmod.com/) package to get historical 
# stock prices for publicly traded companies on the NASDAQ and NYSE. 
# Use the following code to download data on Amazon's stock price and get
# the times the data was sampled.
# How many values were collected in 2012? How many values were collected on Mondays in 2012?


library(quantmod)
amzn = getSymbols("AMZN",auto.assign=FALSE)
sampleTimes = index(amzn)
print(sum(as.numeric(format(sampleTimes, "%Y"))==2012)) ## 250
print(sum(as.numeric(format(sampleTimes, "%Y"))==2012 & weekdays(sampleTimes)=="Monday")) ##??? 47

