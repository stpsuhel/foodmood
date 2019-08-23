package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.CategoryUtils
import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.ProcessDataModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class ProductWebController(val storeUtils: StoreUtils, val productUtils: ProductUtils, val processDataModel: ProcessDataModel,
                           val categoryUtils: CategoryUtils) {


    @RequestMapping("product-information")
    fun getProductInformation(model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val allProductCompany = productUtils.getAllProductByCompanyId(userPrinciple.companyId)

        val productList = ArrayList<ProductItemDataModel>()
        allProductCompany.forEach {
            productList.add(processDataModel.processProductItemToProcessItemDataModel(it, storeUtils.getStoreById(it.storeId!!)))
        }

        model.addAttribute("productList", productList)

        return "product/productInformation"
    }


    @RequestMapping(value = ["add-product"])
    fun getAddUpdateProduct(@RequestParam("id", required = false) id:String? = null, model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if(id.isNullOrEmpty()) {
            model.addAttribute("product", ProductItem())
            model.addAttribute("category", categoryUtils.getAllCategoryList())
            model.addAttribute("storeList", storeUtils.getAllCompanyStore(userPrinciple.companyId))

        }else{
            val productId = id!!.toLong()
            val productInfo = productUtils.getProductById(userPrinciple.companyId, productId)

            val storeInfo = storeUtils.getStoreById(productInfo.storeId!!)

            model.addAttribute("product", productInfo)
            model.addAttribute("storeList", storeInfo)
        }

        return "product/addUpdateProduct"
    }

    @RequestMapping(value = ["add-product"], method = [RequestMethod.POST])
    fun getSaveUpdateProduct(@RequestParam("id", required = false) id: String? = null,
                             @Validated @ModelAttribute("product") product: ProductItem, bindingResult: BindingResult,
                             model: Model, redirectAttributes: RedirectAttributes): String {

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if(product.storeId == null){
            bindingResult.rejectValue("storeId", "500", "Please Select a Store")
        }
        if(product.price == null || product.price == 0){
            bindingResult.rejectValue("price", "500", "Product price must be greater then Zero")
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("storeList", storeUtils.getAllCompanyStore(userPrinciple.companyId))
            return "product/addUpdateProduct"
        }

        product.companyId = userPrinciple.companyId

        product.discountPrice?.let {
            if(it > 0){
                product.isDiscount = true
            }else{
                product.discountPrice = 0
            }
        }

        productUtils.saveUpdateProduct(product)
        productUtils.deleteAllProductByCompanyCache(userPrinciple.companyId)
        productUtils.deleteAllProductCache()

        return "redirect:./product-information"
    }
}
