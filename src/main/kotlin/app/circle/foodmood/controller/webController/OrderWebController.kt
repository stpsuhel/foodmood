package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.dataModel.OrderHistory
import app.circle.foodmood.model.dataModel.OrderItemDetails
import app.circle.foodmood.repository.CouponRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("order")
class OrderWebController(val couponRepository: CouponRepository, val productUtils: ProductUtils, var orderUtils: OrderUtils, val storeUtils: StoreUtils, val userAddressUtils: UserAddressUtils, val globalUtils: GlobalUtils) {

    @RequestMapping("live")
    fun getSaveCoupon(model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val allOrderIdTodayForCurrentCompany = arrayListOf<Long>()
        val allStoreIdCurrentCompany = arrayListOf<Long>()

        var allOrderList = arrayListOf<OrderHistory>()
        val todaysOderList = orderUtils.getOrderByDate(globalUtils.getCurrentDate()!!)

        val allCompanyStore = storeUtils.getAllCompanyStore(userPrinciple.companyId)

        allCompanyStore.forEach {
            allStoreIdCurrentCompany.add(it.id!!)
        }
        todaysOderList.forEach {
            allOrderIdTodayForCurrentCompany.add(it.id!!)
        }

        val orderDetailsList = orderUtils.getAllOrderProductByOrderList(allOrderIdTodayForCurrentCompany)


        todaysOderList.forEach {
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


        model.addAttribute("orderHistoryList", allOrderList)

        return "order/liveOrder"
    }


}
