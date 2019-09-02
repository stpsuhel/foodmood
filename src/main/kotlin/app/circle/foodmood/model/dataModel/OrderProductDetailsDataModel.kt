package app.circle.foodmood.model.dataModel




class OrderDataModel{
    var id: Long? = null
    var companyId: Long? = null
    var addressId: Long? = null
    var couponId: Long? = null
    var hasCoupon: Boolean = false
    var totalPrice: Int? = null
    var totalDiscountPrice: Int? = null
    var orderBy: String = ""
    var orderStatus: Int = -1
    var orderDate: Int? = null
}

class OrderProductDataModel {

    var id: Long? = null
    var companyId: Long? = null
    var orderId: Long? = null
    var productId: Long? = null
    var hasDiscount: Boolean = false
    var perProductDiscountPrice: Int? = null
    var perProductPrice: Int? = null
    var quantity: Int = 0
}
