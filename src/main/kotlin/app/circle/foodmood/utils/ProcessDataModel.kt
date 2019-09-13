package app.circle.foodmood.utils

import app.circle.foodmood.controller.commonUtils.GlobalUtils
import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.*
import app.circle.foodmood.security.User
import org.springframework.stereotype.Service


@Service
class ProcessDataModel(val globalUtils: GlobalUtils) {

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

    fun processCouponDataModelToCoupon(couponDataModel: CouponDataModel, coupon: Coupon): Coupon{

        coupon.couponText = couponDataModel.couponText
        coupon.maxUse = couponDataModel.maxUse
        coupon.startDate = globalUtils.getDateInInteger(couponDataModel.startDate!!)
        coupon.endDate = globalUtils.getDateInInteger(couponDataModel.endDate!!)

        return coupon
    }

    fun processOrderToOrderDataModel(order: Order): OrderDataModel{
        val orderInfo = OrderDataModel()

        orderInfo.id = order.id
        orderInfo.addressId = order.addressId
        orderInfo.couponId = order.couponId
        orderInfo.hasCoupon = order.hasCoupon
        orderInfo.orderBy = order.orderBy
        orderInfo.orderDate = order.orderDate
        orderInfo.orderStatus = order.orderStatus
        orderInfo.totalDiscountPrice = order.totalDiscountPrice
        orderInfo.totalPrice = order.totalPrice

        return orderInfo
    }

    fun processOrderProductToOrderProductDataModel(orderProduct: OrderProduct): OrderProductDataModel{
        val orderProductInfo = OrderProductDataModel()

        orderProductInfo.id = orderProduct.id
        orderProductInfo.companyId = orderProduct.companyId
        orderProductInfo.hasDiscount = orderProduct.hasDiscount
        orderProductInfo.orderId = orderProduct.orderId
        orderProductInfo.perProductDiscountPrice = orderProduct.perProductDiscountPrice
        orderProductInfo.perProductPrice = orderProduct.perProductPrice
        orderProductInfo.productId = orderProduct.productId
        orderProductInfo.quantity = orderProduct.quantity

        return orderProductInfo
    }

    fun processCompanyProductItemToCompanyProductItemDataModel(product: ProductItem, image: String): CompanyProductItemDataModel{
        val productDataModel = CompanyProductItemDataModel()

        try {
            productDataModel.companyId = product.companyId!!
            productDataModel.id = product.id!!
            productDataModel.name = product.name
            productDataModel.description = product.description
            productDataModel.price = product.price
            productDataModel.discountPrice = product.discountPrice
            productDataModel.isDiscount = product.isDiscount
            productDataModel.storeId = product.storeId!!
            productDataModel.categoryId = product.categoryId
            productDataModel.freeDelivery = product.freeDelivery

            productDataModel.imageURL = image
        }catch (e: Exception){
            e.printStackTrace()
        }

        return productDataModel
    }

    fun processCompanyProductItemDataModelToCompanyProductItem(product: CompanyProductItemDataModel, imageId: Long): ProductItem{
        val productDataModel = ProductItem()

        try {
            productDataModel.companyId = product.companyId
            productDataModel.id = product.id
            productDataModel.name = product.name
            productDataModel.description = product.description
            productDataModel.price = product.price
            productDataModel.discountPrice = product.discountPrice
            productDataModel.isDiscount = product.isDiscount
            productDataModel.storeId = product.storeId!!
            productDataModel.categoryId = product.categoryId
            productDataModel.freeDelivery = product.freeDelivery

            productDataModel.primaryImageId = imageId
        }catch (e: Exception){
            e.printStackTrace()
        }

        return productDataModel
    }

    fun processProductOfferDataModelToProductOffer(offerDataModel: OfferDataModel): Offer{
        val offer = Offer()

        offer.offerTitle = offerDataModel.offerTitle
        offer.offerDescription = offerDataModel.offerDescription
        offer.productId = offerDataModel.productId
        offer.startDate = globalUtils.getDateInInteger(offerDataModel.startDate!!)
        offer.endDate = globalUtils.getDateInInteger(offerDataModel.endDate!!)
        offer.offerPrice = offerDataModel.offerPrice

        return offer
    }

    fun processProductOfferToProductOfferDataModel(offer: Offer): OfferDataModel{
        val productOfferDataModel = OfferDataModel()

        productOfferDataModel.id = offer.id
        productOfferDataModel.createdBy = offer.createdBy
        productOfferDataModel.endDate = globalUtils.getDateInString(offer.endDate!!)
        productOfferDataModel.startDate = globalUtils.getDateInString(offer.startDate!!)
        productOfferDataModel.offerDescription = offer.offerDescription
        productOfferDataModel.offerTitle = offer.offerTitle
        productOfferDataModel.offerPrice = offer.offerPrice
        productOfferDataModel.productId = offer.productId

        return productOfferDataModel
    }
}
