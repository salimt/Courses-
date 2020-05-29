library(datasets)

df <- read.table("household_power_consumption.txt", sep=";", header = TRUE)
df$date.time <- as.POSIXct(paste(df$Date, df$Time), format = "%d/%m/%Y %T")

df$Date <- as.Date(df$Date, format="%d/%m/%Y")
df$Time <- format(df$Time, "%T")
subDf <- subset(df, Date == "2007-02-01" | Date == "2007-02-02")

sub_1 <- as.numeric(as.character(subDf$Sub_metering_1))
sub_2 <- as.numeric(as.character(subDf$Sub_metering_2))
sub_3 <- as.numeric(as.character(subDf$Sub_metering_3))

png("plot3.png", width = 480, height = 480)
plot(subDf$date.time, sub_1, col = "black", type = "l", 
     xlab = "", 
     ylab = "Energy sub metering")
lines(subDf$date.time, sub_2, col = "red", type = "l")
lines(subDf$date.time, sub_3, col = "blue", type = "l")
legend('topright','groups'
       ,c("Sub_metering_1","Sub_metering_2","Sub_metering_3"), 
       lty = 1,
       col=c('black','red','blue'))
dev.off()
