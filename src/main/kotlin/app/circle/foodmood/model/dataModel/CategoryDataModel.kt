package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.AuditModel
import javax.validation.constraints.NotEmpty

class CategoryDataModel : AuditModel() {

    @NotEmpty
    var name = ""
    var totalItem: Int = 0
}
