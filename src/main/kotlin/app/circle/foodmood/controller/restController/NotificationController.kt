package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.NotificationUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.dataModel.PromotionDataModel
import app.circle.foodmood.utils.Constant.Companion.FOODMOOD_PROMOTION_NOTIFICATION
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("notification")
class NotificationController(val notificationUtils: NotificationUtils) {

    @PostMapping("promotional-message")
    fun sendPromotionalNotification(@Validated @RequestBody notificationBody: PromotionDataModel): ResponseEntity<String> {

        println(notificationBody)
        return notificationUtils.largeImageNotification(FOODMOOD_PROMOTION_NOTIFICATION, notificationBody.title!!, notificationBody.text!!, notificationBody.imageUrl!!)

    }
}
