let OrderDetails = {

    getPreviousOrder: function (fromMonth) {

        let settings = {
            "async": true,
            "crossDomain": false,
            "url": "./order-details?fromMonth=" + fromMonth,
            "method": "GET",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache",
            },
            "processData": false,
        }

        $.ajax(settings).done(function (response) {
            console.log(response);

            if(response.isResultAvailable && response.isSuccessful) {

                let orderDataTable = '';
                $.each( response.result, function( key, orderItem ) {

                    orderDataTable += '<tr>';
                    orderDataTable += '<td>'+orderItem.orderId+'</td>';
                    orderDataTable += '<td style="cursor: pointer">';
                    orderDataTable += '<div class="card-list">';

                    $.each( orderItem.itemList, function( key, productItem ) {
                        orderDataTable += '<div class="item-list">';
                        orderDataTable += '<div class="info-user">';
                        if(productItem.hasDiscount){
                            orderDataTable += '<pre class="username">';
                            orderDataTable += productItem.name  + ' x ' + productItem.quantity;
                            orderDataTable += ' (' + productItem.priceDiscount + ' BDT) = ';
                            orderDataTable += ' (' + productItem.priceDiscount * productItem.quantity + ') BDT</pre>';

                        }else {
                            orderDataTable += '<pre class="username">';
                            orderDataTable += productItem.name  + ' x ' + productItem.quantity;
                            orderDataTable += ' (' + productItem.price + ' BDT) = ';
                            orderDataTable += productItem.price * productItem.quantity + ' BDT</pre>';
                        }
                        orderDataTable += '</div></div>';
                    });
                    orderDataTable += '</div>';
                    orderDataTable += '<div class="clearfix"></div></td>';
                    let date = moment(orderItem.orderDate, "YYYYMMDD");
                    let dateFormate = "No Date";
                    console.log(orderItem.orderDate);
                    console.log(date);
                    if(date.isValid()){
                        dateFormate = date.format("DD MMM, YYYY");
                        console.log(dateFormate);
                    }
                    orderDataTable += '<td>' + dateFormate +'</td>';
                    orderDataTable += '</tr>';


                });
                $('#showOrderDetails').empty()
                $('#showOrderDetails').append(orderDataTable)
            }
        });
    }
}


$(document).ready(function () {

    $( "#datepicker" ).datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm",
        showButtonPanel: true,
        currentText: "This Month",
    });


    $("#live-Order").DataTable({
        "pageLength": 50,
        "language": {
            "emptyTable": "No order found"
        }
    });

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Store",
        width: '100%',
        dropdownAutoWidth: true
    });

    let fromMonth = "";

    $('#show-previous-order').click(function () {
        let selectedMonth = $('#datepicker').val().substr(5,2)
        let selectedType = $('#selected-type').val();

        fromMonth = selectedMonth + selectedType
        console.log(fromMonth)

        OrderDetails.getPreviousOrder(fromMonth)
    })
    OrderDetails.getPreviousOrder(fromMonth)
})
