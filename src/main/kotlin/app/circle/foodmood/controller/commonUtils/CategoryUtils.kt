package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.ProductCategory
import app.circle.foodmood.repository.CategoryRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CategoryUtils(val categoryRepository: CategoryRepository) {

    fun saveUpdateCategory(productCategory: ProductCategory): ProductCategory{
        return categoryRepository.save(productCategory)
    }

    @Cacheable("all-category")
    fun getAllCategoryList(): ArrayList<ProductCategory>{
        return categoryRepository.getAllByStatus(Status.Active.value)
    }

    @CacheEvict("all-category")
    fun deleteAllCategoryList(): Boolean{
        return true
    }
}
