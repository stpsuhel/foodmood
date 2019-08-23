package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
    fun getAllByStatusOrderByNameAsc(status: Int): ArrayList<Category>
}
