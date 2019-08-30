package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
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
}
