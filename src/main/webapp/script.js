let map;

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
}

window.initMap = initMap;