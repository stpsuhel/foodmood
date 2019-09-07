let OrderAPI = {

    updateOrder: function (body) {

        if (body) {
            $.ajax({
                url: './update-status',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(body),
                success: function (data, textStatus, jQxhr) {


                    if (data['isResultAvailable'] && data['isSuccessful']) {
                        location.reload();
                    }


                },
                error: function (jqXhr, textStatus, errorThrown) {
                    console.log(errorThrown);
                }
            });

        }
    }
}


$(document).ready(function () {


    $(".order-reject").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");
        let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

        let body = {
            orderId: Number(order),
            productList, orderStatus: 10//Order Reject
        };
        OrderAPI.updateOrder(body)
    });
    $(".order-accept").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");
        let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

        let body = {
            orderId: Number(order),
            productList, orderStatus: 2 //Order Accept
        };
        OrderAPI.updateOrder(body)
    })
    $(".order-start-cooking").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");
        let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

        let body = {
            orderId: Number(order),
            productList, orderStatus: 3 //Order start cooking
        };
        OrderAPI.updateOrder(body)
    })

    $(".order-ready-pick-up").click(function () {

        let product = $(this).attr("productlist");
        let order = $(this).attr("orderid");
        let productList = (product + "").toString().replace("[", "").replace("]", "").split(", ");

        let body = {
            orderId: Number(order),
            productList, orderStatus: 4 //Ready for pick up
        };
        OrderAPI.updateOrder(body)
    })
})
