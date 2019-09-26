package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.SourceImage
import app.circle.foodmood.repository.ImageRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ImageUtils(val imageRepository: ImageRepository) {

    fun saveSourceImage(sourceImage: SourceImage): SourceImage{
        return imageRepository.save(sourceImage)
    }

    fun getImageById(id: Long): SourceImage?{
        return imageRepository.getByIdAndStatus(id, Status.Active.value)
    }

    fun getImageBySourceIdAndSourceType(sourceId: Long, sourceType: Int): ArrayList<SourceImage>{
        return imageRepository.getAllBySourceIdAndSourceTypeAndStatus(sourceId, sourceType, Status.Active.value)
    }

    fun getImageBySourceId(sourceId: Long): SourceImage?{
        return imageRepository.getBySourceIdAndStatus(sourceId, Status.Active.value)
    }

    fun deleteImageBySourceIdAndSourceType(): Boolean{
        return true
    }

    fun existsBySourceId(sourceId: Long): Boolean{
        return imageRepository.existsBySourceIdAndStatus(sourceId, Status.Active.value)
    }
}
