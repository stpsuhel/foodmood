package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.GlobalUtils
import app.circle.foodmood.controller.commonUtils.OfferUtils
import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.model.dataModel.OfferDataModel
import app.circle.foodmood.model.database.ProductOffer
import app.circle.foodmood.repository.OfferRepository
import app.circle.foodmood.repository.ProductOfferRepository
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.ProcessDataModel
import app.circle.foodmood.utils.Status
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class OfferWebController(val storeUtils: StoreUtils, val productUtils: ProductUtils, val processDataModel: ProcessDataModel,
                         val offerRepository: OfferRepository, val productOfferRepository: ProductOfferRepository,
                         val globalUtils: GlobalUtils, val offerUtils: OfferUtils) {

    @RequestMapping("add-update-offer")
    fun getSaveOffer(@RequestParam("id", required = false)id: Long? = null, model: Model): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val productList = productUtils.getAllProductByCompanyId(userPrinciple.companyId)

        if(id == null) {

            model.addAttribute("offer", OfferDataModel())

        }else{
            val offerInfo = offerRepository.getByCompanyIdAndIdAndStatus(userPrinciple.companyId, id, Status.Active.value)

            val offerDataModel = processDataModel.processProductOfferToProductOfferDataModel(offerInfo)


            offerDataModel.endDate = globalUtils.getDateForInputField(offerInfo.endDate!!)
            offerDataModel.startDate = globalUtils.getDateForInputField(offerInfo.startDate!!)

            model.addAttribute("offer", offerDataModel)
        }

        model.addAttribute("productList", productList)

        return "product/addUpdateOffer"
    }

    @RequestMapping("add-update-offer", method = [RequestMethod.POST])
    fun addUpdateOffer(@Validated @ModelAttribute offerDataModel: OfferDataModel, @RequestParam("id", required = false) id: Long? = null, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (offerDataModel.productId == null){
            bindingResult.rejectValue("productId", "500", "Select a Product")
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("storeList", productUtils.getAllProductByCompanyId(userPrinciple.companyId))
            return "product/addUpdateOffer"
        }



        if(id == null) {
            val offer = processDataModel.processProductOfferDataModelToProductOffer(offerDataModel)
            offer.createdBy = userPrinciple.id
            offer.companyId = userPrinciple.companyId

            offerRepository.save(offer)

        }else{
            val offerInfo = offerRepository.getByCompanyIdAndIdAndStatus(userPrinciple.companyId, id, Status.Active.value)

            offerInfo.offerTitle = offerDataModel.offerTitle
            offerInfo.offerDescription = offerDataModel.offerDescription
            offerInfo.productId = offerDataModel.productId
            offerInfo.startDate = globalUtils.getDateInInteger(offerDataModel.startDate!!)
            offerInfo.endDate = globalUtils.getDateInInteger(offerDataModel.endDate!!)
            offerInfo.createdBy = userPrinciple.id
            offerInfo.offerPrice = offerDataModel.offerPrice

            offerRepository.save(offerInfo)

        }

        return "redirect:./offer-information"
    }

    @RequestMapping("offer-information")
    fun getOfferInformation(model: Model): String{

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val offerList = offerUtils.getAllOfferByCompanyId(userPrinciple.companyId)

        val offerDataModelList = ArrayList<OfferDataModel>()
        offerList.forEach {

            offerDataModelList.add(processDataModel.processProductOfferToProductOfferDataModel(it))

        }

        model.addAttribute("offerList", offerDataModelList)

        return "product/offerInformation"
    }
}
