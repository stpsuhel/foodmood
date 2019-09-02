package app.circle.foodmood.repository

import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.model.database.ProductItem
import org.springframework.data.jpa.repository.JpaRepository


interface OrderProductRepository : JpaRepository<OrderProduct, Long> {

    fun countAllByCompanyIdAndStatus(companyId: Long, status: Int): Long

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<OrderProduct>

    fun getAllByOrderIdInAndStatus(orderIdList: ArrayList<Long>, status: Int): ArrayList<OrderProduct>
}

