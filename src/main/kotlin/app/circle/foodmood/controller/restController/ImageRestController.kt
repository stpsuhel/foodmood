package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ImageUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.SourceImage
import app.circle.foodmood.security.services.UserPrinciple
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("image")
class ImageRestController(val imageUtils: ImageUtils) {

    @PostMapping
    @RequestMapping("save-update-image")
    fun saveUpdateImage(@RequestBody sourceImage: SourceImage, @RequestParam("id", required = false) id: Long? = null): Response<SourceImage> {
        val response = Response<SourceImage>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        try {
            if (id == null) {
                sourceImage.companyId = userPrinciple.companyId
                val saveImage = imageUtils.saveSourceImage(sourceImage)
                response.result = saveImage
            } else {

                val imageInfo = imageUtils.getImageById(id)


                imageInfo?.let { imageInfo ->


                    imageInfo.imageURL = sourceImage.imageURL
                    imageInfo.sourceId = sourceImage.sourceId
                    imageInfo.sourceType = sourceImage.sourceType

                    val saveImage = imageUtils.saveSourceImage(imageInfo)
                    response.result = saveImage

                }
            }

            response.isResultAvailable = true
            response.isSuccessful = true

            imageUtils.deleteAllImage()
            imageUtils.deleteImageBySourceIdAndSourceType()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    @GetMapping
    @RequestMapping("get")
    fun getImageById(@RequestParam id: Long? = null): Response<SourceImage> {
        val response = Response<SourceImage>()
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        try {
            if (id != null) {
                val imageInfo = imageUtils.getImageById(id)

                imageInfo.let {
                    response.result = imageInfo
                    response.isResultAvailable = true
                    response.isSuccessful = true
                }
            } else {
                response.isSuccessful = false
                response.isResultAvailable = false
                response.message = arrayOf("Id not found")
            }
        } catch (e: Exception) {
            e.printStackTrace()

            response.isSuccessful = false
            response.isResultAvailable = false
        }

        return response
    }
}
