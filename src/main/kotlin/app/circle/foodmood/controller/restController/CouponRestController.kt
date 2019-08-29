package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.Coupon
import app.circle.foodmood.repository.CouponRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("coupon")
class CouponRestController(val couponRepository: CouponRepository) {

    @PostMapping("create-coupon")
    fun saveCoupon(@Validated @RequestBody coupon: Coupon): Response<Coupon>{
        val response = Response<Coupon>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        coupon.companyId = userPrinciple.companyId

        val saveCoupon = couponRepository.save(coupon)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = saveCoupon

        return response
    }

}
