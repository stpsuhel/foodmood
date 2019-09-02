package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.utils.Status
import org.springframework.stereotype.Service

@Service
class OrderUtils(val orderRepository: OrderRepository, val orderProductRepository: OrderProductRepository) {


    fun getAllOrderList(companyId: Long): ArrayList<Order>{
        return orderRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getAllOrderProductList(companyId: Long): ArrayList<OrderProduct>{
        return orderProductRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }
}
