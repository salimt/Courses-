data <- read.csv("./data/getdata_data_ss06hid.csv")
print(sum(data["VAL"]==24, na.rm = TRUE))


library(xlsx)
data2 <- read.xlsx("./data/getdata_data_DATA.gov_NGAP.xlsx", 1, rowIndex = 18:23, colIndex = 7:15)
print(data2)
print(sum(data2$Zip*data2$Ext,na.rm=T))


library(XML)
doc <- xmlTreeParse("./data/getdata_data_restaurants.xml", useInternal=TRUE)
rootNode <- xmlRoot(doc)
zips <- xpathSApply(rootNode, "//zipcode", xmlValue)
print(sum(zips==21231))

library("data.table")
DT <- fread("./data/getdata_data_ss06pid.csv")
system.time(tapply(DT$pwgtp15,DT$SEX,mean))
system.time(mean(DT$pwgtp15,by=DT$SEX))
system.time(sapply(split(DT$pwgtp15,DT$SEX),mean))
system.time(DT[,mean(pwgtp15),by=SEX])
system.time(mean(DT[DT$SEX==1,]$pwgtp15)+ mean(DT[DT$SEX==2,]$pwgtp15))
system.time(mean(DT[DT$SEX==1,]$pwgtp15)+ mean(DT[DT$SEX==2,]$pwgtp15))