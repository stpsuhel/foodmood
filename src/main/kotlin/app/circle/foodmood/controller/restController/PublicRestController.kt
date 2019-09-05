package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.CategoryDataModel
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.model.dataModel.StoreDataModel
import app.circle.foodmood.model.dataModel.TrendingDataModel
import app.circle.foodmood.model.database.UserToken
import app.circle.foodmood.repository.UserTokenRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.Status
import app.circle.foodmood.utils.TOP_TRENDING
import org.joda.time.LocalDate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("public")
class PublicRestController(val productUtils: ProductUtils, val storeUtils: StoreUtils, val categoryUtils: CategoryUtils,
                           val orderUtils: OrderUtils, val globalUtils: GlobalUtils, val userTokenRepository: UserTokenRepository) {

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
    fun getAllCategory(): Response<List<CategoryDataModel>> {

        val response = Response<List<CategoryDataModel>>()

        response.isSuccessful = true
        val allCategoryList = categoryUtils.getAllCategoryList()

        var categoryList = ArrayList<CategoryDataModel>()
        val categoryCount = HashMap<Long, Int>()


        productUtils.getAllProduct().forEach { productItem ->

            productItem.categoryId?.let {

                if (categoryCount.containsKey(it)) {
                    var existingCount = categoryCount.get(it)!!
                    categoryCount.put(it, ++existingCount)
                } else {
                    categoryCount.put(it, 1)

                }
            }
        }
        allCategoryList.forEach {
            if (categoryCount.containsKey(it.id!!)) {
                val itemDataModel = CategoryDataModel()
                itemDataModel.id = it.id!!
                itemDataModel.companyId = it.companyId!!
                itemDataModel.name = it.name!!
                itemDataModel.totalItem = categoryCount.get(it.id!!)!!
                itemDataModel.status = Status.Active.value
                categoryList.add(itemDataModel)
            }
        }


        response.result = categoryList
        response.isResultAvailable = true

        return response
    }

    @GetMapping("all-discount-product")
    fun getAllProductDiscount(): Response<List<ProductItemDataModel>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<List<ProductItemDataModel>>()
        val allProductItemDataModel = ArrayList<ProductItemDataModel>()

        try {
            val allProduct = productUtils.getProductByDiscount(userPrinciple.companyId)
            val allStore = storeUtils.getAllStore()

            allProduct.forEach {
                try {
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
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

    @GetMapping("all-category-product")
    fun getAllProductByCategory(@RequestParam(required = true, defaultValue = ID_NOT_FOUND.toString()) categoryId: Long = ID_NOT_FOUND): Response<List<ProductItemDataModel>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<List<ProductItemDataModel>>()
        val allProductItemDataModel = ArrayList<ProductItemDataModel>()

        try {
            if (categoryId == ID_NOT_FOUND) {
                response.isResultAvailable = false
                response.isSuccessful = false
                response.message = arrayOf("Id not found")

                return response
            }
            val allProduct = productUtils.getProductByCategory(userPrinciple.companyId, categoryId!!)
            val allStore = storeUtils.getAllStore()

            allProduct.forEach {
                try {
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
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

    @GetMapping("get-top-ten-product")
    fun getTopTenProduct(): Response<ArrayList<TrendingDataModel>> {
        val response = Response<ArrayList<TrendingDataModel>>()
        val allProductItemDataModel = ArrayList<ProductItemDataModel>()

        val currentDate = globalUtils.getCurrentDate()
        val oneWeekBeforeDate = globalUtils.getOneWeekBeforeDate(LocalDate.now())

        val allProductOfLastSevenDays = orderUtils.getAllOrderProductOfLastSevenDays(currentDate!!, oneWeekBeforeDate!!)

        val orderIdList = ArrayList<Long>()
        allProductOfLastSevenDays.forEach {
            orderIdList.add(it.id!!)
        }
        val allOrderProductList = orderUtils.getAllOrderProductByOrderId(orderIdList)

        val hashMap = HashMap<Long, Int>()
        allOrderProductList.forEach {
            if (hashMap.containsKey(it.productId)) {
                val quantity = hashMap.get(it.productId)
                hashMap.put(it.productId!!, quantity!! + it.quantity)
            } else {
                hashMap.put(it.productId!!, it.quantity)
            }
        }

        val sortedMap = hashMap.toList().sortedByDescending(Pair<*, Int>::second).toMap()
        val productList = sortedMap.toList().take(TOP_TRENDING)

        val productTrendingList = ArrayList<TrendingDataModel>()
        productList.forEach {
            val trendingItem = TrendingDataModel()

            trendingItem.productId = it.first
            trendingItem.count = it.second

            productTrendingList.add(trendingItem)
        }


        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = productTrendingList

        return response
    }


    @PostMapping("update-token")
    fun updateTokenUnknownUserToken(@Valid @RequestBody userToken: UserToken): Response<UserToken> {
        var response: Response<UserToken> = Response()
        val token = userTokenRepository.save(userToken)
        response.result = token
        response.isResultAvailable = true
        response.isSuccessful = true
        return response
    }
}
