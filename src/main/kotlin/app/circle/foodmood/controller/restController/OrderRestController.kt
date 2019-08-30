package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.model.OrderDetailsSnippet
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.model.request.OrderDetailsRB
import app.circle.foodmood.model.request.OrderRB
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.repository.ProductRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.tokenEqualText
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("order")
class OrderRestController(val productUtils: ProductUtils, val orderRepository: OrderRepository, val productRepository: ProductRepository, val orderProductRepository: OrderProductRepository) {


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


    @PostMapping(value = ["make-order"])
    fun saveOrder(@RequestBody orderRB: OrderRB): Response<String> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        var response = Response<String>()
        if (userPrinciple.username.tokenEqualText(orderRB.secretToken)) {

            try {
                var order = Order()

                order.addressId = -1
                order.hasCoupon = orderRB.hasCoupon
                order.orderBy = userPrinciple.username
                order.totalDiscountPrice = orderRB.discountPrice
                order.totalPrice = orderRB.actualPrice
                order.couponId = userPrinciple.companyId

                val orderData = orderRepository.save(order)
                orderRB.cartItemList.forEach {

                    val orderProduct = OrderProduct()
                    orderProduct.orderId = orderData.id
                    orderProduct.productId = it.productId
                    orderProduct.quantity = it.quantity

                    val product = productRepository.findByIdOrNull(it.productId)
                    product?.let {
                        orderProduct.hasDiscount = it.isDiscount
                        orderProduct.perProductPrice = it.price
                        orderProduct.perProductDiscountPrice = it.discountPrice
                        orderProduct.companyId = userPrinciple.companyId
                        orderProductRepository.save(orderProduct)
                    }


                }
                response.isResultAvailable = true
                response.result = "Order Placed"
                response.successful = true
            } catch (e: Exception) {
                response.isResultAvailable = false
                response.message = arrayOf(e.message)
                response.successful = false
            }

        }
        return response
    }


}
