package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.OrderUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.AssignDeliveryManDataModel
import app.circle.foodmood.model.dataModel.CouponDataModel
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
class OrderDeliveryRestController(val orderUtils: OrderUtils, val orderRepository: OrderRepository) {

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
}
