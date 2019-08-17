package app.circle.foodmood.controller.webController

import app.circle.foodmood.repository.CompanyRepository
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME_PAGE
import app.circle.foodmood.utils.URL.HomeController.Companion.LOGIN_PAGE
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeWebController(val companyRepository: CompanyRepository) {

    @RequestMapping(value = [HOME, HOME_PAGE])
    fun getHome(model: Model): String {




        return "index"
    }


    @RequestMapping(value = [LOGIN_PAGE])
    fun showLoginPage(): String {

        print("login Called")
        return "login"
    }

}
