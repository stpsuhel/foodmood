package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(indexes = [Index(name = "product_category_data", columnList = "categoryId")])
class ProductCategory: AuditModel() {

    @NotNull
    var productId: Long? = null

    @NotNull
    var categoryId: Long? = null
}
