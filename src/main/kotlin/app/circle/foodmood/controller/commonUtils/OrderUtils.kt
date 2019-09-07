package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.utils.Status
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class OrderUtils(val orderRepository: OrderRepository, val orderProductRepository: OrderProductRepository,
                 val globalUtils: GlobalUtils) {


    fun getAllOrderList(companyId: Long): ArrayList<Order> {
        return orderRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getAllOrderProductList(companyId: Long): ArrayList<OrderProduct> {
        return orderProductRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getAllOrderProductOfLastSevenDays(toDay: Int, beforeSevenDay: Int): ArrayList<Order> {
        return orderRepository.findAllByStatusAndOrderDateGreaterThanAndOrderDateLessThan(Status.Active.value, beforeSevenDay, toDay)
    }

    fun getAllOrderProductByOrderId(orderIdList: ArrayList<Long>): ArrayList<OrderProduct> {
        return orderProductRepository.getAllByOrderIdInAndStatus(orderIdList, Status.Active.value)
    }


    fun getAllOrderByUserId(userId: Long): ArrayList<Order> {
        return orderRepository.getAllByUserId(userId)
    }

    fun getAllOrderProductByOrderList(orderList: ArrayList<Long>): ArrayList<OrderProduct> {
        return orderProductRepository.getAllByOrderIdIn(orderList)
    }


    fun getOrderByDate(date:Int): ArrayList<Order> {
        return orderRepository.getAllByOrderDateAndStatus(date,Status.Active.value)
    }

    fun getOrderById(orderId: Long): Order? {
        return orderRepository.getOrderById(orderId)
    }

    fun getAllOrderOfTodaysDate(orderIdList: ArrayList<Long>): ArrayList<Order>{
        return orderRepository.getAllByIdInAndOrderDateAndStatus(orderIdList, globalUtils.getCurrentDate()!!, Status.Active.value)
    }
}
