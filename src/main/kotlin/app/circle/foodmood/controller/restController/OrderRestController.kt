package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.OrderDetailsSnippet
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.model.request.OrderDetailsRB
import app.circle.foodmood.model.request.OrderRB
import app.circle.foodmood.model.request.UpdateOrderStatusRequestBody
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.repository.ProductRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.APP
import app.circle.foodmood.utils.OrderStatus
import app.circle.foodmood.utils.ProcessDataModel
import app.circle.foodmood.utils.tokenEqualText
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("order")
class OrderRestController(val productUtils: ProductUtils, val orderRepository: OrderRepository, val userAddressUtils: UserAddressUtils, val userUtils: UserUtils,
                          val productRepository: ProductRepository, val orderProductRepository: OrderProductRepository, val storeUtils: StoreUtils,
                          val globalUtils: GlobalUtils, val orderUtils: OrderUtils, val processDataModel: ProcessDataModel, val notificationUtils: NotificationUtils) {


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("details")
    fun getOrderDetails(@Validated @RequestBody orderDetailsRB: OrderDetailsRB): Response<OrderDetailsSnippet> {

        val response = Response<OrderDetailsSnippet>()


        var totalPrice: Int = 0
        var totalDiscount: Int = 0

        var discountPrice: Int = 0

        var itemList = arrayListOf<Long>()

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
                // Order table company represent App Id
                order.companyId = APP.FOOD_MOOD.value.toLong()

                order.orderDate = globalUtils.getCurrentDate()
                order.orderTime = globalUtils.getCurrentTime()
                order.userId = userPrinciple.id

                val orderData = orderRepository.save(order)

                var totalQuantity = 0

                orderRB.cartItemList.forEach { cart ->

                    val product = productRepository.findByIdOrNull(cart.productId)
                    product?.let {
                        val orderProduct = OrderProduct()
                        orderProduct.orderId = orderData.id
                        orderProduct.productId = cart.productId
                        orderProduct.orderDate = globalUtils.getCurrentDate()
                        orderProduct.quantity = cart.quantity
                        orderProduct.storeId = cart.storeId

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

    /**
     * Get all Order And Order Product List
     * Has Some error on Company Id
     * All Order has same Company Id
     * After Fix this problem delete this comment
     */
    @GetMapping("get-order-list")
    fun getOrderList(): Response<ArrayList<OrderDataModel>> {
        val response = Response<ArrayList<OrderDataModel>>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val orderList = orderUtils.getAllOrderList(userPrinciple.companyId)

        val allCompanyStore = storeUtils.getAllCompanyStore(userPrinciple.companyId)




        orderList.forEach {
            val orderDataInfo = processDataModel.processOrderToOrderDataModel(it)

            response.result.add(orderDataInfo)
        }
        response.isSuccessful = true
        response.isResultAvailable = true

        return response
    }

    /**
     * Get all Order And Order Product List
     * Has Some error on Company Id
     * All Order has same Company Id
     * After Fix this problem delete this comment
     */
    @GetMapping("get-order-product-list")
    fun getOrderProductList(): Response<ArrayList<OrderProductDataModel>> {
        val response = Response<ArrayList<OrderProductDataModel>>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val orderList = orderUtils.getAllOrderProductList(userPrinciple.companyId)

        orderList.forEach {
            val orderDataInfo = processDataModel.processOrderProductToOrderProductDataModel(it)
            response.result.add(orderDataInfo)
        }
        response.isSuccessful = true
        response.isResultAvailable = true

        return response
    }


    @GetMapping(value = ["get-order-history"])
    fun getUserOrderHistory(): Response<ArrayList<OrderHistory>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<ArrayList<OrderHistory>>()
        val orderList = ArrayList<Long>()
        val allOrderList = arrayListOf<OrderHistory>()


        try {
            val allOrder = orderUtils.getAllOrderByUserId(userPrinciple.id)

            allOrder.forEach {
                orderList.add(it.id!!)
            }

            val orderDetailsList = orderUtils.getAllOrderProductByOrderList(orderList)


            allOrder.forEach {
                val orderHistory = OrderHistory()
                orderHistory.orderId = it.id!!
                orderHistory.hasDiscount = it.totalDiscountPrice!! < it.totalPrice!!
                orderHistory.orderDiscountPrice = it.totalDiscountPrice!!
                orderHistory.orderOriginalPrice = it.totalPrice!!
                orderHistory.orderStatus = it.orderStatus!!
                orderHistory.orderDate = it.orderDate!!
                for (orderProduct in orderDetailsList) {
                    if (orderProduct.orderId == it.id) {
                        val orderItem = OrderItemDetails()
                        orderItem.id = orderProduct.id!!
                        orderItem.price = orderProduct.perProductPrice!!
                        orderItem.priceDiscount = orderProduct.perProductDiscountPrice!!
                        orderItem.hasDiscount = orderProduct.hasDiscount!!
                        orderItem.quantity = orderProduct.quantity!!
                        val product = productUtils.getByProductId(orderProduct.productId!!)
                        orderItem.name = product.name
                        orderHistory.itemList.add(orderItem)

                    }
                }

                orderHistory.deliveryAddress = userAddressUtils.getUserAddressById(it.addressId!!)!!

                allOrderList.add(orderHistory)
            }


            response.result = allOrderList
            response.isSuccessful = true
            response.isResultAvailable = true

            return response
        } catch (e: Exception) {
            response.result = allOrderList
            response.isSuccessful = false
            response.isResultAvailable = false
        }

        return response
    }

    @PostMapping("accept")
    fun acceptOrderByRestaurant(@Validated @RequestBody updateOrderStatusRequestBody: UpdateOrderStatusRequestBody): Response<String> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<String>()
        val order = orderUtils.getOrderById(updateOrderStatusRequestBody.orderId!!)

        try {
            order?.let { order ->
                if (order.orderStatus == OrderStatus.PENDING_FOR_APPROVAL.value) {
                    val productList = productUtils.getAllProductByCompanyId(userPrinciple.companyId)
                    val productIdList = arrayListOf<Long>()

                    productList.forEach {
                        productIdList.add(it.id!!)
                    }
                    updateOrderStatusRequestBody.productList.forEach {
                        if (!productIdList.contains(it)) {
                            response.isResultAvailable = false
                            response.isSuccessful = false
                            response.result = null
                            response.message = arrayOf("Order contain multiple store products")

                            return response
                        }
                    }
                    order.orderStatus = OrderStatus.ACCEPT_BY_RESTAURANT.value
                    orderRepository.save(order)


                    val userId = order.userId


                    val userDetails = userUtils.getUserById(userId!!)

                    val sendOrderAcceptNotification = notificationUtils.sendOrderAcceptNotification("Your order # ${order.id} has accepted by ", userDetails!!.fcmToken!!, order.id!!)


                    if (sendOrderAcceptNotification.statusCode == HttpStatus.OK) {
                        response.isResultAvailable = false
                        response.isSuccessful = false
                        response.result = sendOrderAcceptNotification.body.toString()
                        response.message = arrayOf("Order contain multiple store products")

                        return response
                    } else {

                        response.isResultAvailable = false
                        response.isSuccessful = false
                        response.result = null
                        response.message = arrayOf("App Notification Error")

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            response.isResultAvailable = false
            response.isSuccessful = false
            response.result = null
            response.message = arrayOf(e.message)
        }

        return response
    }


}
