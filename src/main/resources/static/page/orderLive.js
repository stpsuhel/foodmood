$(document).ready(function () {

    $(".order-reject").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");

        if (product && order) {
            let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

            console.log(productList)

        }
    })
    $(".order-accept").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");

        if (product && order) {
            let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

            console.log(productList)


            var body ={
                orderId:Number(order),
                productList
            }

            $.ajax({
                url: './accept',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(body),
                success: function (data, textStatus, jQxhr) {


                    console.log(data)

                },
                error: function (jqXhr, textStatus, errorThrown) {
                    console.log(errorThrown);
                }
            });

        }
    })
})
