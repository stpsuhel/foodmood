package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository: JpaRepository<Coupon, Long> {
    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): Coupon
}
