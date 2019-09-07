$(document).ready(function () {

    home.getHomeData()

})

let home = {
    getHomeData: function() {
        let settings = {
            "async": true,
            "crossDomain": false,
            "url": "./home/customer",
            "method": "GET",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache"
            },
            "processData": false
        }

        $.ajax(settings).done(function (response) {
            console.log(response)
            if(response.isResultAvailable && response.isSuccessful) {
                $('#totalCustomer').html(response.result.countCustomer);
                $('#totalProductItem').html(response.result.countProductItem);
                $('#totalAcceptOrder').html(response.result.countAcceptedOrder);
                $('#totalRejectOrder').html(response.result.countRejectedOrder);
            }
        });
    }
}
