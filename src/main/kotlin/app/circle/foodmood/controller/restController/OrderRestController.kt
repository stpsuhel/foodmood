package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.OrderDetailsSnippet
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderDelivery
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.model.request.OrderDetailsRB
import app.circle.foodmood.model.request.OrderRB
import app.circle.foodmood.model.request.UpdateOrderStatusRequestBody
import app.circle.foodmood.repository.*
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.*
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
                          val globalUtils: GlobalUtils, val orderUtils: OrderUtils, val processDataModel: ProcessDataModel,
                          val deliveryManRepository: DeliveryManRepository, val notificationUtils: NotificationUtils,
                          val homeUtils: HomeUtils, val userAddressRepository: UserAddressRepository,
                          val orderDeliveryRepository: OrderDeliveryRepository, val companyRepository: CompanyRepository) {


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
                order.deliveryCharge = orderRB.deliveryCharge
                // Order table company represent App Id
                order.companyId = APP.FOOD_MOOD.value.toLong()

                order.orderDate = globalUtils.getCurrentDate()
                order.orderTime = globalUtils.getCurrentTime()
                order.userId = userPrinciple.id

                val orderData = orderRepository.save(order)

                var totalQuantity = 0

                var storeId: Long? = null
                orderRB.cartItemList.forEach { cart ->
                    storeId = cart.storeId

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

                val storeInfo = storeUtils.getStoreById(storeId!!)
                val companyAdminUserList = userUtils.getUserByCompanyIdAndPrimaryRole(storeInfo.companyId!!, PrimaryRole.RestaurantManager)
                val adminUserList = userUtils.getUserByAdminPrimaryRole(PrimaryRole.CompanyManagement)

                try {
                    adminUserList.forEach {
                        val companyInfo = companyRepository.getCompanyByIdAndStatus(storeInfo.companyId!!, Status.Active.value)
                        notificationUtils.sendOrderAcceptNotification("Company ${companyInfo.name} get a order # ${orderData.id}", it.fcmToken!!, orderData.id!!, orderData.orderStatus)
                    }

                    companyAdminUserList.forEach {
                        notificationUtils.sendOrderAcceptNotification("Your Company get a order # ${orderData.id}", it.fcmToken!!, orderData.id!!, orderData.orderStatus)
                    }



                    notificationUtils.orderNotification(orderData.id!!,storeInfo.name!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                response.isResultAvailable = true
                val orderDetails = OrderDetails()
                orderDetails.addressId = order.addressId!!
                orderDetails.orderStatus = order.orderStatus
                orderDetails.paymentAmount = order.totalPrice!! + order.deliveryCharge
                orderDetails.discountPaymentAmount = order.totalDiscountPrice!! + order.deliveryCharge

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

    @PostMapping("update-status")
    fun acceptOrderByRestaurant(@Validated @RequestBody updateOrderStatusRequestBody: UpdateOrderStatusRequestBody): Response<String> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<String>()
        val order = orderUtils.getOrderById(updateOrderStatusRequestBody.orderId!!)

        try {
            order?.let { order ->

                val productList = productUtils.getAllProductByCompanyId(userPrinciple.companyId)
                val productIdList = arrayListOf<Long>()

                productList.forEach {
                    productIdList.add(it.id!!)
                }
              /**
                updateOrderStatusRequestBody.productList.forEach {
                    if (!productIdList.contains(it)) {
                        response.isResultAvailable = false
                        response.isSuccessful = false
                        response.result = null
                        response.message = arrayOf("Order contain multiple store products")

                        return response
                    }
                }
                **/
                order.orderStatus = updateOrderStatusRequestBody.orderStatus!!
                val updatedOrder = orderRepository.save(order)


                val userId = order.userId


                val userDetails = userUtils.getUserById(userId!!)

                val sendOrderAcceptNotification = notificationUtils.sendOrderAcceptNotification("Your order # ${order.id} has accepted by ", userDetails!!.fcmToken!!, order.id!!, updatedOrder.orderStatus!!)


                if (sendOrderAcceptNotification.statusCode == HttpStatus.OK) {
                    response.isResultAvailable = true
                    response.isSuccessful = true
                    response.result = sendOrderAcceptNotification.body.toString()
                    response.message = null

                    return response
                } else {

                    response.isResultAvailable = false
                    response.isSuccessful = false
                    response.result = null
                    response.message = arrayOf("App Notification Error")

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

    @GetMapping("todays-order-list")
    fun getTodayUserOrderHistory(): Response<ArrayList<OrderHistory>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<ArrayList<OrderHistory>>()
        val orderList = ArrayList<Long>()
        val allOrderList = arrayListOf<OrderHistory>()


        try {
            val storeList = homeUtils.getStoreByCompanyId(userPrinciple.companyId)

            val storeIdList = arrayListOf<Long>()
            storeList.forEach {
                storeIdList.add(it.id!!)
            }

            val orderProductList = homeUtils.getOrderListByStoreId(storeIdList)
            val orderIdList = arrayListOf<Long>()
            orderProductList.forEach {
                orderIdList.add(it.orderId!!)
            }

            val allOrder = orderUtils.getAllOrderOfTodaysDate(orderIdList)

            val orderDetailsList = orderUtils.getAllOrderProductByOrderList(orderIdList)

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

                val userInfo = userUtils.getUserById(orderHistory.deliveryAddress!!.userId!!)
                orderHistory.orderBy = userInfo!!.phone

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

    @GetMapping("order-details")
    fun getUserOrderHistoryById(@RequestParam id: Long? = null): Response<ArrayList<OrderHistory>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<ArrayList<OrderHistory>>()
        val orderList = ArrayList<Long>()
        val allOrderList = arrayListOf<OrderHistory>()


        try {
            if (id != null) {
                val orderDetails = orderUtils.getOrderById(id)

                val orderDetailsList = orderUtils.getOrderProductByOrderId(orderDetails!!.id!!)

                val orderHistory = OrderHistory()
                orderHistory.orderId = orderDetails.id!!
                orderHistory.hasDiscount = orderDetails.totalDiscountPrice!! < orderDetails.totalPrice!!
                orderHistory.orderDiscountPrice = orderDetails.totalDiscountPrice!!
                orderHistory.orderOriginalPrice = orderDetails.totalPrice!!
                orderHistory.orderStatus = orderDetails.orderStatus!!
                orderHistory.orderDate = orderDetails.orderDate!!
                for (orderProduct in orderDetailsList) {
                    if (orderProduct.orderId == orderDetails.id) {
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

                orderHistory.deliveryAddress = userAddressUtils.getUserAddressById(orderDetails.addressId!!)!!

                allOrderList.add(orderHistory)



                response.result = allOrderList
                response.isSuccessful = true
                response.isResultAvailable = true

                return response
            } else {
                response.result = allOrderList
                response.isSuccessful = false
                response.isResultAvailable = false
            }
        } catch (e: Exception) {
            response.result = allOrderList
            response.isSuccessful = false
            response.isResultAvailable = false
        }

        return response
    }


    /**
     * Delivery Man er Id asbe UserPrinciple theke
     * Has Role Permission DeliveryMan
     */
    @PostMapping("save-update-order-delivery-man")
    fun assignOrderToDeliveryMan(@Validated @RequestBody orderDeliveryDataModel: OrderDeliveryDataModel): Response<OrderDelivery> {
        val response = Response<OrderDelivery>()

        if (!orderRepository.existsByIdAndStatus(orderDeliveryDataModel.orderId!!, Status.Active.value)) {
            response.message = arrayOf("Order Details Not Found!!")
            return response
        }

        val orderInfo = orderRepository.getOrderById(orderDeliveryDataModel.orderId!!)
        if (!userAddressRepository.existsByIdAndStatus(orderInfo!!.addressId!!, Status.Active.value)) {
            response.message = arrayOf("Order Address is Not Found!!")
            return response
        }

        if (!deliveryManRepository.existsByIdAndDeliveryStatusAndStatus(orderDeliveryDataModel.deliveryManId!!, DeliveryManStatus.FREE_NOW.value, Status.Active.value)) {
            response.message = arrayOf("Delivery Man Not Found!!")
            return response
        }

        val orderDelivery = OrderDelivery()

        orderDelivery.deliveryDate = orderDeliveryDataModel.deliveryDate
        orderDelivery.deliveryEndTime = orderDeliveryDataModel.deliveryEndTime
        orderDelivery.deliveryManId = orderDeliveryDataModel.deliveryManId
        orderDelivery.deliveryStartTime = orderDeliveryDataModel.deliveryStartTime
        orderDelivery.orderId = orderDeliveryDataModel.orderId

        val deliveryManInfo = deliveryManRepository.getByIdAndStatus(orderDelivery.deliveryManId!!, Status.Active.value)
        val saveOrderDelivery = orderDeliveryRepository.save(orderDelivery)

        deliveryManInfo?.let { deliveryMan ->
            val userDeliveryMan = userUtils.getUserByIdAndPrimaryRole(deliveryMan.userId!!)
            val order = orderUtils.getOrderById(orderDelivery.orderId!!)

            userDeliveryMan?.let { user ->
                notificationUtils.sendOrderAcceptNotification("Your Company Assign a order # ${orderDelivery.orderId}", user.fcmToken!!, orderDelivery.orderId!!, order?.orderStatus!!)
            }
        }

        response.isSuccessful = true
        response.isResultAvailable = true
        response.result = saveOrderDelivery

        return response
    }
}
