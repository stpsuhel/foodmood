package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.UserBookmarkProduct
import app.circle.foodmood.repository.UserBookmarkProductRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserBookmarkProductUtils(val userBookmarkProductRepository: UserBookmarkProductRepository) {

    @Cacheable("user-bookmark-list", key = "#userId")
    fun getUserBookmarkListByUserId(companyId: Long, userId: Long): ArrayList<UserBookmarkProduct>? {
        return userBookmarkProductRepository.getAllByCompanyIdAndUserIdAndStatus(companyId, userId, Status.Active.value)
    }

    @CacheEvict("user-bookmark-list",  key = "#userId")
    fun deleteCacheUserBookmarkList(companyId: Long) {
    }
}
