package app.circle.foodmood.repository

import app.circle.foodmood.model.database.ApplicationVersion
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationVersionRepository : JpaRepository<ApplicationVersion, Long> {


    fun getApplicationVersionByStatus(status: Int):ApplicationVersion?
}
