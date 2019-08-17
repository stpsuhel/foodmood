package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull


@Entity
class Store : AuditModel() {

    @NotNull
    var name: String? = null

    @NotNull
    var contactNumber: String? = null

    @NotNull
    var address: String? = null

    var slogan: String = ""

    var locationLatitude: Double = 0.0

    var locationLongitude: Double = 0.0
}
