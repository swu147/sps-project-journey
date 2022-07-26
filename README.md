# Journey

Webapp that gives Metropolitan Transportation Authority (MTA) transit data based on location. 
- Shows nearby bus stops and train stations on a map. 
  - Stops include all routes that visit that stop.
- Shows all vehicles on a given route chosen by the user on live map.
  - Vehicles have estimated arrival time and current distance from stop. 
  
## Project Description 

To fetch MTA data, we had the option to either write our own library, or use a third party library. Since it seemed out of the scope of this project to learn about GTFS and Protobuf files, we opted to use the third party library [OneMTA](https://github.com/KatsuteDev/OneMTA) by [KatsuteDev](https://github.com/KatsuteDev)

This was a big risk for us since we did not know if this wrapper/library truely worked, nor did we know how it would work within our webapp. This would mean we would have to sink a lot of resources into getting this aspect of the project working as this was the basis of our entire project. We expected that learning about GTFS, Protobufs, and others to put together our own library would take too much time with respect to the timeframe we were given for this project. In the end, using this library did cause quite a few compatibility issues for us, but we were able to solve them. 

In order to display the stops and stations for the user, we compiled the static data given by the MTA, and used the Haversine formula to get stops near the user. 
These stops are then sent back to client side to be displayed on the map as well as sidebar. 

To display a map, we used the Google Maps Api. This allowed us to use the longitude and latitude given from the servlet to put markers on the map representing bus stops/ train stations as well as any other objects we wanted. 

## Features we want to implement

In no particular order: 

- Real time alerts on a given route chosen by the user.
- Support different kinds of transit such as LIRR, MNR, and ferries
- Integrate more google maps functionaliy such as planning a trip
- UI changes
- Refreshing realtime data
- Cleaning up servers for faster load times


