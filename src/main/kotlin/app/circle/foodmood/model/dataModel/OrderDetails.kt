package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.request.Cart
import app.circle.foodmood.utils.OrderStatus
import app.circle.foodmood.utils.PaymentType


class OrderDetails {

    var id: Long = -1
    var totalQuantity: Int = 0
    var addressId: Long = -1
    var productList: List<Cart> = listOf()

    var paymentAmount: Int = -1
    var discountPaymentAmount: Int = -1
    var paymentType: Int = PaymentType.CASH_ON_DELIVERY.value
    var orderStatus: Int = OrderStatus.PENDING_FOR_APPROVAL.value
}
