package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.utils.DeliveryType
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.OrderStatus

class OrderHistory {

    var orderId: Long = ID_NOT_FOUND
    var orderOriginalPrice: Int = 0
    var orderDiscountPrice: Int = 0
    var hasDiscount: Boolean = false
    var deliveryType: Int = DeliveryType.PAID_DELIVERY.value
    var deliveryPrice: Int = 0
    var orderStatus: Int = OrderStatus.PENDING_FOR_APPROVAL.value
    var orderDate: Int? = null
    var orderBy: String = ""

    var deliveryAddress: UserAddress? = null
    var itemList: ArrayList<OrderItemDetails> = arrayListOf()
}


class OrderItemDetails {
    var id: Long = ID_NOT_FOUND
    var name: String = ""
    var quantity: Int = 0
    var price: Int = 0
    var priceDiscount: Int = 0
    var hasDiscount: Boolean = false


    override fun toString(): String {
        return id.toString()
    }
}
