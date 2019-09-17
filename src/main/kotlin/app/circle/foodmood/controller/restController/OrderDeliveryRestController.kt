package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.AssignDeliveryManDataModel
import app.circle.foodmood.model.dataModel.CouponDataModel
import app.circle.foodmood.model.dataModel.OrderHistory
import app.circle.foodmood.model.dataModel.OrderItemDetails
import app.circle.foodmood.model.database.Coupon
import app.circle.foodmood.model.database.DeliveryMan
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.repository.CouponRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ProcessDataModel
import app.circle.foodmood.utils.Status
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("order-delivery")
class OrderDeliveryRestController(val orderUtils: OrderUtils, val orderRepository: OrderRepository, val homeUtils: HomeUtils,
                                  val productUtils: ProductUtils, val userUtils: UserUtils, val userAddressUtils: UserAddressUtils) {

    @PostMapping("assign-delivery-man-order")
    fun assignDeliveryManToOrder(@RequestBody assignDeliveryManDataModel: AssignDeliveryManDataModel): Response<Order>{
        val response = Response<Order>()

        val orderInfo = orderUtils.getOrderById(assignDeliveryManDataModel.orderId!!)

        if(orderInfo != null){
            orderInfo.deliveryManId = assignDeliveryManDataModel.deliveryManId!!
            val saveOrderInfo = orderRepository.save(orderInfo)

            response.result = saveOrderInfo
            response.isResultAvailable = true
            response.isSuccessful = true
        }else{
            response.message = arrayOf("Order Not Found!!")
        }

        return response
    }

    @GetMapping("delivery-man-assign-order-list")
    fun getDeliveryManOrder(): Response<ArrayList<OrderHistory>>{
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<ArrayList<OrderHistory>>()
        val allOrderList = arrayListOf<OrderHistory>()

        try {
            val allOrder = orderUtils.getAllOrderByOrderDateAndDeliveryManId(userPrinciple.id)

            val orderIdList = ArrayList<Long>()
            allOrder.forEach {
                orderIdList.add(it.id!!)
            }

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
}
