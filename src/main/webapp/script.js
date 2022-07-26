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

    //params.append("text-input", "40.750580 -73.993584");
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

function getStationLines(station,stationLines) {
  for (var i = 0; i < station.length; i++) {
    stationLines.push(station[i]["line"]);
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
    elem.getElementById("lineName").innerText = station["line"];
    elem.getElementById("routeDescription").innerText = station["destination"];
    elem.getElementById("backButton").onclick = function() {showLines(station)};
    // elem.getElementById("showTransport").onclick = function() {showTransportLocation(station)};
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
    for (var i = 0; i < station["routes"].length; i++) {
      createLines(station["routes"][i]);
    }
    createStationMarker(station["pos"]);
    map.setCenter(station["pos"]);
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

// function showTransportLocation(station) {
//   if (!showTransportMarker) {
//     var bus = [
//       {
//         position : {
//           lat: 40.754672, //info that will be fetched
//           lng: -73.986754
//         }
//       },
//       {
//         position : {
//           lat: 40.754672, 
//           lng: -73.97881
//         }
//       }
//     ];
//     for (var i = 0; i < bus.length; i++) {
//       createTransportMarker(bus[i].position);
//     }
//     showTransportMarker = true;
//   }
//   else {
//     removeTranportMarker();
//   }
  
// }

// //placeholder but this would take in the type of trans for marker shape and the lat and long of the bus
// function createTransportMarker (transportPosition) {
//   const transportImage = "https://icons.iconarchive.com/icons/martz90/circle-addon2/48/public-transport-icon.png";
//   newTransportMarker = new google.maps.Marker({
//     position: transportPosition,
//     map: map,
//     animation: google.maps.Animation.DROP,
//     icon: transportImage,
//   });
//   transportMarker.push(newTransportMarker);
// }

// function removeTranportMarker(){
//   for(var i=0; i<transportMarker.length; i++){
//     transportMarker[i].setMap(null);
//   }
//   map.setCenter(userPos);
//   map.setZoom(12);
//   showTransportMarker = false;
// }

window.initMap = initMap;