package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ImageUtils
import app.circle.foodmood.controller.commonUtils.UserAddressUtils
import app.circle.foodmood.controller.commonUtils.UserBookmarkProductUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.AddressDataModel
import app.circle.foodmood.model.dataModel.UpdateTokenDataModel
import app.circle.foodmood.model.dataModel.UserDetails
import app.circle.foodmood.model.database.SourceImage
import app.circle.foodmood.model.database.UserAddress
import app.circle.foodmood.model.database.UserBookmarkProduct
import app.circle.foodmood.repository.*
import app.circle.foodmood.security.User
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ImageSourceType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("user")
class UserRestController(val userAddressRepository: UserAddressRepository, val userAddressUtils: UserAddressUtils,
                         val userBookmarkProductRepository: UserBookmarkProductRepository,
                         val userBookmarkProductUtils: UserBookmarkProductUtils, val imageUtils: ImageUtils,
                         val userRepository: UserRepository, val administrationRepository: AdministrationRepository) {

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("update-fcm-token")
    fun updateFcmToken(@RequestBody updateTokenDataModel: UpdateTokenDataModel): Response<String>{
        val response = Response<String>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val userInfo = userRepository.findById(userPrinciple.id)
        if(userInfo.isPresent){
            val user = userInfo.get()
            user.fcmToken = updateTokenDataModel.token

            val saveUser = userRepository.save(user)
            response.isResultAvailable = true
            response.isSuccessful = true
            response.result = "FCM Token Successfully updated!!"
        }else{
            response.isResultAvailable = false
            response.isSuccessful = false
            response.message = arrayOf("User not found!!")
        }
        return response
    }

    @PostMapping("update-user")
    fun updateUserDetails(@RequestBody userDetails: UserDetails): Response<User>{
        val response = Response<User>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val userInfo = administrationRepository.findByCompanyIdAndId(userPrinciple.companyId, userDetails.id!!)

        if (userInfo != null){
            if (userDetails.name?.isNotEmpty()!! && userDetails.phone.isNotEmpty()) {
                userInfo.name = userDetails.name
                userInfo.phone = userDetails.phone

                val updateUser = userRepository.save(userInfo)

                val imageInfo = SourceImage()

                imageInfo.imageURL = userDetails.imageURL
                imageInfo.sourceId = updateUser.id
                imageInfo.sourceType = ImageSourceType.USER_PROFILE_IMAGE.value
                imageInfo.companyId = updateUser.companyId

                imageUtils.saveSourceImage(imageInfo)

                response.isSuccessful = true
                response.isResultAvailable = true
                response.result = updateUser
            }else{
                response.message = arrayOf("Name and Phone number cannot be Empty")
            }
        }else{
            response.message = arrayOf("User not found")
        }

        return  response
    }

    @GetMapping("get-profile-image")
    fun getUserProfileImage(): Response<String>{
        val response = Response<String>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val image = imageUtils.getImageBySourceId(userPrinciple.id)
        image?.let {
            response.result = it.imageURL
            response.isResultAvailable = true
            response.isSuccessful = true

            return response
        }

        response.isSuccessful = false
        response.isResultAvailable = false
        response.result = ""
        response.message = arrayOf("Image not found!!")

        return response
    }
}
