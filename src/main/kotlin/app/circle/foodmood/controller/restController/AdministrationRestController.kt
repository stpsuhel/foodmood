package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.*
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.OrderStatus
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.ProcessDataModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@RestController("previous-order")
class AdministrationRestController(val orderUtils: OrderUtils, val globalUtils: GlobalUtils, val storeUtils: StoreUtils,
                                   val productUtils: ProductUtils, val userAddressUtils: UserAddressUtils) {

    @GetMapping("order-details")
    fun getPreviousOrder(@RequestParam("fromMonth", required = false) fromMonth: String? = null): Response<ArrayList<OrderHistory>> {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val response = Response<ArrayList<OrderHistory>>()

        val allOrderIdTodayForCurrentCompany = arrayListOf<Long>()
        val allStoreIdCurrentCompany = arrayListOf<Long>()



        val allOrderList = arrayListOf<OrderHistory>()
        var previousDate = -1
        var nextDate = -1

        if(fromMonth != null && fromMonth.isNotEmpty()) {
            if (fromMonth.takeLast(1) == "0") {

                previousDate = globalUtils.getMonthStartDate(fromMonth.take(2).toInt())!!
                nextDate = globalUtils.getCurrentDate()!!
            }else{

                previousDate = globalUtils.getMonthStartDate(fromMonth.take(2).toInt())!!
                nextDate = globalUtils.getMonthEndDate(fromMonth.take(2).toInt())!!
            }
        }else{

            previousDate = globalUtils.getCurrentMonthDate()!!
            nextDate = globalUtils.getCurrentDate()!!
        }
        val previousOderList = orderUtils.getAllOrderByPreviousDateToToday(previousDate, nextDate)

        val allCompanyStore = storeUtils.getAllCompanyStore(userPrinciple.companyId)

        allCompanyStore.forEach {
            allStoreIdCurrentCompany.add(it.id!!)
        }
        previousOderList.forEach {
            allOrderIdTodayForCurrentCompany.add(it.id!!)
        }

        val orderDetailsList = orderUtils.getAllOrderProductByOrderList(allOrderIdTodayForCurrentCompany)


        try {

            previousOderList.forEach {
                val orderHistory = OrderHistory()
                orderHistory.orderId = it.id!!
                orderHistory.hasDiscount = it.totalDiscountPrice!! < it.totalPrice!!
                orderHistory.orderDiscountPrice = it.totalDiscountPrice!!
                orderHistory.orderOriginalPrice = it.totalPrice!!
                orderHistory.orderStatus = it.orderStatus!!
                orderHistory.orderDate = it.orderDate!!
                for (orderProduct in orderDetailsList) {
                    if (orderProduct.orderId == it.id && allStoreIdCurrentCompany.contains(orderProduct.storeId)) {
                        val orderItem = OrderItemDetails()
                        orderItem.id = orderProduct.productId!!
                        orderItem.price = orderProduct.perProductPrice!!
                        orderItem.priceDiscount = orderProduct.perProductDiscountPrice!!
                        orderItem.hasDiscount = orderProduct.hasDiscount!!
                        orderItem.quantity = orderProduct.quantity!!
                        val product = productUtils.getByProductId(orderProduct.productId!!)
                        orderItem.name = product.name
                        orderHistory.itemList.add(orderItem)

                    }
                }

                orderHistory.deliveryAddress = userAddressUtils.getUserAddressById(it.addressId!!)!!

                if (orderHistory.itemList.isNotEmpty())
                    allOrderList.add(orderHistory)
            }
            response.isSuccessful = true
            response.isResultAvailable = true
            response.result = allOrderList

        }catch (e: Exception){
            response.isResultAvailable = false
            response.isSuccessful = false
            response.message = arrayOf("Error")
        }

        return response
    }
}
