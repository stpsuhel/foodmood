package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.utils.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface OrderRepository : JpaRepository<Order, Long> {

    @Query("select count (distinct order_by) from Order where company_id=?1 and status=?2")
    fun countDistinctByCompanyIdAndStatus(companyId: Long, status: Int): Long

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<Order>

    fun findAllByStatusAndOrderDateGreaterThanAndOrderDateLessThan(status: Int, beforeSevenDay: Int, toDay: Int): ArrayList<Order>

    fun getAllByUserId(userId: Long): ArrayList<Order>

    fun getOrderById(orderId:Long):Order?

    fun getAllByOrderDateAndStatus(orderDate: Int, status: Int):ArrayList<Order>

    fun countAllByIdInAndOrderDateAndStatus(idList: ArrayList<Long>, toDaysDate: Int, status: Int): Long

    fun getAllByIdInAndOrderDateAndStatus(idList: ArrayList<Long>, toDaysDate: Int, status: Int): ArrayList<Order>

    fun getAllByIdInAndStatus(idList: ArrayList<Long>, status: Int): ArrayList<Order>
}

