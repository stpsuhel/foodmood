package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.STUDENT_PASSWORD_CONSTANT
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList


@Service
class  UserUtils(val administrationRepository: AdministrationRepository , var userRepository: UserRepository) {

    @Cacheable("user-list-company", key = "#companyId")
    fun getUserList(companyId: Long): ArrayList<User>? {
        return administrationRepository.findAllByCompanyId(companyId)

    }

    @CacheEvict("user-list-company", key = "#companyId")
    fun deleteCacheUserList(companyId: Long) {
    }

    @CacheEvict("user-list-company")
    fun deleteCacheUserList() {
    }


    fun getGenaratedPassword(studentId: Long): String {

        return "$STUDENT_PASSWORD_CONSTANT$studentId@"
    }

    fun getUserById(userId:Long): User ?{
        return userRepository.findById(userId).get()
    }

    fun getUserByCompanyIdAndIdAndPrimaryRole(companyId: Long, id: Long): User? {
        return administrationRepository.findByCompanyIdAndIdAndPrimaryRole(companyId, id, PrimaryRole.CompanyDeliveryMan)
    }

    fun getUserByIdAndPrimaryRole(id: Long): User?{
        return administrationRepository.findByIdAndPrimaryRole(id, PrimaryRole.CompanyDeliveryMan)
    }

    fun getUserByCompanyIdAndPrimaryRole(companyId: Long, primaryRole: PrimaryRole): ArrayList<User>{
        return administrationRepository.findAllByCompanyIdAndPrimaryRole(companyId, primaryRole)
    }

    fun getUserByAdminPrimaryRole(primaryRole: PrimaryRole): ArrayList<User>{
        return administrationRepository.findAllByPrimaryRole(primaryRole)
    }
}
