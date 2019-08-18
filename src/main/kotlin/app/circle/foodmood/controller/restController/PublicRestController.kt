package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.model.dataModel.StoreDataModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("public")
class PublicRestController(val productUtils: ProductUtils, val storeUtils: StoreUtils) {

    @GetMapping("all-product")
    fun getAllActiveProduct(): Response<List<ProductItemDataModel>> {
        var response = Response<List<ProductItemDataModel>>()
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
                productItemDataModel.discountPrice = 80
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
            response.result = allProductItemDataModel.shuffled()
            response.isSuccessful = true
            return response
        } catch (e: Exception) {
            response.isResultAvailable = false
            response.isSuccessful = false
            return response

        }
    }


    @GetMapping("all-store-details")
    fun allStore(): Response<List<StoreDataModel>> {

        val response = Response<List<StoreDataModel>>()
        val lisOfProductDataModel = ArrayList<StoreDataModel>()



        try {
            val storeList = storeUtils.getAllStore()

            storeList.forEach {
                val storeDataModel = StoreDataModel()
                val productsByStoreId = storeUtils.getProductsByStoreId(it.id!!)
                storeDataModel.details = it
                storeDataModel.productList = productsByStoreId

                lisOfProductDataModel.add(storeDataModel)
            }



            response.result = lisOfProductDataModel
            response.isSuccessful = true
            response.isResultAvailable = true
        } catch (e: Exception) {
            response.result = mutableListOf()
            response.isSuccessful = false
            response.isResultAvailable = false
        }


        return response
    }

}
