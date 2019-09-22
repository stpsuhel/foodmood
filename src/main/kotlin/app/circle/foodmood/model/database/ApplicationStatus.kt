package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity


@Entity
class ApplicationStatus : AuditModel() {
    var isActive: Boolean = true
    var notActiveMessage: String = ""

    var hasDeliveryCharge: Boolean = true
    var deliveryCharge: Int = 50
}
