package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Store
import org.springframework.data.jpa.repository.JpaRepository


interface StoreRepository : JpaRepository<Store, Long> {


    fun getAllByCompanyIdAndStatusOrderByIdDesc(companyId: Long, status: Int): List<Store>

    fun getByIdAndStatus(id:Long,status: Int):Store

    fun getByStatus(status: Int): ArrayList<Store>

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<Store>
}
