package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.OrderStatus
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
@Table(indexes = [Index(name = "order_data", columnList = "userId,orderStatus,orderDate")])
class Order : AuditModel() {

    @NotNull
    var addressId: Long? = null

    @NotNull
    var hasCoupon: Boolean = false

    var couponId: Long? = null

    @NotNull
    var totalPrice: Int? = null

    var totalDiscountPrice: Int? = null

    @NotEmpty
    var orderBy: String = ""

    var userId: Long? = ID_NOT_FOUND


    var orderStatus: Int = OrderStatus.PENDING_FOR_APPROVAL.value


    @NotNull
    var orderDate: Int? = null

    @NotNull
    var orderTime: Int? = null

    var deliveryManId: Long = -1L
}
