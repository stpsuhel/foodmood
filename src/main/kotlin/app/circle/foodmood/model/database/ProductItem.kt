package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull


@Entity
class ProductItem : AuditModel() {

    @NotNull
    var name: String = ""

    var description: String = ""

    @NotNull
    var storeId: Long? = null

}
