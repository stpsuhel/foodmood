package app.circle.foodmood.utils

enum class Status(val value: Int) {
    Deleted(0),
    Active(1),


}

enum class AttendanceType(val value: Int) {
    Absent(0),
    CheckIn(1),
    CheckOut(2)
}


enum class AttendanceSource(val value: Int) {
    Grade(1),
    Club(2),
    Course(3)
}

/**
 * Make Sure existing type value is not changed when you add new role.
enum class UserType(val type: Int) {
Admin(1),
Student(2),
Teacher(3),
Parent(4),
Manager(5),
Accounts(6),
}*/

/**
 * Parent Type
 */
enum class ParentType(val value: Int) {
    Parent(1),

    GrandParent(2),
    Other(3)
}


const val ID_NOT_FOUND: Long = -1L
const val SIZE_EMPTY: Int = 0
const val REJECT_FOR_TEACHER_ID: String = "500"


const val STUDENT_PASSWORD_CONSTANT = "student-"


val feedTypeList = arrayOf("feed-item-secondary", "feed-item-danger", "feed-item-primary", "feed-item-success", "feed-item-warning")

const val MAX_HINT_LENGTH = 99

const val START_MONTH_DATE = "0101"
const val END_MONTH_DATE = "1231"

