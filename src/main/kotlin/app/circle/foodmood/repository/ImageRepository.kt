package app.circle.foodmood.repository

import app.circle.foodmood.model.database.SourceImage
import org.springframework.data.jpa.repository.JpaRepository


interface ImageRepository : JpaRepository<SourceImage, Long> {

    fun getByIdAndStatus( id: Long, status: Int): SourceImage?

    fun getAllBySourceIdAndSourceTypeAndStatus(sourceId: Long, sourceType: Int, status: Int): ArrayList<SourceImage>

    fun getBySourceIdAndStatus(sourceId: Long, status: Int): SourceImage?

    fun existsBySourceIdAndStatus(sourceId: Long, status: Int): Boolean

    fun getAllByStatus(status: Int): ArrayList<SourceImage>
}
