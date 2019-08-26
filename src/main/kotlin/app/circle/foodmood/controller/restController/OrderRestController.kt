package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.model.OrderDetailsSnippet
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.request.OrderDetailsRB
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("order")
class OrderRestController(val productUtils: ProductUtils) {


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("details")
    fun getOrderDetails(@Validated @RequestBody orderDetailsRB: OrderDetailsRB): Response<OrderDetailsSnippet> {

        val response = Response<OrderDetailsSnippet>()


        var totalPrice: Int = 0
        var totalDiscount: Int = 0

        var discountPrice: Int = 0

        var itemList = arrayListOf<Long>()
        orderDetailsRB.productList.forEach {
            itemList.add(it.productId)
        }

        val productListOrder = productUtils.getProductsWhereIdIn(itemList)

        productListOrder.forEach {
            totalPrice += it.price!!
            discountPrice += if (it.isDiscount) {
                it.discountPrice!!
            } else {
                it.price!!
            }
        }


        totalDiscount = totalPrice - discountPrice


        response.isResultAvailable = true
        response.result = OrderDetailsSnippet(productListOrder.size, totalPrice, totalDiscount, discountPrice)
        response.successful = true

        return response
    }


}
