library(ggplot2)
library(tidyverse)

## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        SCC <- readRDS("Source_Classification_Code.rds")
}

subDf <- subset(NEI, (fips == "24510" | fips == "06037") & type == "ON-ROAD")
totalEmissions <- aggregate(subDf$Emissions ~ subDf$year + subDf$fips , subDf, sum)
colnames(totalEmissions) <- c("year", "fips", "Emissions")

png("plot6.png", width = 480, height = 480)
ggplot(totalEmissions, aes(year, Emissions, colour=factor(fips))) + 
        geom_line(size=2) +
        labs(color = "city names") +
        ggtitle("Comparison of motor vehicle-related \n emission changes from 1999-2008 \n between Los Angeles County and Baltimore City") +
        theme(plot.title = element_text(hjust = 0.5)) +
        scale_color_manual(labels = c("Los Angeles", "Baltimore City"), values = c("steelblue", "orange"))
dev.off()
