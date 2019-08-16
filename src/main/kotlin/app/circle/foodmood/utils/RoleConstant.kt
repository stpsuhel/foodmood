package app.circle.foodmood.utils

class RoleConstant {

    companion object {
        const val Admin = "ROLE_ADMIN"
        const val ADMIN_READ = "ADMIN_READ"
        const val ADMIN_WRITE = "ADMIN_WRITE"

        const val COMPANY_MANAGEMENT = "COMPANY_MANAGEMENT"


    }

}

enum class PrimaryRole(value: Int) {
    ADMIN(0),

    RestaurantManager(1),
    RestaurantEmployee(2),
    RestaurantOwner(3),
    User(4)

}
