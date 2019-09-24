$(document).ready(function () {

    home.getHomeData()

    /*$('#update-cache').click(function () {
        home.updateCache()
    })*/

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
    },

    /*updateCache: function () {
        let settings = {
            "async": true,
            "crossDomain": false,
            "url": "./delete-update-cache",
            "method": "GET",
            "headers": {
                "content-type": "application/json",
                "cache-control": "no-cache"
            },
            "processData": false
        }

        $.ajax(settings).done(function (response) {
            console.log(response)
            if (response.resultAvailable && response.successful) {
                $.notify({
                    // options
                    message: response.result,
                    icon: 'flaticon-alarm-1',
                }, {
                    // settings
                    type: 'success',
                    newest_on_top: false,
                    placement: {
                        from: "bottom",
                        align: "center"
                    },
                    animate: {
                        enter: 'animated fadeIn',
                        exit: 'animated fadeOutRight'
                    },
                    delay: 2000,
                    timer: 100,
                });
            }

        });
    }*/
}
