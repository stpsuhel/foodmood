package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull

@Entity
class ProductCategory: AuditModel() {

    @NotNull
    var productId: Long? = null

    @NotNull
    var categoryId: Long? = null
}
