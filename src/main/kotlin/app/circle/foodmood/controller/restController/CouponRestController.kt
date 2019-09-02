package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.CouponDataModel
import app.circle.foodmood.model.database.Coupon
import app.circle.foodmood.repository.CouponRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ProcessDataModel
import app.circle.foodmood.utils.Status
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("coupon")
class CouponRestController(val couponRepository: CouponRepository, val processDataModel: ProcessDataModel) {

    @PostMapping("create-update-coupon")
    fun saveCoupon(@RequestBody couponDataModel: CouponDataModel, @RequestParam("id", required = false) id: Long? = null): Response<Coupon>{
        val response = Response<Coupon>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        var couponInfo = Coupon()
        if(id == null) {
            val coupon = Coupon()
            couponInfo = processDataModel.processCouponDataModelToCoupon(couponDataModel, coupon)
            couponInfo.companyId = userPrinciple.companyId

        }else{
            val coupon = couponRepository.getByCompanyIdAndIdAndStatus(userPrinciple.companyId, id, Status.Active.value)

            couponInfo = processDataModel.processCouponDataModelToCoupon(couponDataModel, coupon)
        }

        val saveCoupon = couponRepository.save(couponInfo)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = saveCoupon

        return response
    }

}
