
## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        SCC <- readRDS("Source_Classification_Code.rds")
}

totalEmissions <- aggregate(NEI$Emissions ~ NEI$year, NEI, sum)
colnames(totalEmissions) <- c("year", "Emissions")

png("plot1.png", width = 480, height = 480)
plot(totalEmissions$year, 
     as.numeric(as.character(totalEmissions$Emissions)), 
     type = "o",
     main = "total PM2.5 emission in the US from 1999 to 2008",
     xlab = "year",
     ylab = "total PM2.5",
     col = "steelblue",
     lwd = 2,
     cex = 1.5,
     pch = 19)

dev.off()
