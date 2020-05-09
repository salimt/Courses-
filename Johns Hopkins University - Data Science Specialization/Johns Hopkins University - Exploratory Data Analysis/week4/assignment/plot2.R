## This first line will likely take a few seconds. Be patient!
if(!exists("NEI")) {
        NEI <- readRDS("summarySCC_PM25.rds")
}
if(!exists("SCC")) {
        NEI <- readRDS("Source_Classification_Code.rds")
}

subDf <- subset(NEI, fips == "24510")
totalEmissions <- aggregate(subDf$Emissions ~ subDf$year, subDf, sum)
colnames(totalEmissions) <- c("year", "Emissions")


png("plot2.png", width = 480, height = 480)
plot(totalEmissions$year,
     totalEmissions$Emissions,
     type = "o",
     xlab = "year",
     ylab = "total PM2.5",
     main = "Emissions in the Baltimore City, Maryland from 1999 to 2008",
     col = "forestgreen",
     pch = 19,
     lwd = 2,
     cex = 1.5)
dev.off()
