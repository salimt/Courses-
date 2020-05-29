library(ggplot2)
library(tidyverse)

## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        SCC <- readRDS("Source_Classification_Code.rds")
}

coalDf <- dplyr::filter(SCC, grepl("Coal", EI.Sector))
commonDf <- left_join(coalDf, NEI, by = "SCC")
totalEmissions <- aggregate(commonDf$Emissions ~ commonDf$year, commonDf, sum)
colnames(totalEmissions) <- c("year", "Emissions")

png("plot4.png", width = 480, height = 480)
ggplot(totalEmissions, aes(year, Emissions, fill=factor(year))) + 
        geom_bar(width=2, stat="identity", position="dodge",) +
        labs(color = "year") +
        ggtitle("Change in emissions from coal combustion-related \n sources from 1999-2008") +
        theme(plot.title = element_text(hjust = 0.5))
dev.off()
