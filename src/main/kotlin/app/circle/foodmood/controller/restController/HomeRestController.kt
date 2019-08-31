package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.HomeUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.DashboardDataModel
import app.circle.foodmood.repository.CompanyRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.Status
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME_PAGE
import app.circle.foodmood.utils.URL.HomeController.Companion.LOGIN_PAGE
import org.springframework.core.annotation.OrderUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("home")
class HomeRestController(val homeUtils: HomeUtils) {

    @GetMapping("customer")
    fun getHomeData(): Response<DashboardDataModel> {
        val response = Response<DashboardDataModel>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val dashboardDataModel = DashboardDataModel()

        try {
            dashboardDataModel.countCustomer = homeUtils.getCountDistinctCustomer(userPrinciple.companyId)
            dashboardDataModel.countOrder = homeUtils.getCountOrder(userPrinciple.companyId)
            dashboardDataModel.countProductItem = homeUtils.getCountItem(userPrinciple.companyId)

            response.isResultAvailable = true
            response.isSuccessful = true
            response.result = dashboardDataModel

        } catch (e: Exception) {

            response.isSuccessful = false
            response.isResultAvailable = false
            response.result = null
            e.printStackTrace()
            response.message = arrayOf(e.message)
        }

        return response
    }
}
