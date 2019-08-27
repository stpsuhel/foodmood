package app.circle.foodmood.repository

import app.circle.foodmood.model.database.UserBookmarkProduct
import org.springframework.data.jpa.repository.JpaRepository

interface UserBookmarkProductRepository: JpaRepository<UserBookmarkProduct, Long> {
    fun getAllByCompanyIdAndUserIdAndStatus(companyId: Long, userId: Long, status: Int): ArrayList<UserBookmarkProduct>
}
