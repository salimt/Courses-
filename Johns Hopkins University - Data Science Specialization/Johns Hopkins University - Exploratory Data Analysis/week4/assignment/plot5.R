library(ggplot2)
library(tidyverse)

## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        SCC <- readRDS("Source_Classification_Code.rds")
}

subDf <- subset(NEI, fips == "24510" & type == "ON-ROAD")
totalEmissions <- aggregate(subDf$Emissions ~ subDf$year , subDf, sum)
colnames(totalEmissions) <- c("year", "Emissions")


png("plot5.png", width = 480, height = 480)
ggplot(totalEmissions, aes(year, Emissions, fill=factor(year))) + 
        geom_bar(width=2, stat="identity", position="dodge",) +
        labs(color = "year") +
        ggtitle("Change in emissions from motor vehicle-related \n sources from 1999-2008 in Baltimore City") +
        theme(plot.title = element_text(hjust = 0.5))
dev.off()
