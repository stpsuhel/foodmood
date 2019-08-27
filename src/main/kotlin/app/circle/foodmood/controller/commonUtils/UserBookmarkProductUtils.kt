package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.model.database.UserBookmarkProduct
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserAddressRepository
import app.circle.foodmood.repository.UserBookmarkProductRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.utils.STUDENT_PASSWORD_CONSTANT
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList

@Service
class  UserBookmarkProductUtils(val userBookmarkProductRepository: UserBookmarkProductRepository) {

    @Cacheable("user-bookmark-list")
    fun getUserBookmarkListByUserId(companyId: Long, userId: Long): ArrayList<UserBookmarkProduct>? {
        return userBookmarkProductRepository.getAllByCompanyIdAndUserIdAndStatus(companyId, userId, Status.Active.value)
    }

    @CacheEvict("user-bookmark-list")
    fun deleteCacheUserBookmarkList(companyId: Long) {
    }
}
