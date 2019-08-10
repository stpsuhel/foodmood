package app.circle.foodmood.utils

class RoleConstant {

    companion object {
        const val Admin = "ROLE_ADMIN"
        const val ADMIN_READ = "ADMIN_READ"
        const val ADMIN_WRITE = "ADMIN_WRITE"
        const val STUDENT_WRITE = "STUDENT_WRITE"
        const val STUDENT_READ = "STUDENT_READ"
        const val TEACHER_READ = "TEACHER_READ"
        const val TEACHER_WRITE = "TEACHER_WRITE"
        const val INVENTORY_READ = "INVENTORY_READ"
        const val INVENTORY_WRITE = "INVENTORY_WRITE"
        const val PARENT_READ = "PARENT_READ"
        const val PARENT_WRITE = "PARENT_WRITE"
        const val COURSE_READ = "COURSE_READ"
        const val COURSE_WRITE = "COURSE_WRITE"
        const val GRADE_READ = "GRADE_READ"
        const val GRADE_WRITE = "GRADE_WRITE"
        const val CLUB_READ = "CLUB_READ"
        const val CLUB_WRITE = "CLUB_WRITE"
        const val LIBRARY_READ = "LIBRARY_READ"
        const val LIBRARY_WRITE = "LIBRARY_WRITE"
        const val COMPANY_MANAGEMENT = "COMPANY_MANAGEMENT"
        const val STUDENT_PORTAL = "STUDENT_PORTAL"
        const val PARENT_PORTAL = "PARENT_PORTAL"


    }

}

enum class PrimaryRole(value: Int) {
    ADMIN(0),
    TEACHER(1),
    STUDENT(2),
    PARENT(3),

}
