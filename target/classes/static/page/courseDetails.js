$(document).ready(function () {
    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Student",
        width: 'resolve',
        dropdownAutoWidth: true
    });


    $('#student-list').on('select2:select', function (e) {
    });


    $("#student-course-save").click(function () {
        let studentIdList = $('#student-list').val()
        if (!studentIdList.length){
            return
        }


        $("#student-course-save-loading").removeAttr('hidden');
        let courseId = $("#course-details").attr('courseId');
        StudentCourse.addStudentToCourse({
            courseId, studentIdList
        })
    })

    $("#course-table").DataTable({
        paging: false
    })

    $(".attendanceType").click(function () {

        let studentId = Number($(this).closest("ul").attr("studentId"))
        let attendanceType = Number($(this).attr('attendanceType'))
        let sourceId = Number($("#course-details").attr('courseId'))
        let studentName = $(this).closest("ul").attr("studentName")

        let date = Number(moment().format("YYYYMMDD"));
        let time = Number(moment().format("hhmm"));
        /**
         *Source Type = 1 (grade)  , 2 (Club) , 3 (Course)
         **/
        let sourceType = 3;


        let body = {
            studentId, attendanceType, sourceId, sourceType, date, time
        };

        StudentAttendance.post(body, studentName)
    })
});


let StudentCourse = {

    addStudentToCourse: function (studentCourse) {
        $.ajax({
            url: './student/assign-course',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(studentCourse),
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
            success: function (response) {
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
