/*
$(document).ready(function () {


})

let product = {
    getAllProduct: function() {
        let settings = {
            "async": true,
            "crossDomain": false,
            "url": "./public/all-product",
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

                let productList = '';
                $.each(response.result, function( key, productItem) {

                    productList += '<div class="col-sm-6 col-md-6 col-lg-3">';
                    productList += '<div class="card" style="width: 18rem;">';
                    productList += '<img class="card-img-top" src="'+productItem.primaryImageUrl+'" alt="Card image cap">';
                    productList += '<div class="card-body">';
                    productList += '<h5 class="card-title mb-2 fw-mediumbold">Card title</h5>';
                    productList += '<p class="card-text"></p>';
                    productList += '<a href="#" class="btn btn-warning">Go somewhere</a>';
                    productList += '</div></div></div>';

                    $('#all-product-list').appendChild(productList)
                });

            }
        });
    }
}
*/
