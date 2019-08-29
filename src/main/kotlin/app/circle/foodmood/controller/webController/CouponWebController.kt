package app.circle.foodmood.controller.webController

import app.circle.foodmood.repository.CouponRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class CouponWebController(val couponRepository: CouponRepository) {

    @RequestMapping("create-coupon")
    fun getSaveCoupon(model: Model): String{
        return "product/addUpdateCoupon"
    }

}
