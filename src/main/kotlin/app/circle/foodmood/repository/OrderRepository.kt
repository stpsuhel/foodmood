package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.ProductItem
import org.springframework.data.jpa.repository.JpaRepository


interface OrderRepository : JpaRepository<Order, Long> {
}

