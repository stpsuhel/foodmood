package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.dataModel.NotificationDataModel
import app.circle.foodmood.model.database.Notification
import app.circle.foodmood.repository.NotificationRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.NotificationType
import app.circle.foodmood.utils.Status
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class CSRWebController(val storeUtils: StoreUtils, val notificationRepository: NotificationRepository) {

    @RequestMapping("notification-information")
    fun getNotificationInformation(model: Model): String{
        return "crm/notificationInformation"
    }

    @RequestMapping("add-notification")
    fun getAddUpdateNotification(@RequestParam("id", required = false) id: Long? = null, model: Model): String{
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (id == null){
            model.addAttribute("notification", NotificationDataModel())
        }else{
            val notificationInfo = notificationRepository.getByCompanyIdAndIdAndStatus(userPrinciple.companyId, id, Status.Active.value)

            val notificationDataModel = NotificationDataModel()
            notificationDataModel.id = notificationInfo?.id
            notificationDataModel.companyId = notificationInfo?.companyId
            notificationDataModel.description = notificationInfo?.description
            notificationDataModel.imageURL = notificationInfo?.imageURL!!
            notificationDataModel.notificationType = notificationInfo.notificationType
            notificationDataModel.sourceId = notificationInfo.sourceId
            notificationDataModel.title = notificationInfo.title

            model.addAttribute("notification", notificationInfo)
        }

        val storeList = storeUtils.getAllCompanyStore(userPrinciple.companyId)
        model.addAttribute("storeList", storeList)
        model.addAttribute("type", NotificationType.values())

        return "crm/addNotification"
    }

    @RequestMapping("add-notification", method = [RequestMethod.POST])
    fun saveUpdateNotification(@Validated @ModelAttribute notificationDataModel: NotificationDataModel, bindingResult: BindingResult,
                               @RequestParam("id", required = false) id: Long? = null, model: Model, redirectAttributes: RedirectAttributes): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        if(bindingResult.hasErrors()){
            val storeList = storeUtils.getAllCompanyStore(userPrinciple.companyId)
            model.addAttribute("storeList", storeList)
            model.addAttribute("type", NotificationType.values())
            return "crm/addNotification"
        }

        if (id == null){
            val notification = Notification()

            notification.description = notificationDataModel.description
            notification.imageURL = notificationDataModel.imageURL
            notification.notificationType = notificationDataModel.notificationType!!
            notification.sourceId = notificationDataModel.sourceId
            notification.title = notificationDataModel.title
            notification.sendTo = ""
            notification.toType = -1
            notification.companyId = userPrinciple.companyId

            notificationRepository.save(notification)
        }else{
            val notification = notificationRepository.getByCompanyIdAndIdAndStatus(userPrinciple.companyId, id, Status.Active.value)
            notification?.let {
                it.description = notificationDataModel.description
                it.imageURL = notificationDataModel.imageURL
                it.notificationType = notificationDataModel.notificationType!!
                it.sourceId = notificationDataModel.sourceId
                it.title = notificationDataModel.title
                it.sendTo = ""
                it.toType = -1
            }

            notificationRepository.save(notification!!)
        }

        return "redirect:./notification-information"
    }
}
