/*
$(document).ready(function () {


    $(".updateInformation").click(function () {


        $('.enableInputField').prop('disabled', false);

        let element = document.getElementById("hideSaveButton");
        element.classList.remove("d-none");

    })

    $(".saveUpdateInformation").click(function () {
        $('.enableInputField').prop('disabled', true);

        let element = document.getElementById("hideSaveButton");
        element.classList.add("d-none");

        let name = $("#name").val()
        let phone = $("#phoneNumber").val()


        let body = {
            name, phone
        }

        userUpdate.post(body)
    })


});

let userUpdate = {

    post: function (body) {
        $.ajax({
            url: './user/update',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(body),
            success: function (response, textStatus, jQxhr) {
                if (response.resultAvailable && response.successful) {
                    $.notify({
                        // options
                        message: 'User Successfully Updated',
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
                        delay: 1500,
                        timer: 100,
                    });
                }

            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }

}



        /!*"async": true,
        "crossDomain": true,
        "url": "./user/update",
        "method": "POST",
        "headers": {
            "content-type": "application/json",
        },
        "processData": false,
        "data": "{\n\t\"\": \"name\",\n\t\"userName\": \"skdjgl\",\n\t\"email\": \"adhzg@gmail.com\",\n\t\"phone\": \"114422556\",\n\t\"id\": 15\n}"
}*!/

/!*$.ajax(userUpdate).done(function (response) {
    console.log(response);
});*!/

*/
