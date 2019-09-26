package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
class Notification() : AuditModel() {

    @NotNull
    @Size(min=3, max=30, message = "Notification Title cannot be Empty or less then three")
    var title: String? = null

    @NotNull
    @Size(min=3)
    var description: String? = null

    @NotNull
    var notificationType: Int? = null

    var imageURL: String = ""

    @NotNull
    var sourceId: Long? = null

    @NotNull
    var sendTo: String = ""

    @NotNull
    var toType: Int = -1
}
