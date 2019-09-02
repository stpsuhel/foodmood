package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.utils.Status
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class OrderUtils(val orderRepository: OrderRepository, val orderProductRepository: OrderProductRepository) {


    fun getAllOrderList(companyId: Long): ArrayList<Order>{
        return orderRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getAllOrderProductList(companyId: Long): ArrayList<OrderProduct>{
        return orderProductRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getAllOrderProductOfLastSevenDays(toDay: Int, beforeSevenDay: Int): ArrayList<Order>{
        return orderRepository.findAllByStatusAndOrderDateGreaterThanAndOrderDateLessThan(Status.Active.value, beforeSevenDay, toDay)
    }

    fun getAllOrderProductByOrderId(orderIdList: ArrayList<Long>): ArrayList<OrderProduct>{
        return orderProductRepository.getAllByOrderIdInAndStatus(orderIdList, Status.Active.value)
    }
}
