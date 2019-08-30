package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class OrderProduct : AuditModel() {
    @NotNull
    var orderId: Long? = null

    @NotNull
    var productId: Long? = null

    @NotNull
    var hasDiscount: Boolean = false

    var perProductDiscountPrice: Int? = null

    @NotNull
    var perProductPrice: Int? = null

    var quantity: Int = 0
}
