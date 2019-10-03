package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.ApplicationStatus
import app.circle.foodmood.model.database.ApplicationVersion
import app.circle.foodmood.model.database.UserToken
import app.circle.foodmood.repository.ApplicationStatusRepository
import app.circle.foodmood.repository.ApplicationVersionRepository
import app.circle.foodmood.repository.UserTokenRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.*
import org.joda.time.LocalDate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("public")
class PublicRestController(val productUtils: ProductUtils, val storeUtils: StoreUtils, val categoryUtils: CategoryUtils, val imageUtils: ImageUtils, val userUtils: UserUtils, val userAddressUtils: UserAddressUtils,
                           val orderUtils: OrderUtils, val globalUtils: GlobalUtils, val userTokenRepository: UserTokenRepository, val processDataModel: ProcessDataModel, val applicationVersionRepository: ApplicationVersionRepository, val applicationStatusRepository: ApplicationStatusRepository) {

    @GetMapping("all-product")
    fun getAllActiveProduct(): Response<List<ProductItemDataModel>> {
        val response = Response<List<ProductItemDataModel>>()
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
                storeDataModel.details = processDataModel.processStoreToStoreDetails(it)!!
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
                    var existingCount = categoryCount[it]!!
                    categoryCount[it] = ++existingCount
                } else {
                    categoryCount[it] = 1

                }
            }
        }
        allCategoryList.forEach {
            if (categoryCount.containsKey(it.id!!)) {
                val itemDataModel = CategoryDataModel()
                itemDataModel.id = it.id!!
                itemDataModel.companyId = it.companyId!!
                itemDataModel.name = it.name!!
                itemDataModel.imageURL = it.imageURl!!
                itemDataModel.totalItem = categoryCount[it.id!!]!!
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

            allProduct.forEach { productItem ->
                try {

                    val allImage = arrayListOf<String>()

                    val productItemDataModel = ProductItemDataModel()
                    productItemDataModel.companyId = productItem.companyId!!
                    productItemDataModel.id = productItem.id!!
                    productItemDataModel.name = productItem.name
                    productItemDataModel.price = productItem.price!!
                    productItemDataModel.storeId = productItem.storeId!!
                    productItemDataModel.description = productItem.description
                    productItemDataModel.status = productItem.status
                    productItemDataModel.discountPrice = productItem.discountPrice
                    productItemDataModel.isDiscount = productItem.isDiscount

                    productItemDataModel.categoryId = productItem.categoryId!!


                    val categoryListByProductId = categoryUtils.getCategoryListByProductId(productItem.id!!)

                    if (categoryListByProductId.isNotEmpty()) {
                        categoryListByProductId.forEach {
                            productItemDataModel.categoryList.add(it.categoryId!!)
                        }
                    } else {
                        productItemDataModel.categoryList = arrayListOf(productItem.categoryId!!)
                    }


                    if (productItem.primaryImageId != ID_NOT_FOUND) {
                        val imageSource = imageUtils.getImageById(productItem.primaryImageId)
                        productItemDataModel.primaryImageUrl = imageSource?.imageURL!!
                    }


                    val imageBySourceIdAndSourceType = imageUtils.getImageBySourceIdAndSourceType(productItem.id!!, ImageSourceType.PRODUCT_IMAGE.value)


                    imageBySourceIdAndSourceType.forEach {
                        allImage.add(it.imageURL!!)
                    }

                    productItemDataModel.imageList = allImage

                    for (store in allStore) {
                        if (store.id == productItem.storeId) {
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

            allProduct.forEach { productItem ->
                try {
                    val productItemDataModel = ProductItemDataModel()
                    val allImage = arrayListOf<String>()

                    productItemDataModel.companyId = productItem.companyId!!
                    productItemDataModel.id = productItem.id!!
                    productItemDataModel.name = productItem.name
                    productItemDataModel.price = productItem.price!!
                    productItemDataModel.storeId = productItem.storeId!!
                    productItemDataModel.description = productItem.description
                    productItemDataModel.status = productItem.status
                    productItemDataModel.discountPrice = productItem.discountPrice
                    productItemDataModel.isDiscount = productItem.isDiscount


                    productItemDataModel.categoryId = productItem.categoryId!!


                    val categoryListByProductId = categoryUtils.getCategoryListByProductId(productItem.id!!)

                    if (categoryListByProductId.isNotEmpty()) {
                        categoryListByProductId.forEach {
                            productItemDataModel.categoryList.add(it.categoryId!!)
                        }
                    } else {
                        productItemDataModel.categoryList = arrayListOf(productItem.categoryId!!)
                    }



                    if (productItem.primaryImageId != ID_NOT_FOUND) {
                        val imageSource = imageUtils.getImageById(productItem.primaryImageId)
                        productItemDataModel.primaryImageUrl = imageSource?.imageURL!!
                    }


                    val imageBySourceIdAndSourceType = imageUtils.getImageBySourceIdAndSourceType(productItem.id!!, ImageSourceType.PRODUCT_IMAGE.value)


                    imageBySourceIdAndSourceType.forEach {
                        allImage.add(it.imageURL!!)
                    }

                    productItemDataModel.imageList = allImage

                    for (store in allStore) {
                        if (store.id == productItem.storeId) {
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

        val allProductOfLastSevenDays = orderUtils.getAllOrderByPreviousDateToToday(oneWeekBeforeDate!!, currentDate!!)

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


    @GetMapping("app-version")
    fun getCurrentAppVersion(): Response<ApplicationVersion> {
        val response: Response<ApplicationVersion> = Response()


        val applicationVersionByStatus = applicationVersionRepository.getApplicationVersionByStatus(Status.Active.value)


        applicationVersionByStatus?.let {
            response.result = it
            response.isResultAvailable = true
            response.isSuccessful = true
        }

        return response
    }

    @GetMapping("app-status")
    fun getCurrentAppStatus(): Response<ApplicationStatus> {
        val response: Response<ApplicationStatus> = Response()

        val applicationVersionByStatus = applicationStatusRepository.getApplicationStatusByStatus(Status.Active.value)
        applicationVersionByStatus?.let {
            response.result = it
            response.isResultAvailable = true
            response.isSuccessful = true
        }

        return response
    }


    @GetMapping("delivery-data")
    fun getDeliveryData(): Response<ArrayList<OrderHistory>> {

        val response = Response<ArrayList<OrderHistory>>()
        val orderList = ArrayList<Long>()
        val allOrderList = arrayListOf<OrderHistory>()

        try {
            val orderProductList = orderUtils.getAllOrder()
            val orderIdList = arrayListOf<Long>()
            orderProductList.forEach {
                orderIdList.add(it.id!!)
            }

            val allOrder = orderUtils.getAllOrderByIdList(orderIdList)

            val orderDetailsList = orderUtils.getAllOrderProductByOrderList(orderIdList)

            allOrder.forEach {
                try {
                    val orderHistory = OrderHistory()
                    orderHistory.orderId = it.id!!
                    orderHistory.hasDiscount = it.totalDiscountPrice!! < it.totalPrice!!
                    orderHistory.orderDiscountPrice = it.totalDiscountPrice!!
                    orderHistory.orderOriginalPrice = it.totalPrice!!
                    orderHistory.orderStatus = it.orderStatus!!
                    orderHistory.orderDate = it.orderDate!!

                    for (orderProduct in orderDetailsList) {
                        try {
                            if (orderProduct.orderId == it.id) {
                                val orderItem = OrderItemDetails()
                                orderItem.id = orderProduct.id!!
                                orderItem.price = orderProduct.perProductPrice!!
                                orderItem.priceDiscount = orderProduct.perProductDiscountPrice!!
                                orderItem.hasDiscount = orderProduct.hasDiscount!!
                                orderItem.quantity = orderProduct.quantity!!
                                val product = productUtils.getByProductId(orderProduct.productId!!)
                                orderItem.name = product.name
                                try {
                                    val store = storeUtils.getStoreById(orderProduct.storeId!!)

                                    orderHistory.storeName = store.name!!
                                    orderHistory.storePhone = store.contactNumber!!

                                    orderItem.storeName = store.name!!
                                    orderItem.storePhone = store.contactNumber!!
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                orderHistory.itemList.add(orderItem)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    orderHistory.deliveryAddress = userAddressUtils.getUserAddressById(it.addressId!!)!!

                    val userInfo = userUtils.getUserById(orderHistory.deliveryAddress!!.userId!!)
                    orderHistory.orderBy = userInfo!!.phone

                    allOrderList.add(orderHistory)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            allOrderList.sortByDescending { orderHistory -> orderHistory.orderId  }


            response.result = allOrderList
            response.isSuccessful = true
            response.isResultAvailable = true

            return response
        } catch (e: Exception) {
            response.result = allOrderList
            response.isSuccessful = false
            response.isResultAvailable = false
        }

        return response
    }
}


