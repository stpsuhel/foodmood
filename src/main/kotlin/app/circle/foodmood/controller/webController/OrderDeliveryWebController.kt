package app.circle.foodmood.controller.webController


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("order-delivery")
class OrderDeliveryWebController() {

    @RequestMapping("dashboard")
    fun getDeliveryManInformation(): String{
        return "company/deliveryManDashboard"
    }

}
