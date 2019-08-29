$(document).ready(function () {

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Store",
        width: '100%',
        dropdownAutoWidth: true
    });

    $("#cancel-button").click(function () {

        $(location).attr('href', './product-information');

        /*if(productId==null){
            $(location).attr('href', './product-information');
        }else{
            $('.hideSaveButton').hide();
            $('.disableProductInfo').prop('disabled', true);
        }*/

    });

    $("#save-button").click(function () {
        let couponText = $("#couponText").val();

        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val();

        console.log(startDate)

        let maxUse = $("#maxUse").val();

        let body = {
            couponText, startDate, endDate, maxUse
        }

        console.log(body)
        /*coupon.saveCoupon(body)*/
    })

    /*$('#updateProduct').click(function () {
        $('.hideSaveButton').show();
        $('.disableProductInfo').prop('disabled', false);
    })*/

})

let coupon = {
    saveCoupon: function(coupon) {
        $.ajax({
            url: './coupon/create-coupon',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(coupon),
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
