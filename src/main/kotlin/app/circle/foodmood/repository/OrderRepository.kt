package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.ProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*
import kotlin.collections.ArrayList


interface OrderRepository : JpaRepository<Order, Long> {

    @Query("select count (distinct order_by) from Order where company_id=?1 and status=?2")
    fun countDistinctByCompanyIdAndStatus(companyId: Long, status: Int): Long

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<Order>

    fun findAllByStatusAndOrderDateGreaterThanAndOrderDateLessThan(status: Int, beforeSevenDay: Int, toDay: Int): ArrayList<Order>
}

