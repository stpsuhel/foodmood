package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.PrimaryRole
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class ProductWebController(val storeUtils: StoreUtils, val productUtils: ProductUtils) {


    @RequestMapping("product-information")
    fun getProductInformation(model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val allProductCompany = productUtils.getAllProductByCompanyId(userPrinciple.companyId)

        model.addAttribute("productList", allProductCompany)

        return "product/productInformation"
    }


    @RequestMapping(value = ["add-product"])
    fun getAddUpdateProduct(model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple


        model.addAttribute("product", ProductItem())
        model.addAttribute("storeList", storeUtils.getAllCompanyStore(userPrinciple.companyId))


        return "product/addUpdateProduct"
    }

    @RequestMapping(value = ["add-product"], method = [RequestMethod.POST])
    fun getSaveUpdateProduct(@Validated @ModelAttribute("product") product: ProductItem, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple


        product.companyId = userPrinciple.companyId
        productUtils.saveUpdateProduct(product)
        productUtils.deleteAllProductByCompanyCache(userPrinciple.companyId)
        productUtils.deleteAllProductCache()

        return "redirect:./product-information"
    }
}
