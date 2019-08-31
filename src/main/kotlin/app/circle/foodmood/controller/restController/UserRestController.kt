package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.UserAddressUtils
import app.circle.foodmood.controller.commonUtils.UserBookmarkProductUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.AddressDataModel
import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.model.database.UserBookmarkProduct
import app.circle.foodmood.repository.UserAddressRepository
import app.circle.foodmood.repository.UserBookmarkProductRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("user")
class UserRestController(val userAddressRepository: UserAddressRepository, val userAddressUtils: UserAddressUtils,
                         val userBookmarkProductRepository: UserBookmarkProductRepository,
                         val userBookmarkProductUtils: UserBookmarkProductUtils) {

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("create-update-address")
    fun saveUpdateUserAddressDetails(@RequestBody address: AddressDataModel): Response<UserAddress> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val response = Response<UserAddress>()

        try {
            val userAddress = UserAddress()
            userAddress.addressLineOne = address.addressLineOne
            userAddress.addressLineTwo = address.addressLineTwo
            userAddress.locationLatitude = address.locationLatitude!!
            userAddress.locationLongitude = address.locationLongitude!!
            userAddress.description = address.description!!
            userAddress.companyId = userPrinciple.companyId
            userAddress.userId = userPrinciple.id

            val userAddressDetails = userAddressRepository.save(userAddress)
            response.isResultAvailable = true
            response.isSuccessful = true
            response.result = userAddressDetails
            userAddressUtils.deleteCacheUserAddressList(userPrinciple.companyId)
        } catch (e: Exception) {

            response.isResultAvailable = true
            response.isSuccessful = true
            response.message = arrayOf(e.message)
        }

        return response
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("get-user-address-by-user-id")
    fun getAllUserAddressByUserId(): Response<ArrayList<UserAddress>> {
        val response = Response<ArrayList<UserAddress>>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val allUserAddress = userAddressUtils.getUserAddressListByUserId(userPrinciple.companyId, userPrinciple.id)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = allUserAddress

        return response
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("create-update-user-Bookmark-product")
    fun saveUpdateUserBookmarkProductDetails(@RequestBody userBookmarkProduct: UserBookmarkProduct): Response<UserBookmarkProduct> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<UserBookmarkProduct>()

        userBookmarkProduct.userId = userPrinciple.id
        userBookmarkProduct.companyId = userPrinciple.companyId

        val userBookmarkProductDetails = userBookmarkProductRepository.save(userBookmarkProduct)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = userBookmarkProductDetails

        userBookmarkProductUtils.deleteCacheUserBookmarkList(userPrinciple.companyId)

        return response
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("get-user-bookmark-by-user-id")
    fun getAllUserBookmarkProductListByUserId(): Response<ArrayList<UserBookmarkProduct>> {
        val response = Response<ArrayList<UserBookmarkProduct>>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val allUserBookmarkProductList = userBookmarkProductUtils.getUserBookmarkListByUserId(userPrinciple.companyId, userPrinciple.id)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = allUserBookmarkProductList

        return response
    }
}
