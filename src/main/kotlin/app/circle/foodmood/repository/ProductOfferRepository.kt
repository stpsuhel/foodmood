package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ProductOffer
import org.springframework.data.jpa.repository.JpaRepository

interface ProductOfferRepository: JpaRepository<ProductOffer, Long> {

    fun getByCompanyIdAndOfferIdAndStatus(companyId: Long, offerId: Long, status: Int): ProductOffer
}
