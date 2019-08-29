package app.circle.foodmood.utils

import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.security.User
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


    fun processProductItemToProductItemDataModel(product: ProductItem, store: Store): ProductItemDataModel{
        val productDataModel = ProductItemDataModel()

        try {
            productDataModel.companyId = product.companyId!!
            productDataModel.id = product.id!!
            productDataModel.name = product.name
            productDataModel.description = product.description
            productDataModel.price = product.price
            productDataModel.discountPrice = product.discountPrice
            productDataModel.isDiscount = product.isDiscount
            productDataModel.storeId = product.storeId!!
            productDataModel.storeName = store.name!!
            productDataModel.isFreeDelivery = product.freeDelivery!!
        }catch (e: Exception){
            e.printStackTrace()
        }

        return productDataModel
    }
}
