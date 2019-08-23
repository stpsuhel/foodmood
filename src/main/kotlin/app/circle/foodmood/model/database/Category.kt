package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Category : AuditModel() {

    @Size(min = 3, max = 20)
    @NotNull
    var name: String? = null
}
