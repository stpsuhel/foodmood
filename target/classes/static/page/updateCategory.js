
$(document).ready(function () {

    let id = $(".checkId").attr("id");
    console.log(id)

    if(id==null){
        $('.disableProductInfo').prop('disabled', false);
        $('#updateCategory').hide();
    }else{
        $('.hideButton').hide();
    }

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Grade",
        width: '100%',
        dropdownAutoWidth: true
    });


    $("#cancel-button").click(function () {

        if(id == null){
            $(location).attr('href', './all-category-information');
        }else{
            $('.hideButton').hide();
            $('.disableProductInfo').prop('disabled', true);
            $('#updateCategory').show();
        }
    });

    $('#updateCategory').click(function () {
        $('.hideButton').show();
        $('.disableProductInfo').prop('disabled', false);
        $('#updateCategory').hide();
    })
})


