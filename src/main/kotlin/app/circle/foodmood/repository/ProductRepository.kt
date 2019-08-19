package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.model.database.Store
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<ProductItem, Long> {

    fun getAllByCompanyIdAndStatusOrderByIdDesc(companyId:Long,status:Int):List<ProductItem>


    fun getAllByStatusOrderByIdDesc(status: Int):List<ProductItem>


    fun getAllByStoreIdAndStatus(storeId:Long,status: Int):List<ProductItem>

    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): ProductItem
}

