package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class OrderDelivery : AuditModel() {

    @NotNull
    var orderId: Long? = null

    @NotNull
    var deliveryManId: Long? = null

    @NotNull
    var deliveryStartTime: Int? = null

    @NotNull
    var deliveryEndTime: Int = 0

    @NotNull
    var deliveryDate: Int? = null
}
