package app.circle.foodmood.controller.restController


import app.circle.foodmood.controller.commonUtils.ImageUtils
import app.circle.foodmood.controller.commonUtils.OrderDeliveryUtils
import app.circle.foodmood.controller.commonUtils.UserAddressUtils
import app.circle.foodmood.controller.commonUtils.UserUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.DeliveryManDetails
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("delivery")
class DeliveryRestController(val userUtils: UserUtils, val userAddressUtils: UserAddressUtils, val orderDeliveryUtils: OrderDeliveryUtils,
                             val imageUtils: ImageUtils) {

    @GetMapping("get-details")
    fun getDeliveryManDetails(): Response<DeliveryManDetails>{
        val response = Response<DeliveryManDetails>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val userInfo = userUtils.getUserById(userPrinciple.id)
        if (userInfo != null){
            val deliveryManDetails = DeliveryManDetails()

            deliveryManDetails.id = userInfo.id
            deliveryManDetails.companyId = userInfo.companyId
            deliveryManDetails.email = userInfo.email
            deliveryManDetails.name = userInfo.name
            deliveryManDetails.userName = userInfo.username
            deliveryManDetails.phone = userInfo.phone

            val userAddressInfo = userAddressUtils.getUserAddressByUserId(userInfo.id)
            userAddressInfo?.let {
                deliveryManDetails.addressLineOne = userAddressInfo.addressLineOne
                deliveryManDetails.addressLineTwo = userAddressInfo.addressLineTwo
                deliveryManDetails.latitude = it.locationLatitude.toInt()
                deliveryManDetails.longitude = it.locationLongitude.toInt()
            }

            val deliveryManInfo = orderDeliveryUtils.getDeliveryManByUserId(userInfo.id)
            deliveryManInfo?.let {
                deliveryManDetails.deliveryId = deliveryManInfo.id
                deliveryManDetails.deliveryStatus = deliveryManInfo.deliveryStatus!!
            }

            val imageDetails = imageUtils.getImageBySourceId(userInfo.id)
            imageDetails?.let {
                deliveryManDetails.imageURL = imageDetails.imageURL!!
            }

            response.isSuccessful = true
            response.isResultAvailable = true
            response.result = deliveryManDetails

        }else{
            response.isSuccessful = false
            response.isResultAvailable = false
            response.message = arrayOf("User Information Not Found!!")
        }

        return response
    }

}
