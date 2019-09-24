package app.circle.foodmood.repository

import app.circle.foodmood.model.database.Notification
import org.springframework.data.jpa.repository.JpaRepository


interface NotificationRepository : JpaRepository<Notification, Long> {

    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): Notification?
}
