package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.utils.DeliveryType
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.OrderStatus

class OrderDashboard {

    var orderId: Long = ID_NOT_FOUND
    var orderStatus: Int = OrderStatus.PENDING_FOR_APPROVAL.value
    var orderDate: String? = null
    var orderBy: String = ""
    var deliveryAddress: String? = null
    var deliveryMan: String = ""
    var itemList: ArrayList<String> = arrayListOf()
}

