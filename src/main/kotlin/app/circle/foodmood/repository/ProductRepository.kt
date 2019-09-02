package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ProductItem
import org.springframework.data.jpa.repository.JpaRepository


interface ProductRepository : JpaRepository<ProductItem, Long> {

    fun getAllByCompanyIdAndStatusOrderByIdDesc(companyId:Long,status:Int):List<ProductItem>


    fun getAllByStatusOrderByIdDesc(status: Int):List<ProductItem>


    fun getAllByStoreIdAndStatus(storeId:Long,status: Int):List<ProductItem>

    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): ProductItem

    fun getAllByIdInAndStatus(productList: ArrayList<Long>, value: Int):List<ProductItem>

    fun getAllByCompanyIdAndIsDiscountAndStatus(companyId: Long, isDiscount: Boolean, status: Int): ArrayList<ProductItem>

    fun getByIdAndStatus(id: Long, status: Int): ProductItem

    fun countAllByCompanyIdAndStatus(companyId: Long, status: Int): Long

    fun getAllByCompanyIdAndCategoryIdAndStatus(companyId: Long, categoryId: Long, status: Int): ArrayList<ProductItem>
}

