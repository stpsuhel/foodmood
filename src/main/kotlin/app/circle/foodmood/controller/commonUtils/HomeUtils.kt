package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.dataModel.OrderStatusDataModel
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.repository.ProductRepository
import app.circle.foodmood.repository.StoreRepository
import app.circle.foodmood.utils.OrderStatus
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class HomeUtils(val orderRepository: OrderRepository, val orderProductRepository: OrderProductRepository,
                val productRepository: ProductRepository, val storeRepository: StoreRepository,
                val globalUtils: GlobalUtils) {

    fun getCountDistinctCustomer(companyId: Long): Long{
        return orderRepository.countDistinctByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getStoreByCompanyId(companyId: Long): ArrayList<Store>{
        return storeRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getOrderListByStoreId(storeIdList: ArrayList<Long>): ArrayList<OrderProduct>{
        return orderProductRepository.getAllByStoreIdInAndStatus(storeIdList, Status.Active.value)
    }

    fun getCountProductItem(storeIdList: ArrayList<Long>): Int{
        return productRepository.countAllByStoreIdInAndStatus(storeIdList, Status.Active.value)
    }

    fun getCountOrder(orderIdList: ArrayList<Long>): OrderStatusDataModel{
        val orderList = orderRepository.getAllByIdInAndOrderDateAndStatus(orderIdList, globalUtils.getCurrentDate()!!, Status.Active.value)

        val orderStatus = OrderStatusDataModel()
        orderList.forEach {
            if (it.orderStatus != OrderStatus.ORDER_CANCELED.value){
                orderStatus.acceptCount += 1
            }else{
                orderStatus.rejectCount += 1
            }
        }

        return orderStatus
    }

    fun getTotalPrice(orderIdList: ArrayList<Long>): ArrayList<Order>{
        return orderRepository.getAllByIdInAndStatus(orderIdList, Status.Active.value)
    }

    fun countRejectedOrderList(orderIdList: ArrayList<Long>): Long{
        return orderRepository.countAllByIdInAndOrderDateAndStatus(orderIdList, globalUtils.getCurrentDate()!!, Status.Active.value)
    }
}
