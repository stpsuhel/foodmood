package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.StoreRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class StoreUtils(val storeRepository: StoreRepository,val productUtils: ProductUtils) {

    fun saveStoreData(store: Store) {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (store.name!!.isNotEmpty() && store.contactNumber!!.isNotEmpty() && store.address!!.isNotEmpty()) {
            store.companyId = userPrinciple.companyId
            storeRepository.save(store)
        } else {
            throw  Exception("Mandatory Field is empty")
        }
    }


    @Cacheable("all-store-company", key = "#companyId")
    fun getAllCompanyStore(companyId: Long): List<Store> {
        return storeRepository.getAllByCompanyIdAndStatusOrderByIdDesc(companyId, Status.Active.value)
    }


    @CacheEvict("all-store-company", key = "#companyId")
    fun deleteAllStoreCompanyCache(companyId: Long): Boolean {
        return true
    }


    @Cacheable("all-store", key = "1")
    fun getAllStore(): MutableList<Store> {
        return storeRepository.findAll()
    }

    @CacheEvict("all-store", key = "1")
    fun deleteAllStoreCache() {
        storeRepository.findAll()
    }

    fun getStoreById(id: Long): Store {
        return storeRepository.getByIdAndStatus(id, Status.Active.value)

    }


    @Cacheable("all-products-store", key = "#storeId")
    fun getProductsByStoreId(storeId: Long): List<ProductItem> {
       return  productUtils.getProductsByStoreId(storeId)
    }


    @CacheEvict("all-products-store", key = "#storeId")
    fun deleteCacheGetProductsByStoreId(storeId: Long): List<ProductItem> {
       return  productUtils.getProductsByStoreId(storeId)
    }
}
