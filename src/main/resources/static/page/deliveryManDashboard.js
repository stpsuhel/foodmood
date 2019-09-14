
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

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Store",
        width: '100%',
        dropdownAutoWidth: true
    });

    $("#map-delivery-table").DataTable({});

    $(".row-store").click(function () {

    })

    // getTodeysOrder.deliveryDashboard()
})

let getTodeysOrder = {
    deliveryDashboard: function() {
        let settings = {
            "async": true,
            "crossDomain": true,
            "url": "../order/todays-order-list",
            "method": "GET",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache",
            }
        }

        $.ajax(settings).done(function (response) {
            console.log(response.result)
            if(response.isResultAvailable && response.isSuccessful) {

                $("#order-delivery-table").DataTable({
                    data: response.result,
                    columns: [
                        { data: 'orderId' },
                        { data: 'orderBy' },
                        {},
                        { data: 'deliveryAddress.addressLineOne' },
                        { data: 'orderDate' },
                        { data: 'orderStatus' },
                        {},
                        {}
                        ]
                });
            }
        });
    }
}

