let map, userPos,infoWindow, stationMarker;
// var transportMarker = [];
// var showTransportMarker = false;

async function initMap() {
  userPos = {
    lat: 40.7614,
    lng: -73.9776,
  };
  
  map = new google.maps.Map(document.getElementById("map"), {
    center: userPos,
    zoom: 12,
    gestureHandling: "cooperative",
  });
  
  var marker = new google.maps.Marker({
    position: userPos,
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
        userPos = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };

        map.setCenter(userPos);
        marker.setPosition(userPos);
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
    userPos = {
      lat: marker.getPosition().lat(),
      lng: marker.getPosition().lng(),
    };
  });

// console.log(marker.getPosition());

  //turning on the transit layer
  const transitLayer = new google.maps.TransitLayer();
  transitLayer.setMap(map);

  var customStyled = [
    {
      featureType: "all",
      elementType: "labels",
      stylers: [
        { visibility: "simplified" }
      ]
    }
  ];  
  map.set('styles',customStyled);

    const params = new URLSearchParams();
    let currpos = (userPos.lat+" "+userPos.lng).toString();
    params.append("text-input", currpos);
    const response = await fetch("/stops", {method: 'POST', body: params});
    const stopsJson = await response.json(); 
    for (var i = 0; i < stopsJson.length; i++) {
        createStation(stopsJson[i]);
    }
    console.log(stopsJson);
}

function createDiv(container, elem, className, containerID) {
  container.className = className;
  container.append(elem);
  document.getElementById(containerID).append(container)
}

function getStationLines(routes,stationLines) {
  for (var i = 0; i < routes.length; i++) {
    stationLines.push(routes[i]);
  }
}

function createStation(station) {
  tmpl = document.getElementById("currentStation");
  elem = tmpl.content.cloneNode(true);
  elem.getElementById("stationName").innerText = station["stationName"];
  
  var stationLines = [];
  getStationLines(station["routes"],stationLines)
  elem.getElementById("stationLines").innerText = stationLines.toString();
  var container = document.createElement("div");
  container.onclick = function() {showLines(station)};
  createDiv(container, elem, "transitLines", "CurrentStationContainer");
}

function createLines(station) {
  tmpl = document.getElementById("transitLine");
    elem = tmpl.content.cloneNode(true);
    elem.getElementById("lineName").innerText = station;
    elem.getElementById("routeDescription").innerText = "NA";
    elem.getElementById("backButton").onclick = function() {showLines(station)};
    var container = document.createElement("div");
    createDiv(container, elem, "transitLines", "transitLineContainer");
}

function showLines(station) {
  var currentStation = document.getElementById("CurrentStationContainer");

  if (currentStation.style.display == "none") {
    currentStation.style.display = "block";
    // if (showTransportMarker) {
    //   removeTranportMarker();
    // }
    stationMarker.setMap(null);
    map.setCenter(userPos);
    map.setZoom(12);
    document.getElementById("transitLineContainer").innerHTML = "";
  } 
  else {
    currentStation.style.display = "none";

    var stationPos = {
        lat: station["latitude"],
        lng: station["longitude"],

    };

    for (var i = 0; i < station["routes"].length; i++) {
        const params = new URLSearchParams();
        // var paramInfo = station["id"]+" "+station["type"]+" "+station["routes"];
        var paramInfo = "300000 BUS B1";
        params.append("text-input", paramInfo);        
        // const response;
        // ( async () => response = await fetch("/vehicles", {method: 'POST', body: params}) )();
        (async function () {
            const response2 = await fetch("/vehicles", {method: 'POST', body: params});
            const routesJson = await response2.json(); 
            createLines(station["routes"][i]);
            console.log("Routes:");
            console.log(routesJson);
        }())
    
    }

    // for (var i = 0; i < station["routes"].length; i++) {
    //   createLines(station["routes"][i]);
    // }
    createStationMarker(stationPos);
    map.setCenter(stationPos);
    map.setZoom(15);
  }
}

function createStationMarker (stationLocation) {
  stationMarker = new google.maps.Marker({
    position: stationLocation,
    map: map,
    animation: google.maps.Animation.DROP,
  });
}

window.initMap = initMap;