package app.circle.foodmood.utils


class URL {

    class HomeController {
        companion object {
            const val HOME_PAGE = "/"
            const val HOME = "/home"
            const val LOGIN_PAGE = "/login"
        }
    }

    class StudentController {
        companion object {
            const val STUDENT_INFORMATION = "student-information"
            const val STUDENT_REGISTRATION = "student-registration"
            const val STUDENT_ATTENDANCE = "student-attendance"
            const val STUDENT_DETAILS = "student-details"
        }
    }

    class TeacherController {
        companion object {
            const val TEACHER_REGISTRATION = "teacher-registration"
            const val TEACHER_INFORMATION= "teacher-information"
            const val TEACHER_DETAILS = "teacher-details"
        }
    }

    class InventoryController {
        companion object {
            const val ACCOUNT_DETAILS = "account-details"
            const val ACCOUNT_HISTORY= "account-history"
            const val FEE_COLLECTION = "fee-collection"
            const val SPEND_DETAILS = "spend-details"
        }
    }

    class ParentController {
        companion object {
            const val PARENT_INFORMATION = "parent-information"
            const val PARENT_REGISTRATION = "parent-registration"
            const val PARENT_DETAILS = "parent-details"
        }
    }

    class CourseController {
        companion object {
            const val COURSE_INFORMATION = "course-information"
            const val COURSE_REGISTRATION = "course-registration"
            const val UPDATE_COURSE = "update-course"
            const val COURSE_DETAILS = "course-details"
        }
    }

    class GradeController {
        companion object {
            const val GRADE_INFORMATION = "grade-information"
            const val CREATE_GRADE = "create-grade"
            const val GRADE_DETAILS = "grade-details"
        }
    }

    class ClubController {
        companion object {
            const val CLUB_INFORMATION = "club-information"
            const val CLUB_ATTENDANCE = "club-attendance"
            const val CLUB_REGISTRATION = "club-registration"
            const val CLUB_DETAILS = "club-details"
        }
    }

    class LibraryController {
        companion object {
            const val BOOK_DETAILS = "book-details"
            const val BORROW_DETAILS = "borrow-details"
        }
    }

}


