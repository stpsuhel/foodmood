package app.circle.foodmood.repository

import app.circle.foodmood.model.database.UserAddress
import org.springframework.data.jpa.repository.JpaRepository

interface UserAddressRepository: JpaRepository<UserAddress, Long> {
}
