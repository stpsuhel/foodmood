
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

    $("#order-delivery-table").DataTable({});

    $(".row-store").click(function () {

    });

    $('.assignDeliveryMan').click(function () {
        let orderId = $(this).attr('id');
        let deliveryManId = $('#deliveryMan'+orderId).val();

        console.log(orderId+', '+deliveryManId)

        let body = {
            orderId, deliveryManId
        }

        assign.deliveryMan(body)
    })
})

let assign = {
    deliveryMan: function(deliveryMan) {
        $.ajax({
            url: '../order-delivery/assign-delivery-man-order',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(deliveryMan),
            success: function (data, textStatus, jQxhr) {
                if (data.resultAvailable && data.successful) {
                    window.location.reload(true);
                }
            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}


