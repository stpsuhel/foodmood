
$(document).ready(function () {

    let productId = $(".checkProduct").attr("id");
    console.log(productId);
    if(productId==null){
        $('.disableProductInfo').prop('disabled', false);
        $('#updateProduct').hide();
    }else{
        $('.hideSaveButton').hide();
    }

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Store",
        width: '100%',
        dropdownAutoWidth: true
    });


    $("#cancel-button").click(function () {

        if(productId==null){
            $(location).attr('href', './product-information');
        }else{
            $('.hideSaveButton').hide();
            $('.disableProductInfo').prop('disabled', true);
            $('#updateProduct').show();
        }
    });

    $('#updateProduct').click(function () {
        $('.hideSaveButton').show();
        $('.disableProductInfo').prop('disabled', false);
        $('#updateProduct').hide();
    })

})


