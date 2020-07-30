
let multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');

function uploadMultipleFiles(files) {
    $('#hideSaveButton').addClass('d-none')
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
            try {
                $('.userImage').attr('src', response[0].fileDownloadUri)
                $('#hideSaveButton').removeClass('d-none')
            }catch (e) {

            }
        }
    };

    xhr.send(formData);
}

$(document).ready(function () {

    $(".updateInformation").click(function () {

        $('#multipleUploadForm').removeClass('d-none');
        $('.enableInputField').prop('disabled', false);

        $('#hideSaveButton').removeClass('d-none')

    });

    $("#multipleFileUploadInput").change(function(){
        let files = multipleFileUploadInput.files;
        if (files.length !== 0) {
            uploadMultipleFiles(files);
        }
    });

    $(".saveUpdateInformation").click(function () {

        $('.enableInputField').prop('disabled', true);
        $('#hideSaveButton').addClass('d-none');
        $('#multipleUploadForm').addClass('d-none');

        let name = $("#name").val();
        let phone = $("#phoneNumber").val();
        let id = $('.getUserId').attr('id');
        let imageURL = $('#profileImage').attr('src');
        console.log(imageURL);

        let body = {
            id, name, phone, imageURL
        };
        console.log(body);

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

