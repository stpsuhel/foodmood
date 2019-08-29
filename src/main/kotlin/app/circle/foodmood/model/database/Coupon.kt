package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class Coupon: AuditModel() {

    @NotNull
    var couponText: String? = null

    @NotNull
    var startDate: Int? = null

    @NotNull
    var endDate: Int? = null

    @NotNull
    var maxUse: Int? = -1
}
