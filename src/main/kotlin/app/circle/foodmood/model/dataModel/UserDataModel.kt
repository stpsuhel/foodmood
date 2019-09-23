package app.circle.foodmood.model.dataModel

import app.circle.foodmood.security.Role
import java.util.*
import kotlin.collections.ArrayList


class UserDetails {
    var id: Long? = null
    var name: String? = ""
    var userName: String? = ""
    var email: String = ""
    var phone: String = ""
    var companyId: Long? = null
    var roles: Set<Role> = HashSet()
    var imageURL: String = ""
}


class UserDataModel {
    var id: Long? = null
    var name: String? = ""
    var userName: String? = ""
    var email: String = ""
    var phone: String = ""
    var companyId: Long? = null
    var permissionList: ArrayList<String> = arrayListOf()
    var password: String? = null
    var confirmPassword: String? = null
    var primaryRole: String? = null
}
