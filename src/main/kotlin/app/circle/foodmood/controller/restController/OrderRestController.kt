package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.GlobalUtils
import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.model.OrderDetailsSnippet
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.OrderDetails
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
class OrderRestController(val productUtils: ProductUtils, val orderRepository: OrderRepository, val productRepository: ProductRepository, val orderProductRepository: OrderProductRepository , val globalUtils: GlobalUtils) {


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("details")
    fun getOrderDetails(@Validated @RequestBody orderDetailsRB: OrderDetailsRB): Response<OrderDetailsSnippet> {

        val response = Response<OrderDetailsSnippet>()


        var totalPrice: Int = 0
        var totalDiscount: Int = 0

        var discountPrice: Int = 0

        var itemList = arrayListOf<Long>()

/*        orderDetailsRB.productList.forEach {
            itemList.add(it.productId)
        }*/

/*        val productListOrder = productUtils.getProductsWhereIdIn(itemList)

        productListOrder.forEach {

            totalPrice += it.price!!
            discountPrice += if (it.isDiscount) {
                it.discountPrice!!
            } else {
                it.price!!
            }
        }*/

        var totalItem = 0


        orderDetailsRB.productList.let { cartList ->


            cartList.forEach { cart ->

                val product = productRepository.findById(cart.productId)

                product.ifPresent {
                    totalItem += cart.quantity
                    totalPrice += it.price!! * cart.quantity
                    discountPrice += if (it.isDiscount) {
                        it.discountPrice!! * cart.quantity
                    } else {
                        it.price!! * cart.quantity
                    }
                }
            }
        }



        totalDiscount = totalPrice - discountPrice
        response.isResultAvailable = true
        response.result = OrderDetailsSnippet(totalItem, totalPrice, totalDiscount, discountPrice)
        response.successful = true

        return response
    }


    @PostMapping(value = ["make-order"])
    fun saveOrder(@RequestBody orderRB: OrderRB): Response<OrderDetails> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<OrderDetails>()


        if (userPrinciple.username.tokenEqualText(orderRB.secretToken)) {

            try {
                val order = Order()

                order.addressId = orderRB.addressId
                order.hasCoupon = orderRB.hasCoupon
                order.orderBy = userPrinciple.username
                order.totalDiscountPrice = orderRB.discountPrice
                order.totalPrice = orderRB.actualPrice
                order.companyId = userPrinciple.companyId

                order.orderDate = globalUtils.getCurrentDate()

                val orderData = orderRepository.save(order)

                var totalQuantity = 0

                orderRB.cartItemList.forEach { cart ->

                    val product = productRepository.findByIdOrNull(cart.productId)
                    product?.let {
                        val orderProduct = OrderProduct()
                        orderProduct.orderId = orderData.id
                        orderProduct.productId = cart.productId
                        orderProduct.quantity = cart.quantity

                        orderProduct.hasDiscount = it.isDiscount
                        orderProduct.perProductPrice = it.price
                        orderProduct.perProductDiscountPrice = it.discountPrice
                        orderProduct.companyId = userPrinciple.companyId
                        orderProductRepository.save(orderProduct)

                        totalQuantity += cart.quantity
                    }
                }
                response.isResultAvailable = true
                val orderDetails = OrderDetails()
                orderDetails.addressId = order.addressId!!
                orderDetails.orderStatus = order.orderStatus
                orderDetails.paymentAmount = order.totalPrice!!
                orderDetails.discountPaymentAmount = order.totalDiscountPrice!!

                orderDetails.totalQuantity = totalQuantity
                orderDetails.id = order.id!!

                response.result = orderDetails
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
