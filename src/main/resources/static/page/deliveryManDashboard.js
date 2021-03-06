
let map;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 24.749181, lng: 90.418864},
        zoom: 13

    });
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

    $(".row-store-order").click(function () {
        let orderId = $(this).attr('id');

        console.log(orderId);

        let latitude = Number($('#latitude'+orderId).attr('data'));
        let longitude = Number($('#longitude'+orderId).attr('data'))

        console.log(typeof latitude)

        let center = new google.maps.LatLng(latitude, longitude);
        map.panTo(center);

        let marker = new google.maps.Marker({
            position: {lat: latitude, lng: longitude},
            map
        });
        console.log(longitude + latitude)
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


