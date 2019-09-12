package app.circle.foodmood.repository

import app.circle.foodmood.model.database.OrderDelivery
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDeliveryRepository: JpaRepository<OrderDelivery, Long> {

}
