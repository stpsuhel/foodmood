package app.circle.foodmood.utils

import app.circle.foodmood.controller.commonUtils.GlobalUtils
import app.circle.foodmood.controller.commonUtils.OrderUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class ScheduleTask(val orderUtils: OrderUtils , val globalUtils: GlobalUtils) {

    @Scheduled(cron = "0 59 23 1/1 * ? *")
    fun cancelAllUntouchOrder(){

        val orderByDate = orderUtils.getOrderByDate(globalUtils.getCurrentDate()!!)
        orderByDate.forEach {
            it.orderStatus = OrderStatus.ORDER_CANCELED.value
        }

    }
}
