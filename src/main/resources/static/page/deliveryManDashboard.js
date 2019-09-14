
let map;
let marker;
let latitude;
let longitude;

function initMap() {
    latitude = 24.757352
    longitude = 90.406580
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: latitude, lng: longitude},
        zoom: 13
    });
    marker = new google.maps.Marker({
        position: {lat: latitude, lng: longitude},
        map
    })
}

$(document).ready(function () {

    $("#map-delivery-table").DataTable({});

    $(".row-store").click(function () {

        latitude = $(this).data("lat");
        longitude = $(this).data("long")
    })



})


