package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Offer
import app.circle.foodmood.repository.OfferRepository
import app.circle.foodmood.utils.Status
import org.springframework.stereotype.Service


@Service
class OfferUtils(val offerRepository: OfferRepository) {

    fun getAllOfferByCompanyId(companyId: Long): ArrayList<Offer>{
        return offerRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }
}
