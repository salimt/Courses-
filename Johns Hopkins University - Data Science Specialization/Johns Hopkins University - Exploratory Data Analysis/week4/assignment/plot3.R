library(ggplot2)
library(tidyverse)

## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        NEI <- readRDS("Source_Classification_Code.rds")
}

subDf <- subset(NEI, fips == "24510")
totalEmissions <- aggregate(subDf$Emissions ~ subDf$type + subDf$year, subDf, sum)
colnames(totalEmissions) <- c("type", "year", "Emissions")


png("plot3.png", width = 480, height = 480)
ggplot(subset(totalEmissions, year %in% totalEmissions$year)) + 
        geom_line(size=1.2, aes(type, Emissions, group=year, color=factor(year))) +
        labs(color = "year") +
        ggtitle("Four sources' emissions from 1999-2008 \n for Baltimore City") +
        theme(plot.title = element_text(hjust = 0.5))
dev.off()
        