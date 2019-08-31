
$(document).ready(function () {

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Grade",
        width: '100%',
        dropdownAutoWidth: true
    });


    $("#product-table").DataTable({
    });

    $(".row-store").click(function (e) {
        let productId = $(this).attr("id");

        if($(e.target).hasClass('ignoreClick')) {

            let freeDelivery = $(e.target).attr("deliveryType");
            console.log(freeDelivery)
            let body = {
                freeDelivery
            }
            console.log(body)
            product.saveDeliveryType(body, productId);

            return;
        }
        let isAdmin = $("#isAdmin").attr('content')
        if(isAdmin){
            $(location).attr('href', './update-product?id=' + productId)
        }else {
            $(location).attr('href', './add-product?id=' + productId)
        }
    })

    $("#add-product").click(function (e) {
        $(location).attr('href', './add-product')
    })

})

let product = {
    saveDeliveryType: function(product, productId) {
        $.ajax({
            url: './product/save-free-delivery?id=' + productId,
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(product),
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


