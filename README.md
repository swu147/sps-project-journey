# [Journey](https://summer22-sps-5.appspot.com/)

<!-- <img src='https://github.com/swu147.sps-project-journey/walkthrough.gif' width='' alt='Video Walkthrough' /> -->

Webapp that gives Metropolitan Transportation Authority (MTA) transit data based on location. 
- Shows nearby bus stops and train stations on a map. 
  - Stops include all routes that visit that stop.
- Shows all vehicles on a given route chosen by the user on live map.
  - Vehicles have estimated arrival time and current distance from stop. 

## Contributors
- [Adrian Mok](https://adrian-mok15.github.io/) - [Adrian-Mok15](https://github.com/Adrian-Mok15)
- [Pavel Yatsuk]() - [pavlik-y](https://github.com/pavlik-y)
- [Sara Wi]() - [swu147](https://github.com/swu147)
- [Yiling Li]() - [floatingtortoise](https://github.com/floatingtortoise)

  
## Project Description 

To fetch MTA data, we had the option to either write our own library, or use a third party library. Since it seemed out of the scope of this project to learn about GTFS and Protobuf files, we opted to use the third party library [OneMTA](https://github.com/KatsuteDev/OneMTA) by [KatsuteDev](https://github.com/KatsuteDev)

This was a big risk for us since we did not know if this wrapper/library truely worked, nor did we know how it would work within our webapp. This would mean we would have to sink a lot of resources into getting this aspect of the project working as this was the basis of our entire project. We expected that learning about GTFS, Protobufs, and others to put together our own library would take too much time with respect to the timeframe we were given for this project. In the end, using this library did cause quite a few compatibility issues for us, but we were able to solve them. This affords us an ease of integrating future features from the MTA api.

In order to display the stops and stations for the user, we compiled the static data given by the MTA. We then used the Haversine formula to get stops near the user. 
These stops are then sent back to client side to be displayed on the map as well as sidebar. 

To display a map, we used the Google Maps Api. This allowed us to use the longitude and latitude given from the servlet to put markers on the map representing bus stops/ train stations as well as any other objects we wanted. 

#### Render nearby stops -> Select a stop
![](https://github.com/swu147/sps-project-journey/blob/finalDevelopment3/SelectStop.gif)

#### Render routes of a stop -> Select a route -> Render vehicles on the route
<!-- ![](https://github.com/swu147/sps-project-journey/SelectRoute.gif) -->
<img src='https://github.com/swu147/sps-project-journey/blob/finalDevelopment3/SelectRoute.gif' />

## Features we want to implement

In no particular order: 

- Real time alerts on a given route chosen by the user.
- Support different kinds of transit such as LIRR, MNR, and ferries
- Integrate more google maps functionaliy such as planning a trip
- UI changes
- Refreshing realtime data
- Add more data to subway vehicles
- Include destinations for subway routes. 


