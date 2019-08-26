package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.repository.ProductRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service


@Service
class ProductUtils(val productRepository: ProductRepository) {

    fun saveUpdateProduct(productItem: ProductItem): ProductItem {
        return productRepository.save(productItem)
    }


    @Cacheable("all-product")
    fun getAllProduct(): List<ProductItem> {
        return productRepository.getAllByStatusOrderByIdDesc(Status.Active.value)
    }


    fun getAllProductWithOutStatus(): List<ProductItem> {
        return productRepository.findAll()
    }


    @CacheEvict("all-product")
    fun deleteAllProductCache(): Boolean {
        return true
    }


    @Cacheable("all-product-company", key = "#companyId")
    fun getAllProductByCompanyId(companyId: Long): List<ProductItem> {
        return productRepository.getAllByCompanyIdAndStatusOrderByIdDesc(companyId, Status.Active.value)
    }


    @CacheEvict("all-product-company", key = "#companyId")
    fun deleteAllProductByCompanyCache(companyId: Long): Boolean {
        return true
    }


    fun getProductsByStoreId(storeId: Long): List<ProductItem> {
        return productRepository.getAllByStoreIdAndStatus(storeId, Status.Active.value)
    }

    fun getProductById(companyId: Long, id: Long): ProductItem {
        return productRepository.getByCompanyIdAndIdAndStatus(companyId, id, Status.Active.value)
    }


    fun getProductsWhereIdIn(productList: ArrayList<Long>): List<ProductItem> {

        return productRepository.getAllByIdInAndStatus(productList, Status.Active.value)

    }
}
