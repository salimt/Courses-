library(datasets)

df <- read.table("household_power_consumption.txt", sep=";", header = TRUE)
df$date.time <- as.POSIXct(paste(df$Date, df$Time), format = "%d/%m/%Y %T")

df$Date <- as.Date(df$Date, format="%d/%m/%Y")
df$Time <- format(df$Time, "%T")
subDf <- subset(df, Date == "2007-02-01" | Date == "2007-02-02")


png("plot2.png", width = 480, height = 480)
plot(subDf$date.time, 
     as.numeric(as.character(subDf$Global_active_power)), 
     type = "l",
     xlab = "",
     ylab = "Global Active Power (kilowatts)")
dev.off()
