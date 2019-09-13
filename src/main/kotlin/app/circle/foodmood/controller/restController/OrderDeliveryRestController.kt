package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.CouponDataModel
import app.circle.foodmood.model.database.Coupon
import app.circle.foodmood.model.database.DeliveryMan
import app.circle.foodmood.repository.CouponRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ProcessDataModel
import app.circle.foodmood.utils.Status
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("order-delivery")
class OrderDeliveryRestController() {

    /*@GetMapping("deshbord")
    fun getDeliveryManInformation(): Response<DeliveryMan>
*/
}
