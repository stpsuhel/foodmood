$(document).ready(function () {
    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Student",
        width: 'resolve',
        dropdownAutoWidth: true

    });

    $('#student-list').on('select2:select', function (e) {
    });

    $("#student-club-save").click(function () {
        let studentIdList = $('#student-list').val()
        if (!studentIdList.length){
            return
        }

        $("#student-club-save-loading").removeAttr('hidden');
        let clubId = $("#club-details").attr('clubId');
        StudentClub.addStudentToClub({
            clubId, studentIdList
        })
    })

    $("#club-student-table").DataTable({
        paging: false
    })
    $(".attendanceType").click(function () {
        let studentId = Number($(this).closest("ul").attr("studentId"))

        let attendanceType = Number($(this).attr('attendanceType'))
        let sourceId = Number($("#club-details").attr('clubId'))
        let studentName = $(this).closest("ul").attr("studentName")

        let date = Number(moment().format("YYYYMMDD"));
        let time = Number(moment().format("hhmm"));
        /**
         *Source Type = 1 (grade)  , 2 (Club) , 3 (Course)
         **/
        let sourceType = 2;


        let body = {
            studentId, attendanceType, sourceId, sourceType, date, time
        };

        StudentAttendance.post(body, studentName)
    })

});


let StudentClub = {
    addStudentToClub: function (studentClub) {
        $.ajax({
            url: './student/assign-club',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(studentClub),
            success: function (data, textStatus, jQxhr) {
                if (data.resultAvailable && data.successful) {
                    window.location.reload(true);
                }
            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
};

let StudentAttendance = {

    post: function (body, studentName) {
        $.ajax({
            url: './student-attendance/post',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(body),
            success: function (response, textStatus, jQxhr) {
                if (response.resultAvailable && response.successful) {
                    $.notify({
                        // options
                        message: 'Attendance Successfully Submitted for ' + studentName,
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
