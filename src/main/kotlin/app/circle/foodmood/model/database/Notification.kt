package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull


@Entity
class Notification() : AuditModel() {

    @NotNull
    var title: String? = null

    @NotNull
    var description: String? = null

    @NotNull
    var notificationType: Int? = null

    var imageURL: String = ""

    @NotNull
    var sourceId: Long? = null

    @NotNull
    var to: String? = null

    @NotNull
    var toType: Int? = null
}
