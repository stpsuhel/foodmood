package app.circle.foodmood.utils

import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.*
import app.circle.foodmood.security.User
import org.joda.time.format.DateTimeFormat
import org.springframework.stereotype.Service


@Service
class ProcessDataModel {



    fun processUserItemToUserDetailsItem(user: UserDetails, userInfo: User): UserDetails {

        user.id = userInfo.id
        user.companyId = userInfo.companyId
        user.email = userInfo.email
        user.name = userInfo.name
        user.userName = userInfo.username
        user.phone = userInfo.phone
        user.roles = userInfo.roles

        return user
    }

    fun processUserToUserDataModel(userInfo: User): UserDataModel {
        val user = UserDataModel()

        user.id = userInfo.id
        user.companyId = userInfo.companyId
        user.email = userInfo.email
        user.name = userInfo.name
        user.userName = userInfo.username
        user.phone = userInfo.phone
        user.primaryRole = userInfo.primaryRole.name

        return user
    }


}
