package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Offer
import org.springframework.data.jpa.repository.JpaRepository

interface OfferRepository: JpaRepository<Offer, Long> {

    fun getAllByCompanyIdAndStatus(companyId: Long, status: Int): ArrayList<Offer>

    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): Offer
}
