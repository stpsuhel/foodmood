package app.circle.foodmood.repository

import app.circle.foodmood.security.User
import org.springframework.data.jpa.repository.JpaRepository


interface AdministrationRepository : JpaRepository<User, Long> {
    fun findAllByCompanyId(companyId: Long): ArrayList<User>

    fun findByCompanyIdAndId(companyId: Long, id: Long): User?
}
