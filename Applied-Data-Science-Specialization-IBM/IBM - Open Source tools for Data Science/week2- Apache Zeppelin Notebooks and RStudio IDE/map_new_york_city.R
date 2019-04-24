#Import the leaflet library for plotting maps in R
library(leaflet)

#If the above line of code returns an error as in: 
#  "Error in library(leaflet):there is no package called 'leaflet'"
#Then run the follow command to install the package:
install.packages("leaflet")

#Important! After installing, go back to the top and run `library(leaflet)` to load the library

#New York GPS
nyc_latitude = 40.7128
nyc_longitude = -74.0060

#Create a map
m <- leaflet() %>% setView(lng = nyc_longitude, 
                           lat = nyc_latitude, 
                           zoom = 12)
m %>% addTiles()
