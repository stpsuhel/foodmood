package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class UserAddress: AuditModel() {

    @NotNull
    var userId: Long? = null

    @NotNull
    var description: String? = null

    @NotNull
    var address: String? = null

    var addressLineOne: String? = null

    var addressLineTwo: String? = null

    var locationLatitude: Double = 0.0

    var locationLongitude: Double = 0.0
}
