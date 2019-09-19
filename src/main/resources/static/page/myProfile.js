
let multipleUploadForm = document.querySelector('#multipleUploadForm');
let multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
let multipleFileUploadError = document.querySelector('#multipleFileUploadError');
let multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

function uploadMultipleFiles(files) {
    let formData = new FormData();
    for (let index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "https://foodmood.app/file-demo-0.0.1-SNAPSHOT/uploadMultipleFiles");

    xhr.onload = function () {
        console.log(xhr.responseText);
        let response = JSON.parse(xhr.responseText);
        if (xhr.status == 200) {
            multipleFileUploadError.style.display = "none";
            let content = "<p>All Files Uploaded Successfully</p>";
            for (let i = 0; i < response.length; i++) {
                $('.userImage').attr('src', response[i].fileDownloadUri)
            }
            multipleFileUploadSuccess.innerHTML = content;
            multipleFileUploadSuccess.style.display = "block";
        } else {
            multipleFileUploadSuccess.style.display = "none";
            multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

$(document).ready(function () {


    $(".updateInformation").click(function () {


        $('.enableInputField').prop('disabled', false);

        $('.hideSaveButton').show();

    });

    $("#saveUpdateInformation").click(function () {

        let files = multipleFileUploadInput.files;
        if (files.length === 0) {
            multipleFileUploadError.innerHTML = "Please select at least one file";
            multipleFileUploadError.style.display = "block";
        }
        uploadMultipleFiles(files);

        $('.enableInputField').prop('disabled', true);
        $('.hideSaveButton').hide();

        let name = $("#name").val();
        let phone = $("#phoneNumber").val();
        let id = $('.getUserId').attr('id');

        let body = {
            id, name, phone
        }

        userUpdate.post(body)
    })

});

let userUpdate = {

    post: function (body) {
        $.ajax({
            url: './user/update-user',
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



        /*"async": true,
        "crossDomain": true,
        "url": "./user/update",
        "method": "POST",
        "headers": {
            "content-type": "application/json",
        },
        "processData": false,
        "data": "{\n\t\"\": \"name\",\n\t\"userName\": \"skdjgl\",\n\t\"email\": \"adhzg@gmail.com\",\n\t\"phone\": \"114422556\",\n\t\"id\": 15\n}"
}*/

/*$.ajax(userUpdate).done(function (response) {
    console.log(response);
});*/

