package app.circle.foodmood.controller.webController


import app.circle.foodmood.controller.commonUtils.OrderDeliveryUtils
import app.circle.foodmood.controller.commonUtils.UserUtils
import app.circle.foodmood.model.dataModel.DeliveryManDataModel
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ProcessDataModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("order-delivery")
class OrderDeliveryWebController(val orderDeliveryUtils: OrderDeliveryUtils, val userUtils: UserUtils,
                                 val processDataModel: ProcessDataModel) {

    @RequestMapping("dashboard")
    fun getDeliveryManInformation(model: Model): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val deliveryManList = orderDeliveryUtils.getAllDeliveryManByCompanyId(userPrinciple.companyId)

        val deliveryManDataModelList = ArrayList<DeliveryManDataModel>()
        deliveryManList.forEach {

            val user = userUtils.getUserByCompanyIdAndIdAndPrimaryRole(userPrinciple.companyId, it.userId!!)

            val deliveryManDataModel = processDataModel.processDeliveryManToDeliveryManDataModel(it, user)

            deliveryManDataModelList.add(deliveryManDataModel)
        }

        model.addAttribute("deliveryManList", deliveryManDataModelList)

        return "company/deliveryManDashboard"
    }
}
