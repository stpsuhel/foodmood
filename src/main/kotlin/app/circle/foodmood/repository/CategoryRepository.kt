package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ProductCategory
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<ProductCategory, Long> {
    fun getAllByStatusOrderByNameAsc(status: Int): ArrayList<ProductCategory>
}
