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

    fun getImageById(companyId: Long, id: Long): SourceImage{
        return imageRepository.getByCompanyIdAndIdAndStatus(companyId, id, Status.Active.value)
    }

    @Cacheable("all-image-by-source-type")
    fun getImageBySourceIdAndSourceType(sourceId: Long, sourceType: Int): ArrayList<SourceImage>{
        return imageRepository.getAllBySourceIdAndSourceTypeAndStatus(sourceId, sourceType, Status.Active.value)
    }

    @CacheEvict("all-image-by-source-type")
    fun deleteImageBySourceIdAndSourceType(): Boolean{
        return true
    }
}
