'use strict';

function myMap() {
    var amsterdam = new google.maps.LatLng(40.730610, -73.935242);

    var mapCanvas = document.getElementById("map");
    var mapOptions = {center: amsterdam, zoom: 7};
    var map = new google.maps.Map(mapCanvas,mapOptions);

    var myCity = new google.maps.Circle({
        center: amsterdam,
        radius: 40000,
        strokeColor: "rgb(53,52,154)",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "rgba(23,163,255,0.96)",
        fillOpacity: 0.4
    });
    myCity.setMap(map);
}