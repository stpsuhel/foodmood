package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserAddressRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.utils.STUDENT_PASSWORD_CONSTANT
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList

@Service
class  UserAddressUtils(val userAddressRepository: UserAddressRepository) {

    @Cacheable("user-address-list")
    fun getUserAddressListByUserId(companyId: Long, userId: Long): ArrayList<UserAddress>? {
        return userAddressRepository.getAllByCompanyIdAndUserIdAndStatus(companyId, userId, Status.Active.value)
    }

    @CacheEvict("user-address-list")
    fun deleteCacheUserAddressList(companyId: Long) {
    }
}
