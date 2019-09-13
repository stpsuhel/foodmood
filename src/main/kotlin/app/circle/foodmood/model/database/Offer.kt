package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Offer: AuditModel() {

    @NotNull
    var offerTitle: String? = ""

    @NotNull
    var offerDescription: String? = null

    @NotNull
    var productId: Long? = null

    @NotNull
    var startDate: Int? = -1

    @NotNull
    var endDate: Int? = -1

    @NotNull
    var createdBy: Long? = null

    @NotNull
    var offerPrice: Int? = -1
}



