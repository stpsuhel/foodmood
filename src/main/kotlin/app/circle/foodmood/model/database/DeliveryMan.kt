package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class DeliveryMan : AuditModel() {
    @NotNull
    var userId: Long? = null

    @NotNull
    var deliveryStatus: Int? = null

    var latitude: Int = 0

    var longitude: Int = 0
}
