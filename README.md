# Routing Application Project
Exercise project for Routing Application using Spring

# Requirement
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later

# Assumptions
- minimum station number is 0
- maximum number of stations in a line <= 50 (i.e maximum station number is 50)
- there is no loop in the train line (e.g if there's station from DT1 to DT20, DT1 & DT20 is not connected)
- for simplicity, time period is determined by the start time of the trip and won't change during the trip (e.g if a trip starts at 9AM weekday, it will consider the whole trip to be during peak hours, regardless of the trip duration) 