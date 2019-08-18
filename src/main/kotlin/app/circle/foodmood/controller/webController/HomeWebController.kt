package app.circle.foodmood.controller.webController

import app.circle.foodmood.repository.CompanyRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME_PAGE
import app.circle.foodmood.utils.URL.HomeController.Companion.LOGIN_PAGE
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeWebController(val companyRepository: CompanyRepository) {

    @RequestMapping(value = [HOME, HOME_PAGE])
    fun getHome(model: Model): String {

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple


        var role: PrimaryRole = userPrinciple.primaryRole



        if (role == PrimaryRole.ADMIN) {

        } else {

        }

        return "index"
    }


    @RequestMapping(value = [LOGIN_PAGE])
    fun showLoginPage(): String {

        print("login Called")
        return "login"
    }

}
