package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Category
import app.circle.foodmood.model.database.ProductCategory
import app.circle.foodmood.repository.CategoryRepository
import app.circle.foodmood.repository.ProductCategoryRepository
import app.circle.foodmood.utils.Status
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CategoryUtils(val categoryRepository: CategoryRepository , val productCategoryRepository: ProductCategoryRepository) {

    fun saveUpdateCategory(category: Category): Category{
        return categoryRepository.save(category)
    }

    @Cacheable("all-category")
    fun getAllCategoryList(): ArrayList<Category>{
        return categoryRepository.getAllByStatusOrderByNameAsc(Status.Active.value)
    }

    @CacheEvict("all-category")
    fun deleteAllCategoryList(): Boolean{
        return true
    }

    fun getCategoryById(categoryId: Long): Category{
        return categoryRepository.getByIdAndStatus(categoryId, Status.Active.value)
    }
    fun getCategoryListByProductId(productId: Long): ArrayList<ProductCategory> {
        return productCategoryRepository.getAllByProductIdAndStatus(productId, Status.Active.value)
    }
}
