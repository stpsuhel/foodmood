package app.circle.foodmood.model.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class UpdateOrderStatusRequestBody {

    @NotNull
    var orderId: Long? = null

    @NotNull
    var orderStatus: Int? = null

    @NotEmpty
    var productList: Array<Long> = arrayOf()

}
