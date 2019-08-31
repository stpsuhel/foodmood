package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class HomeUtils(val orderRepository: OrderRepository, val orderProductRepository: OrderProductRepository,
                val productRepository: OrderProductRepository) {

    fun getCountDistinctCustomer(companyId: Long): Long{
        return orderRepository.countDistinctByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getCountItem(companyId: Long): Long{
        return productRepository.countAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

    fun getCountOrder(companyId: Long): Long{
        return orderProductRepository.countAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }
}
