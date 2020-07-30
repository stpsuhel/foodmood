
$(document).ready(function () {
    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Grade",
        width: '100%',
        dropdownAutoWidth: true
    });

    $("#update-user").click(function () {
        $(".enableInputField").prop("disabled", false)
    })

    $("#cancelUpdate").click(function () {
        $(".enableInputField").prop("disabled", true)
    })
});
