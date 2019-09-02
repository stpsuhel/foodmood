package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Category
import app.circle.foodmood.model.database.SourceImage
import app.circle.foodmood.repository.CategoryRepository
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
}
