package app.circle.foodmood.repository

import app.circle.foodmood.model.database.SourceImage
import org.springframework.data.jpa.repository.JpaRepository


interface ImageRepository : JpaRepository<SourceImage, Long> {

    fun getByCompanyIdAndIdAndStatus(companyId: Long, id: Long, status: Int): SourceImage
}
