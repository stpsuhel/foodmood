package app.circle.foodmood.repository

import app.circle.foodmood.model.database.DeliveryMan
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryManRepository: JpaRepository<DeliveryMan, Long> {

    fun getByUserIdAndStatus(userId: Long, status: Int): DeliveryMan

    fun existsByUserIdAndDeliveryStatusAndStatus(userId: Long, deliveryStatus: Int, status: Int): Boolean

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<DeliveryMan>

    fun getAllByStatus(status: Int): ArrayList<DeliveryMan>
}
