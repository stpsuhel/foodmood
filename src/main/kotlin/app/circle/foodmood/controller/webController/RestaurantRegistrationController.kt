package app.circle.foodmood.controller.webController

import org.jetbrains.annotations.Contract
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("restaurant")
class RestaurantRegistrationController {

    @RequestMapping("contact-form")
    fun getRegistrationForm(): String {
        return "registration/restaurantRegistration"
    }
}
