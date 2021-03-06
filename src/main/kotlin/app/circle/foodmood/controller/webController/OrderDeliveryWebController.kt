package app.circle.foodmood.controller.webController


import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.DeliveryManDataModel
import app.circle.foodmood.model.dataModel.OrderDashboard
import app.circle.foodmood.model.dataModel.OrderHistory
import app.circle.foodmood.model.dataModel.OrderItemDetails
import app.circle.foodmood.repository.DeliveryManRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.ProcessDataModel
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("order-delivery")
class OrderDeliveryWebController(val orderDeliveryUtils: OrderDeliveryUtils, val userUtils: UserUtils, val productUtils: ProductUtils,
                                 val processDataModel: ProcessDataModel, val homeUtils: HomeUtils, val orderUtils: OrderUtils,
                                 val userAddressUtils: UserAddressUtils, val globalUtils: GlobalUtils,
                                 val deliveryManRepository: DeliveryManRepository, val storeUtils: StoreUtils) {

    /**
     * Entering Database too many time
     */

    @RequestMapping("dashboard")
    fun getDeliveryManInformation(model: Model): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val deliveryManList = orderDeliveryUtils.getAllDeliveryMan()

        val deliveryManDataModelList = ArrayList<DeliveryManDataModel>()
        deliveryManList.forEach {deliveryMan ->

            val user = userUtils.getUserByIdAndPrimaryRole(deliveryMan.userId!!)

            user?.let {
                val deliveryManDataModel = processDataModel.processDeliveryManToDeliveryManDataModel(deliveryMan, user)
                deliveryManDataModelList.add(deliveryManDataModel)
            }
        }

        val allOrderList = ArrayList<OrderDashboard>()
        try {
            val storeList = storeUtils.getAllStore()

            val storeIdList = arrayListOf<Long>()
            storeList.forEach {
                storeIdList.add(it.id!!)
            }

            val orderProductList = homeUtils.getOrderListByStoreId(storeIdList)
            val orderIdList = arrayListOf<Long>()
            orderProductList.forEach {
                orderIdList.add(it.orderId!!)
            }

            val allOrder = orderUtils.getAllOrderOfTodaysDate(orderIdList)
            val orderDetailsList = orderUtils.getAllOrderProductByOrderList(orderIdList)

            allOrder.forEach {
                val orderDashboard = OrderDashboard()
                orderDashboard.orderId = it.id!!
                orderDashboard.orderStatus = it.orderStatus
                orderDashboard.orderDate = globalUtils.getTimeInString(it.orderTime!!)
                orderDashboard.deliveryManId = it.deliveryManId

                val deliveryManInfo = userUtils.getUserByIdAndPrimaryRole(it.deliveryManId)
                deliveryManInfo?.let {
                    orderDashboard.deliveryMan = it.name
                }

                for (orderProduct in orderDetailsList) {
                    if (orderProduct.orderId == it.id) {
                        val product = productUtils.getByProductId(orderProduct.productId!!)
                        orderDashboard.itemList.add(product.name)
                    }
                }

                val userAddress = userAddressUtils.getUserAddressById(it.addressId!!)!!
                orderDashboard.deliveryAddress = userAddress.addressLineOne
                orderDashboard.latitude = userAddress.locationLatitude
                orderDashboard.longitude = userAddress.locationLongitude

                val userInfo = userUtils.getUserById(userAddress.userId!!)
                orderDashboard.orderBy = userInfo!!.phone

                allOrderList.add(orderDashboard)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:./error"
        }

        model.addAttribute("deliveryManList", deliveryManDataModelList)
        model.addAttribute("allOrderList", allOrderList)
        model.addAttribute("ID_NOT_FOUND", ID_NOT_FOUND)

        return "company/deliveryManDashboard"
    }
}
