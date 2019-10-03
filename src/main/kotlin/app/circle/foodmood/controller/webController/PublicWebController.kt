package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.CategoryUtils
import app.circle.foodmood.controller.commonUtils.ImageUtils
import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.ImageSourceType
import app.circle.foodmood.utils.URL.HomeController.Companion.HOME_PAGE
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class PublicWebController(val productUtils: ProductUtils, val storeUtils: StoreUtils, val categoryUtils: CategoryUtils,
                          val imageUtils: ImageUtils) {

    @RequestMapping(HOME_PAGE)
    fun getRegistrationForm(model: Model): String {

        val allProductItemDataModel = ArrayList<ProductItemDataModel>()

        try {
            val allProduct = productUtils.getAllProduct()

            val allStore = storeUtils.getAllStore()

            allProduct.forEach { productITem ->
                val allImage = arrayListOf<String>()
                val productItemDataModel = ProductItemDataModel()
                productItemDataModel.companyId = productITem.companyId!!
                productItemDataModel.id = productITem.id!!
                productItemDataModel.name = productITem.name
                productItemDataModel.price = productITem.price!!
                productItemDataModel.storeId = productITem.storeId!!
                productItemDataModel.description = productITem.description
                productItemDataModel.status = productITem.status
                productItemDataModel.discountPrice = productITem.discountPrice
                productItemDataModel.isDiscount = productITem.isDiscount
                productItemDataModel.isFreeDelivery = productITem.freeDelivery
                productItemDataModel.categoryId = productITem.categoryId!!


                val categoryListByProductId = categoryUtils.getCategoryListByProductId(productITem.id!!)

                if (categoryListByProductId.isNotEmpty()) {
                    categoryListByProductId.forEach {
                        productItemDataModel.categoryList.add(it.categoryId!!)
                    }
                } else {
                    productItemDataModel.categoryList = arrayListOf(productITem.categoryId!!)
                }


                if (productITem.primaryImageId != ID_NOT_FOUND) {
                    val imageSource = imageUtils.getImageById(productITem.primaryImageId)
                    productItemDataModel.primaryImageUrl = imageSource?.imageURL!!
                }


                val imageBySourceIdAndSourceType = imageUtils.getImageBySourceIdAndSourceType(productITem.id!!, ImageSourceType.PRODUCT_IMAGE.value)


                imageBySourceIdAndSourceType.forEach {
                    allImage.add(it.imageURL!!)
                }

                productItemDataModel.imageList = allImage


                for (store in allStore) {
                    if (store.id == productITem.storeId) {
                        productItemDataModel.storeName = store.name!!
                        break
                    }
                }

                allProductItemDataModel.add(productItemDataModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        model.addAttribute("allProduct", allProductItemDataModel)
        return "public/home"
    }

    @RequestMapping("terms-condition")
    fun getTerms(): String {
        return "public/terms"
    }
}
