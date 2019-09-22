package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ApplicationStatus
import app.circle.foodmood.model.database.ApplicationVersion
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationStatusRepository : JpaRepository<ApplicationStatus, Long> {


    fun getApplicationStatusByStatus(status: Int):ApplicationStatus?
    fun getFirstByStatus(status: Int):ApplicationStatus?


}
