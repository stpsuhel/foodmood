package app.circle.foodmood.repository

import app.circle.foodmood.security.User
import app.circle.foodmood.utils.PrimaryRole
import org.springframework.data.jpa.repository.JpaRepository


interface AdministrationRepository : JpaRepository<User, Long> {
    fun findAllByCompanyId(companyId: Long): ArrayList<User>

    fun findByCompanyIdAndId(companyId: Long, id: Long): User?

    fun findByCompanyIdAndIdAndPrimaryRole(companyId: Long, id: Long, primaryRole: PrimaryRole): User

    fun findByIdAndPrimaryRole(id: Long, primaryRole: PrimaryRole): User
}
