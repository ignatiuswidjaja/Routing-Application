# Routing Application Project
Exercise project for Routing Application using Spring

# Requirement
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
```   
sudo apt-get update
sudo apt-get install default-jre
```
- [[Optional] cURL](https://curl.haxx.se/download.html)
```                
sudo apt update
sudo apt upgrade    
sudo apt install curl
```

# Assumptions
- minimum station number is 0
- maximum number of stations in a line <= 50 (i.e maximum station number is 50)
- there is no loop in the train line (e.g if there's station from DT1 to DT20, DT1 & DT20 is not connected)
- for simplicity, time period is determined by the start time of the trip and won't change during the trip (e.g if a trip starts at 9AM weekday, it will consider the whole trip to be during peak hours, regardless of the trip duration) 

# Application
## Running Tests
To run all tests, you can use the following command.
```
./mvnw clean test
```      

## Running the application
To run the application, simply run the following command.   
```
./mvnw spring-boot:run
```                                                 

## Making request to the application
After Spring finished initializing the application, you can start creating request to the web server at `localhost:8080` via your browser
```                  
http://localhost:8080/api/route?orig=Holland%20Village&dest=Bugis
http://localhost:8080/api/route/time?orig=Boon Lay&dest=Little India&time=2020-10-19T8:00
```               

Alternatively, you could do a curl command via terminal
```
curl --location --request GET 'http://localhost:8080/api/route?orig=Holland%20Village&dest=Bugis' 
curl --location --request GET 'http://localhost:8080/api/route/time?orig=Boon%20Lay&dest=Little%20India&time=2020-10-19T8:00'
```

# License
This application is made available under the [MIT License](https://opensource.org/licenses/MIT).
