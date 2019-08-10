$(document).ready(function () {
    let studentId = $("#student-details").attr('studentId');

    $(".select2-class").select2({
        closeOnSelect: true,
        placeholder: "Select Parent",
        width: 'resolve',
        dropdownAutoWidth: true

    });

    $('#student-list').on('select2:select', function (e) {
    });


    $("#student-parent-save").click(function () {
        let parentId = $('#parent-list').val()

        // -1 for default select value
        if (parentId != -1) {
            $("#student-parent-save-loading").removeAttr('hidden');
            StudentCourse.addStudentWithParent({
                parentId, studentId
            })
        }

    })

    StudentCourse.initializeClubAttendance(studentId, 2)

});


let StudentCourse = {

    addStudentWithParent: function (studentParent) {
        $.ajax({
            url: './student/assign-student-parent',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(studentParent),
            success: function (data, textStatus, jQxhr) {


                console.log(data)
                if (data.resultAvailable && data.successful) {
                    window.location.reload(true);
                } else {
                    $("#student-parent-save-loading").attr('hidden', true);
                }

            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    },
    initializeClubAttendance: function (studentId, sourceType) {


        $.ajax({
            url: "./student-attendance/get-student-attendance?studentId=" + studentId + "&sourceType=" + sourceType,
            type: 'get',
            contentType: 'application/json',
            success: function (data) {


                console.log(data.result)
                /*     $('#student-club-attendance').DataTable({
                         data: data.result,
                         'bSort': false,
                         'aoColumns': [
                             {sWidth: "45%", bSearchable: false, bSortable: false},
                             {sWidth: "45%", bSearchable: false, bSortable: false},
                             {sWidth: "10%", bSearchable: false, bSortable: false}
                         ],
                         "scrollY": "200px",
                         "scrollCollapse": true,
                         "info": true,
                         "paging": true
                     });

                     $('#student-club-attendance').DataTable({}).destroy()
     */
                $('#student-club-attendance').DataTable({
                    "ajax": "./student-attendance/get-student-attendance?studentId=" + studentId + "&sourceType=" + sourceType,
                    destroy: true,

                });


            },
            error: function (jqXhr, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });

    }

};
