$(document).ready(function () {

    $("#student-table").DataTable({
        paging: false
    })

    $(".attendanceType").click(function () {

        let studentId = Number($(this).closest("ul").attr("studentId"))
        let attendanceType = Number($(this).attr('attendanceType'))
        let sourceId = Number($("#sourceDetails").attr('sourceId'))

        let date = Number(moment().format("YYYYMMDD"));
        let time = Number(moment().format("hhmm"));
        /**
         Source Type = 1 (grade)  , 2 (Club) , 3 (Course)  **/
        let sourceType = 1;


        let body = {
            studentId, attendanceType, sourceId, sourceType, date, time
        };

        StudentAttendance.post(body)
    })


});


let StudentAttendance = {

    post: function (body) {
        $.ajax({
            url: './student-attendance/post',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(body),
            success: function (response, textStatus, jQxhr) {
                if (response.resultAvailable && response.successful) {
                    $.notify({
                        // options
                        message: 'Attendance Successfully Submitted for ' + '',
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


