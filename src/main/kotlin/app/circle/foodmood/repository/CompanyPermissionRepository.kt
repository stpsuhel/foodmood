package app.circle.foodmood.repository

import app.circle.foodmood.model.database.CompanyPermission
import org.springframework.data.jpa.repository.JpaRepository


interface CompanyPermissionRepository : JpaRepository<CompanyPermission, Long> {


    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<CompanyPermission>

}
