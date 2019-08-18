package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("public")
class PublicRestController(val productUtils: ProductUtils, val storeUtils: StoreUtils) {

    @GetMapping("all-product")
    fun getAllActiveProduct(): Response<ArrayList<ProductItemDataModel>> {
        var response = Response<ArrayList<ProductItemDataModel>>()
        var allProductItemDataModel = ArrayList<ProductItemDataModel>()

        try {
            val allProduct = productUtils.getAllProduct()

            val allStore = storeUtils.getAllStore()

            allProduct.forEach {
                val productItemDataModel = ProductItemDataModel()
                productItemDataModel.companyId = it.companyId!!
                productItemDataModel.id = it.id!!
                productItemDataModel.name = it.name!!
                productItemDataModel.price = it.price!!
                productItemDataModel.storeId = it.storeId!!
                productItemDataModel.description = it.description!!
                productItemDataModel.status = it.status!!
                productItemDataModel.isFreeDelivery = true
                productItemDataModel.discountPrice = 10
                productItemDataModel.isDiscount = true


                for (store in allStore) {
                    if (store.id == it.storeId) {
                        productItemDataModel.storeName = store.name!!
                        break
                    }
                }

                allProductItemDataModel.add(productItemDataModel)
            }

            response.isResultAvailable = true
            response.result = allProductItemDataModel
            response.isSuccessful = true
            return response
        } catch (e: Exception) {
            response.isResultAvailable = false
            response.isSuccessful = false
            return response

        }
    }

}
