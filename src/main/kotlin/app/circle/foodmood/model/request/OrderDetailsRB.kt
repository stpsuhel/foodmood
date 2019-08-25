package app.circle.foodmood.model.request

import app.circle.foodmood.model.AuditModel
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class OrderDetailsRB : AuditModel() {

    @NotEmpty
    val productList: Array<Long> = arrayOf()

    @NotNull
    val userId: Long? = null
}
