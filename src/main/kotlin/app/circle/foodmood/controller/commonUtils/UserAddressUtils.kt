package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.repository.UserAddressRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserAddressUtils(val userAddressRepository: UserAddressRepository) {

    @Cacheable("user-address-list", key = "#userId")
    fun getUserAddressListByUserId(companyId: Long, userId: Long): ArrayList<UserAddress>? {
        return userAddressRepository.getAllByCompanyIdAndUserIdAndStatus(companyId, userId, Status.Active.value)
    }

    @CacheEvict("user-address-list", key = "#userId")
    fun deleteCacheUserAddressList(companyId: Long) {
    }


    fun getUserAddressById(id: Long): UserAddress? {
        val address = userAddressRepository.findById(id)

        return if (address.isPresent) {

            address.get()
        } else {
            null
        }
    }
}
