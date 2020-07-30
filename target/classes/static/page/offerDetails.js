$(document).ready(function () {

    let offerId = $('.checkOffer').attr('id');
    console.log(offerId)

    if(offerId == null){
        $('#updateOffer').hide()
        $(".disableProductInfo").prop('disabled', false)
    }else {
        $(".hideSaveButton").hide()
    }

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Store",
        width: '100%',
        dropdownAutoWidth: true
    });

    $("#cancel-button").click(function () {

        if(offerId == null){
            $(location).attr('href', './offer-information');
        }else{
            $('.hideSaveButton').hide();
            $('.disableProductInfo').prop('disabled', true);
            $("#updateOffer").show()
        }

    });

    $('#updateOffer').click(function () {
        $('.hideSaveButton').show();
        $('.disableProductInfo').prop('disabled', false);
        $("#updateOffer").hide()
    })

})
