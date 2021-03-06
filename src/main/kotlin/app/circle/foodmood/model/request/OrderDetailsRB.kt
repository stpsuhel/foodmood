package app.circle.foodmood.model.request

import app.circle.foodmood.model.AuditModel
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class OrderDetailsRB : AuditModel() {

    @NotEmpty
    val productList: Array<Cart> = arrayOf()

    @NotNull
    @NotEmpty
    val userId: String = ""
}


class Cart(
        val productId: Long,
        val storeId: Long,
        var quantity: Int = 1
)
