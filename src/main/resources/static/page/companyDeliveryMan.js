
$(document).ready(function () {

    let deliveryManId = $(".checkDeliveryMan").attr("id");
    console.log(deliveryManId)

    if(deliveryManId==null){
        $('.disableProductInfo').prop('disabled', false);
        $('#updateDeliveryMan').hide();
    }else{
        $('.hideButton').hide();
        $('.hideField').hide();
    }

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Grade",
        width: '100%',
        dropdownAutoWidth: true
    });


    $("#cancel-button").click(function () {

        if(deliveryManId==null){
            $(location).attr('href', './delivery-man-information');
        }else{
            $('.hideButton').hide();
            $('.disableProductInfo').prop('disabled', true);
            $('#updateDeliveryMan').show();
        }
    });

    $('#updateDeliveryMan').click(function () {
        $('.hideButton').show();
        $('.disableProductInfo').prop('disabled', false);
        $('#updateDeliveryMan').hide();
    })

})


