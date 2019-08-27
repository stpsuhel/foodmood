package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class UserBookmarkProduct: AuditModel() {

    @NotNull
    var userId: Long? = null

    @NotNull
    var productId: Long? = null

    var bookmarkType: String? = null
}
