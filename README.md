# Routing Application
Routing Application using Spring
                
## Contents
* [Requirement](#requirement)
* [Assumptions](#assumptions)
* [Running Tests](#running-tests)
* [Running The Application](#running-the-application)
* [Making Request To The Application](#making-request-to-the-application)
  * [Route](#route)
    * [Get Shortest Route](#get-shortest-route)
    * [Get Shortest Route With Time](#get-shortest-route-with-time) 
  * [Station](#station)
    * [Get All Stations](#get-all-stations)
    * [Get Station By Station Code](#get-station-by-station-code)
    * [Get Stations By Station Name](#get-stations-by-station-name)
* [License](#license)

## Requirement
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

## Assumptions
- minimum station number is 0
- maximum number of stations in a line <= 50 (i.e maximum station number is 50)
- there is no loop in the train line (e.g if there's station from DT1 to DT20, DT1 & DT20 is not connected)
- for simplicity, time period is determined by the start time of the trip and won't change during the trip (e.g if a trip starts at 9AM weekday, it will consider the whole trip to be during peak hours, regardless of the trip duration) 

## Running Tests
To run all tests, you can use the following command.
```
./mvnw clean test
```      

## Running the Application
To run the application, simply run the following command.   
```
./mvnw spring-boot:run
```                                                 

## Making Request To The Application
After Spring finished initializing the application, you can start creating request to the web server at `localhost:8080` via your browser.
Alternatively, you could also do a curl command via terminal.
### Route
#### Get Shortest Route
```                  
http://localhost:8080/api/route?orig={origin_station_name}&dest={destination_station_name}
http://localhost:8080/api/route?orig=Holland%20Village&dest=Bugis                        
curl --location --request GET 'http://localhost:8080/api/route?orig=Holland%20Village&dest=Bugis' 
```  
             
#### Get Shortest Route With Time
Parameter `time` needs to be in the following format `yyyy-MM-dd'T'HH:mm`.
```                       
http://localhost:8080/api/route/time?orig={origin_station_name}&dest={destination_station_name}&time={departure_time}                                                        
http://localhost:8080/api/route/time?orig=Boon Lay&dest=Little India&time=2020-10-19T8:00
curl --location --request GET 'http://localhost:8080/api/route/time?orig=Boon%20Lay&dest=Little%20India&time=2020-10-19T8:00'
```   

### Station
#### Get All Stations
```
http://localhost:8080/api/station
```

#### Get Station By Station Code
``` 
http://localhost:8080/api/station/{station_code}
http://localhost:8080/api/station/NS1

```

#### Get Stations By Station Name
```
http://localhost:8080/api/station/name/{station_name}
http://localhost:8080/api/station/name/Jurong%20East
``` 

## License
This application is made available under the [MIT License](https://opensource.org/licenses/MIT).
