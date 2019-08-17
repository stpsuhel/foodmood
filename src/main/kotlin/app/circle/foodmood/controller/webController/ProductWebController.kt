package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.RoleUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.controller.commonUtils.UserUtils
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserRepository
import app.circle.foodmood.utils.ProcessDataModel
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class ProductWebController(val administrationRepository: AdministrationRepository, val userRepository: UserRepository, val encoder: PasswordEncoder,
                           val userUtils: UserUtils, val processDataModel: ProcessDataModel, val roleUtils: RoleUtils,
                           val storeUtils: StoreUtils) {


    @GetMapping("product-information")
    fun getProductInformation(): String {

        return "product/productInformation"
    }

}
