package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.repository.UserAddressRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("user")
class UserRestController(val userAddressRepository: UserAddressRepository) {

    @PostMapping("create-save-user-address")
    fun getUserAddressDetails(@RequestBody userAddress: UserAddress): Response<UserAddress> {
        val response = Response<UserAddress>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        userAddress.userId = userPrinciple.id
        userAddress.companyId = userPrinciple.companyId

        val userAddressDetails = userAddressRepository.save(userAddress)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = userAddressDetails

        return response
    }
}
