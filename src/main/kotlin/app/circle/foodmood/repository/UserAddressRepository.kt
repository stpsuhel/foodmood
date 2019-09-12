package app.circle.foodmood.repository

import app.circle.foodmood.model.database.UserAddress
import org.springframework.data.jpa.repository.JpaRepository

interface UserAddressRepository: JpaRepository<UserAddress, Long> {
    fun getAllByCompanyIdAndUserIdAndStatus(companyId: Long, userId: Long, status: Int): ArrayList<UserAddress>

    fun existsByIdAndStatus(id: Long, status: Int): Boolean
}
