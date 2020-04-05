data <- read.csv("getdata_data_ss06hid.csv")
print(which(data$ACR==3 & data$AGS==6))





library(jpeg)
data <- readJPEG("getdata_jeff.jpg", native = TRUE)
quantile(data, probs = c(0.1, 0.5, 1, 10, 30, 80, NA)/100)





data1 <- read.csv("getdata_data_GDP.csv", nrows=195)
data2 <- read.csv("getdata_data_EDSTATS_Country.csv")
mergedData <- merge(data1, data2, by.x="X", by.y="CountryCode", all=TRUE)

ranking <- as.numeric(levels(mergedData$Gross.domestic.product.2012))[mergedData$Gross.domestic.product.2012]
print(mergedData[order(-ranking),][13,]) ## St. Kitts and Nevis



highOECD <- subset(mergedData, mergedData$Income.Group=="High income: OECD")
highNON <- subset(mergedData, mergedData$Income.Group=="High income: nonOECD")

highOECDMean <- mean(as.numeric(levels(highOECD$Gross.domestic.product.2012))[highOECD$Gross.domestic.product.2012], na.rm=TRUE)
highNONEMean <- mean(as.numeric(levels(highNON$Gross.domestic.product.2012))[highNON$Gross.domestic.product.2012], na.rm=TRUE)

print(highOECDMean) # 32.96667
print(highNONEMean) # 91.91304



d <- na.omit(as.numeric(gsub("([0-9]+).*$", "\\1", levels(na.omit(mergedData$Gross.domestic.product.2012)))))
print(length(d)) ## 189


d3 <- unique(as.numeric(mergedData$Gross.domestic.product.2012), na.rm=TRUE)
rankQuantile <- cut(d3, breaks=c(quantile(d3,  probs = seq(0, 1, by=0.20), 
                             na.rm=TRUE, names=TRUE, include.lowest=TRUE, 
                             right = TRUE, labels=c("1","2","3","4","5")))) # makes quintiles

table(rankQuantile, useNA='ifany') ## 38
rankQuantile 



counter = 0
searchTerm <- "Lower middle income"
for(i in 1:38){
        if(mergedData[which(mergedData$Gross.domestic.product.2012==i),]$Income.Group==searchTerm){
                counter <- counter +1 
                print(mergedData[which(mergedData$Gross.domestic.product.2012==i),]$Long.Name)
        }
}
counter




