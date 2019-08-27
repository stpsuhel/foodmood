package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.CategoryUtils
import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.model.dataModel.StoreDataModel
import app.circle.foodmood.model.database.Category
import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("public")
class PublicRestController(val productUtils: ProductUtils, val storeUtils: StoreUtils, val categoryUtils: CategoryUtils) {

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
                productItemDataModel.name = it.name
                productItemDataModel.price = it.price!!
                productItemDataModel.storeId = it.storeId!!
                productItemDataModel.description = it.description
                productItemDataModel.status = it.status
                productItemDataModel.discountPrice = it.discountPrice
                productItemDataModel.isDiscount = it.isDiscount


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


    @GetMapping("all-product-category")
    fun getAllCategory(): Response<List<Category>> {

        val response = Response<List<Category>>()

        response.isSuccessful = true
        response.result = categoryUtils.getAllCategoryList().shuffled()
        response.isResultAvailable = true

        return response
    }

    @GetMapping("all-product-discount")
    fun getAllProductDiscount(): Response<ArrayList<ProductItem>>{
        val response = Response<ArrayList<ProductItem>>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val allDiscountProduct = productUtils.getProductByDiscount(userPrinciple.companyId)

        response.isSuccessful = true
        response.isResultAvailable = true
        response.result = allDiscountProduct

        return response
    }
}
