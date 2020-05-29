library(datasets)

df <- read.table("household_power_consumption.txt", sep=";", header = TRUE)
df$Date <- as.Date(df$Date, format="%d/%m/%Y")
df$Time <- format(df$Time, "%T")
global_active_power <- subset(df, Date == "2007-02-01" | Date == "2007-02-02")$Global_active_power

png("plot1.png", width = 480, height = 480)
hist(as.numeric(as.character(activePowerVals)), 
     col = "red",
     main = "Global Active Power",
     xlab = "Global Active Power (kilowatts)")

dev.off()

     