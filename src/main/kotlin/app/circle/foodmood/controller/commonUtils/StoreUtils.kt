package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.StoreRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class StoreUtils(val storeRepository: StoreRepository) {

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
}
