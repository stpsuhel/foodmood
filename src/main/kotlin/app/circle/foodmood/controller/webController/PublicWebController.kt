package app.circle.foodmood.controller.webController

import app.circle.foodmood.utils.URL.HomeController.Companion.HOME_PAGE
import org.jetbrains.annotations.Contract
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class PublicWebController {

    @RequestMapping(HOME_PAGE)
    fun getRegistrationForm(): String {
        return "public/home"
    }
    @RequestMapping("terms-condition")
    fun getTerms(): String {
        return "public/terms"
    }
}
