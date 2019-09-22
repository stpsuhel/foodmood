package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

/**
 * Only Company Admin can access this
 */
@RestController
@RequestMapping("admin/product")
class ProductRestController(val productUtils: ProductUtils) {

    @PostMapping("save-free-delivery")
    fun saveFreeDelivery(@RequestParam id: Long, @RequestBody productItem: ProductItem): Response<ProductItem>{
        val response = Response<ProductItem>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val productInfo = productUtils.getByProductId(id)

        productInfo.freeDelivery = productItem.freeDelivery

        productUtils.saveUpdateProduct(productInfo)
        productUtils.deleteAllProductCache()
        productUtils.deleteAllProductByCompanyCache(userPrinciple.companyId)

        response.isSuccessful = true
        response.isResultAvailable = true
        response.result = productInfo


        productUtils.deleteAllProductCache()
        productUtils.deleteAllProductByCompanyCache(userPrinciple.companyId)

        return response
    }
}
