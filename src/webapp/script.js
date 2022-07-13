let map, infoWindow;

function initMap() {
  var pos = {
    lat: 40.730610,
    lng: -73.935242,
  };
  
  map = new google.maps.Map(document.getElementById("map"), {
    center: pos,
    zoom: 12,
    gestureHandling: "cooperative",
  });
  
  var marker = new google.maps.Marker({
    position: pos,
    map: map,
    draggable:true,
    animation: google.maps.Animation.DROP,
    icon: {
      path: google.maps.SymbolPath.CIRCLE,
      scale: 10,
      fillOpacity: 1,
      strokeWeight: 2,
      fillColor: '#5384ED',
      strokeColor: '#ffffff',
    },
  });

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        pos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };

        map.setCenter(pos);
        marker.setPosition(pos);
        map.setZoom(15);
      },
    );
  } 
  else {
    // Browser doesn't support Geolocation
    alert("Error: Your browser doesn't support geolocation.");
  }

  //marker dragged event gives new position
  google.maps.event.addListener(marker,'dragend',function(event) {
    var pos = {
      lat: marker.getPosition().lat(),
      lng: marker.getPosition().lng(),
    };
  });

  const transitLayer = new google.maps.TransitLayer();
  transitLayer.setMap(map);

  //TO DO LATER: WRITE AN ALGO THAT CAN LOOP THRU ALL THE LINES TO CREATELINES NEAR THE USER'S LOCATION

  const station = {
    name: "Times Square",
    route: "Astoria",
    position: {
      lat: 40.7559,
      lng: -73.9871,
    },
    line: "1",
  };
  createLines(map, station.line, station.position, station.name, station.route);
  createLines(map, station.line, station.position, station.name, station.route);

}

function createLines(map, line, pos, name, route) {
  tmpl = document.getElementById("nearbyStops");
  elem = tmpl.content.cloneNode(true);
  elem.getElementById("lineName").innerText = line;
  elem.getElementById("routeDescription").innerText = route;
  elem.getElementById("stationName").innerText = name;

  var container = document.createElement("div");
  container.className = "transitLines";
  container.append(elem);
  container.onclick = function() {toggleDisplay(map, line, pos, name, route)};

  document.getElementById("nearbyStopsContainer").append(container)
}

function createArrivalTime(map, line, pos, name, route) {
  tmpl = document.getElementById("transitTimes");
  elem = tmpl.content.cloneNode(true);
  elem.getElementById("lineName").innerText = line;
  elem.getElementById("routeDescription").innerText = route;
  elem.getElementById("stationName").innerText = name;

  var temp = document.createElement("div");
  temp.className = "transitLines";
  temp.append(elem);
  temp.onclick = function() {toggleDisplay(map, line, pos, name, route)};

  document.getElementById("transitTimesContainer").append(temp)
}

function toggleDisplay(map, line, pos, name, route) {
  var nearStopsID = document.getElementById("nearbyStopsContainer");
  var transTimeID = document.getElementById("transitTimesContainer");

  if (nearStopsID.style.display == "none") {
    nearStopsID.style.display = "block";
    document.getElementById("transitTimesContainer").innerHTML = "";

  } else {
    nearStopsID.style.display = "none";
    createArrivalTime(map, line, pos, name, route);
  }
}

window.initMap = initMap;