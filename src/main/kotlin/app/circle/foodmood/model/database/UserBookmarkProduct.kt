package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(indexes = [Index(name = "user_bookmark", columnList = "userId,productId")])
class UserBookmarkProduct: AuditModel() {

    @NotNull
    var userId: Long? = null

    @NotNull
    var productId: Long? = null

    var bookmarkType: String? = null
}
