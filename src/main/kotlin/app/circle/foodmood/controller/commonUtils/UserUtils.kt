package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.utils.STUDENT_PASSWORD_CONSTANT
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList


@Service
class  UserUtils(val administrationRepository: AdministrationRepository) {

    @Cacheable("user-list-company", key = "#companyId")
    fun getUserList(companyId: Long): ArrayList<User>? {
        return administrationRepository.findAllByCompanyId(companyId)

    }

    @CacheEvict("user-list-company", key = "#companyId")
    fun deleteCacheUserList(companyId: Long) {
    }


    fun getGenaratedPassword(studentId: Long): String {

        return "$STUDENT_PASSWORD_CONSTANT$studentId@"
    }
    
}
