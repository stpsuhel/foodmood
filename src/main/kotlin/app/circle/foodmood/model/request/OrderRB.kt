package app.circle.foodmood.model.request

import app.circle.foodmood.utils.DISCOUNT_NOT_FOUND_INT
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class OrderRB {
    @NotNull
    var actualPrice: Int? = null

    @NotNull
    var addressId: Long? = null

    @NotNull
    var hasCoupon: Boolean = false

    @Size(max = 10)
    var couponCode: String = ""

    @NotNull
    var hasDiscount: Boolean = false

    var discountPrice: Int = DISCOUNT_NOT_FOUND_INT

    var deliveryCharge: Int = 0

    @NotEmpty
    var cartItemList: List<Cart> = listOf()

    @NotEmpty
    var secretToken: String = ""
}
