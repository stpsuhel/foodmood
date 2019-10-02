package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Company
import org.springframework.data.jpa.repository.JpaRepository


interface CompanyRepository : JpaRepository<Company, Int> {
    fun getCompanyByIdAndStatus(id: Long, status: Int): Company

    fun getAllByStatus(status: Int): ArrayList<Company>
}
