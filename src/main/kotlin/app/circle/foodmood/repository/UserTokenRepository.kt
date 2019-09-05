package app.circle.foodmood.repository

import app.circle.foodmood.model.database.UserToken
import org.springframework.data.jpa.repository.JpaRepository

interface UserTokenRepository : JpaRepository<UserToken, Long> {
}
