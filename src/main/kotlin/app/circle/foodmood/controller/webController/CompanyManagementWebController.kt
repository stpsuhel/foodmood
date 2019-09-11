package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.*
import app.circle.foodmood.model.dataModel.CompanyDataModel
import app.circle.foodmood.model.dataModel.CompanyProductItemDataModel
import app.circle.foodmood.model.dataModel.ProductItemDataModel
import app.circle.foodmood.model.dataModel.UserDataModel
import app.circle.foodmood.model.database.*
import app.circle.foodmood.repository.CompanyPermissionRepository
import app.circle.foodmood.repository.CompanyRepository
import app.circle.foodmood.repository.UserRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.*
import app.circle.foodmood.utils.RoleConstant.Companion.ROLE_DELIVERY_MAN
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.lang.StringBuilder


@Controller
@RequestMapping("admin")
class CompanyManagementWebController(val companyRepository: CompanyRepository, val processDataModel: ProcessDataModel,
                                     val categoryUtils: CategoryUtils, val userRepository: UserRepository,
                                     val companyPermissionRepository: CompanyPermissionRepository,
                                     val roleUtils: RoleUtils, val encoder: PasswordEncoder, val imageUtils: ImageUtils,
                                     val productUtils: ProductUtils, val storeUtils: StoreUtils) {

    @RequestMapping("company-registration", method = [RequestMethod.GET])
    fun companyRegistration(model: Model): String {


        val values = roleUtils.getCompanyRegistrationRoles()


        model.addAttribute("permissionList", values)
        model.addAttribute("companyDataModel", CompanyDataModel())
        return "company/companyRegistration"
    }


    @RequestMapping("company-information", method = [RequestMethod.GET])
    fun companyInformation(model: Model): String {


        val allCompany = companyRepository.findAll()
        model.addAttribute("companyList", allCompany)


        return "company/companyInformation"
    }


    @RequestMapping("company-registration", method = [RequestMethod.POST])
    fun saveCompany(@Validated @ModelAttribute companyDataModel: CompanyDataModel, bindingResult: BindingResult, model: Model): String {

        if (companyDataModel.permissionList!!.size > SIZE_EMPTY) {
            val company = Company()
            company.name = companyDataModel.name
            company.phoneNumber = companyDataModel.phoneNumber
            company.address = companyDataModel.address
            company.location = companyDataModel.location
            company.contactInfo = companyDataModel.contactInfo
            val roleListByPermission = roleUtils.getRoleListByPermission(companyDataModel.permissionList)
            val savedCompany = companyRepository.save(company)

            val permissionList = ArrayList<CompanyPermission>()
            for (role in roleListByPermission) {
                val permission = CompanyPermission()
                permission.roleId = role.id
                permission.companyId = savedCompany.id;
                permissionList.add(permission)
            }


            companyPermissionRepository.saveAll(permissionList)

        } else {

        }

        return "redirect:./company-information"
    }


    /*
        @PreAuthorize("hasAnyRole('" + RoleConstant.Admin + "','" + RoleConstant.ADMIN_READ + "')")
    */
    @RequestMapping("company-details", method = [RequestMethod.GET])
    fun getCompanyDetails(@RequestParam("id", required = false) id: Long? = null, model: Model): String {

        if (id != null) {

            val company = companyRepository.getCompanyByIdAndStatus(id, Status.Active.value)
            val userList = userRepository.getAllByCompanyId(id)


            var roleIdList = ArrayList<Long>()
            val permissionList = companyPermissionRepository.getAllByCompanyIdAndStatus(company.id!!, Status.Active.value)
            permissionList.forEach {
                roleIdList.add(it.roleId!!)
            }

            val permissionListCompany = roleUtils.getPermissionListFromRoleIdList(roleIdList)

            model.addAttribute("company", company)
            model.addAttribute("accountList", userList)
            model.addAttribute("permissionList", permissionListCompany)

            return "company/companyDetails"

        } else {
            return "redirect:./error"
        }
    }


    @RequestMapping("create-admin-user", method = [RequestMethod.GET])
    fun createAdminUser(@RequestParam("id", required = false) id: Long? = null, model: Model): String {
        if (id != null) {
            val company = companyRepository.getCompanyByIdAndStatus(id, Status.Active.value)
            val permission = roleUtils.getPermissionRoleListByCompany(companyId = company.id!!)


            model.addAttribute("company", company)
            model.addAttribute("user", UserDataModel())
            model.addAttribute("permissionList", permission)
            model.addAttribute("primaryRole", PrimaryRole.values())

            return "company/companyUserRegistration"
        } else {
            return "redirect:./error"
        }
    }


    /*
        @PreAuthorize("hasAnyRole('" + RoleConstant.Admin + "','" + RoleConstant.ADMIN_WRITE + "')")
    */
    @RequestMapping("create-admin-user", method = [RequestMethod.POST])
    fun saveAdminUser(@Validated @ModelAttribute userDataModel: UserDataModel, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String {

        if (userDataModel.name!!.isNotEmpty() && userDataModel.phone!!.isNotEmpty() && userDataModel.userName!!.isNotEmpty() && userDataModel.password!!.isNotEmpty() && userDataModel.confirmPassword!!.isNotEmpty()) {
            if (userDataModel.password == userDataModel.confirmPassword) {

                if (userRepository.existsByUsername(userDataModel.userName)!!) {
                    return ""
                }

                if (userRepository.existsByEmail(userDataModel.email)!!) {
                    return ""
                }


                var user = User(userDataModel.name, userDataModel.userName, userDataModel.email, encoder.encode(userDataModel.password), userDataModel.companyId);

                val permission = roleUtils.getRoleListByPermission(userDataModel.permissionList)
                user.roles = HashSet(permission)
                user.primaryRole = PrimaryRole.valueOf(userDataModel.primaryRole!!)

                val savedUser = userRepository.save(user)


            } else {
                throw Exception("Password is not matched")
            }
        } else {
            throw  Exception("Mandatory Field is empty")
        }

        redirectAttributes.addAttribute("id", userDataModel.companyId)
        return "redirect:./company-details"
    }

    @RequestMapping("all-product-information")
    fun getAllProductInformation(model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        if (userPrinciple.primaryRole.name == PrimaryRole.ADMIN.name) {
            val allProductCompany = productUtils.getAllProductWithOutStatus()
            val productList = ArrayList<ProductItemDataModel>()

            allProductCompany.forEach {

                try {
                    val storeInfo = storeUtils.getStoreById(it.storeId!!)
                    val processData = processDataModel.processProductItemToProductItemDataModel(it, storeInfo)
                    productList.add(processData)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            model.addAttribute("productList", productList)
        } else {

            val data: List<ProductItem> = mutableListOf()
            model.addAttribute("productList", data)
        }

        return "product/productInformation"
    }

    @RequestMapping("all-category-information")
    fun getAllCategoryInformation(model: Model): String{

        val allCategory = categoryUtils.getAllCategoryList()
        model.addAttribute("allCategoryList", allCategory)

        return "product/categoryInformation"
    }

    @RequestMapping("add-category")
    fun getAddUpdateCategory(model: Model): String{
        model.addAttribute("category", Category())

        return "product/addUpdateCategory"
    }

    @RequestMapping(value=["add-category"], method = [RequestMethod.POST])
    fun getSaveUpdateCategory(@Validated @ModelAttribute("productCategory") category: Category, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String{

        categoryUtils.saveUpdateCategory(category)
        categoryUtils.deleteAllCategoryList ()

        return "redirect:./all-category-information"
    }

    @RequestMapping("update-product")
    fun getUpdateProduct(@RequestParam id: Long? = null, model: Model): String{
        if (id != null) {

            val productInfo = productUtils.getByProductId(id)
            val companyInfo = companyRepository.getCompanyByIdAndStatus(productInfo.companyId!!, Status.Active.value)
            val storeList = storeUtils.getAllCompanyStore(companyInfo.id!!)
            val categoryList = categoryUtils.getAllCategoryList()

            val imageList = imageUtils.getImageBySourceIdAndSourceType(productInfo.id!!, ImageSourceType.PRODUCT_IMAGE.value)

            val stringBuilder = StringBuilder()
            imageList.forEach{
                stringBuilder.append(it.imageURL).append(",")
            }

            val companyProductItemDataModel = processDataModel.processCompanyProductItemToCompanyProductItemDataModel(productInfo, stringBuilder.toString())

            model.addAttribute("product", companyProductItemDataModel)
            model.addAttribute("company", companyInfo)
            model.addAttribute("storeList", storeList)
            model.addAttribute("categoryList", categoryList)

            return "company/updateProduct"
        } else {
            return "redirect:./error"
        }
    }

    @RequestMapping(value = ["update-product"], method = [RequestMethod.POST])
    fun getSaveUpdateProduct(@RequestParam("id", required = false) id: Long? = null,
                             @Validated @ModelAttribute("product") product: CompanyProductItemDataModel, bindingResult: BindingResult,
                             model: Model, redirectAttributes: RedirectAttributes): String {


        if(product.storeId == null){
            bindingResult.rejectValue("storeId", "500", "Please Select a Store")
        }
        if(product.price == null || product.price == 0){
            bindingResult.rejectValue("price", "500", "Product price must be greater then Zero")
        }
        if(product.imageURL.isNullOrEmpty()){
            bindingResult.rejectValue("imageURL", "500", "give a image URL")
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("storeList", storeUtils.getAllCompanyStore(product.companyId!!))
            return "company/updateProduct"
        }

        product.discountPrice?.let {
            if(it > 0){
                product.isDiscount = true
            }else{
                product.discountPrice = 0
            }
        }

        val imageURLList = product.imageURL.split(",")

        var firstImage: String? = null
        if(imageURLList.isNotEmpty()) {
            firstImage = imageURLList[0]
        }

        val imageSourceList = imageUtils.getImageBySourceIdAndSourceType(id!!, ImageSourceType.PRODUCT_IMAGE.value)
        var primaryImageId: Long? = null

        val isNewImageURL = ArrayList<String>()
        imageURLList.forEach {

            if(it.isNotBlank() && it.isNotEmpty()) {
                isNewImageURL.add(it)

                if(imageSourceList.isNotEmpty()) {
                    for (image in imageSourceList) {

                        if(image.imageURL == it){
                            isNewImageURL.remove(it)
                            break
                        }
                    }
                }
            }
        }

        val isDeleteImageItem = ArrayList<SourceImage>()
        imageSourceList.forEach {

            println(it.imageURL)
            try {
                if (imageSourceList.isNotEmpty()) {
                    isDeleteImageItem.add(it)

                    if(imageURLList.isNotEmpty()){

                        for (image in imageURLList) {

                            if (image == it.imageURL) {
                                isDeleteImageItem.remove(it)
                                break
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        isDeleteImageItem.forEach {
            it.status = Status.Deleted.value
            imageUtils.saveSourceImage(it)
        }

        isNewImageURL.forEach {
            val imageItem = SourceImage()

            imageItem.imageURL = it
            imageItem.sourceType = ImageSourceType.PRODUCT_IMAGE.value
            imageItem.sourceId = product.id
            imageItem.companyId = product.companyId

            imageUtils.saveSourceImage(imageItem)
        }

        val allImageList = imageUtils.getImageBySourceIdAndSourceType(product.id, ImageSourceType.PRODUCT_IMAGE.value)

        for(imageItem in allImageList){

            if(firstImage != null && firstImage == imageItem.imageURL){
                primaryImageId = imageItem.id
                break
            }
        }

        val productItem = processDataModel.processCompanyProductItemDataModelToCompanyProductItem(product, primaryImageId!!)

        productUtils.saveUpdateProduct(productItem)
        productUtils.deleteAllProductByCompanyCache(product.companyId)
        productUtils.deleteAllProductCache()
        imageUtils.deleteImageBySourceIdAndSourceType()

        return "redirect:./all-product-information"
    }

    @RequestMapping("delivery-man-registration")
    fun getDeliveryMan(@RequestParam("id", required = false) id: Long? = null, model: Model): String{

        if(id == null) {
            model.addAttribute("user", UserDataModel())
        }else{
            val deliveryManInfo = userRepository.findById(id)

            if(deliveryManInfo.isPresent){

                val deliveryUserDataModel = processDataModel.processUserToUserDataModel(deliveryManInfo.get())
                model.addAttribute("user", deliveryUserDataModel)
            }
        }
        return "company/companyDeliveryManRegistration"
    }

    @RequestMapping("delivery-man-registration", method = [RequestMethod.POST])
    fun saveDeliveryMan(@Validated @ModelAttribute userDataModel: UserDataModel, @RequestParam("id", required = false) id: Long? = null, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String {

        if(id == null) {
            if (userDataModel.name!!.isNotEmpty() && userDataModel.phone.isNotEmpty() && userDataModel.userName!!.isNotEmpty() && userDataModel.password!!.isNotEmpty() && userDataModel.confirmPassword!!.isNotEmpty()) {
                if (userDataModel.password == userDataModel.confirmPassword) {

                    if (userRepository.existsByUsername(userDataModel.userName)!!) {
                        return ""
                    }

                    if (userRepository.existsByEmail(userDataModel.email)!!) {
                        return ""
                    }
                    val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

                    val user = User(userDataModel.name, userDataModel.userName, userDataModel.email, encoder.encode(userDataModel.password), userPrinciple.companyId);

                    val permission = roleUtils.getRoleListByPermission(arrayListOf(ROLE_DELIVERY_MAN))
                    user.roles = HashSet(permission)

                    user.primaryRole = PrimaryRole.CompanyDeliveryMan

                    userRepository.save(user)

                } else {
                    throw Exception("Password is not matched")
                }
            } else {
                throw  Exception("Mandatory Field is empty")
            }
        }else {
            if (userDataModel.name!!.isNotEmpty() && userDataModel.phone.isNotEmpty() && userDataModel.userName!!.isNotEmpty()) {

                val user = userRepository.findById(id).get()

                if (user.username != userDataModel.userName && userRepository.existsByUsername(userDataModel.userName)!!) {
                    return ""
                }

                if (user.email != userDataModel.email && userRepository.existsByEmail(userDataModel.email)!!) {
                    return ""
                }

                user.phone = userDataModel.phone
                user.name = userDataModel.name
                user.username = userDataModel.userName
                user.email = userDataModel.email

                userRepository.save(user)
            }else {
                throw  Exception("Mandatory Field is empty")
            }
        }

        return "redirect:./delivery-man-information"
    }

    @RequestMapping("delivery-man-information")
    fun getDeliveryManInformation(model: Model): String{
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val deliveryManList = userRepository.getAllByCompanyIdAndPrimaryRole(userPrinciple.companyId, PrimaryRole.CompanyDeliveryMan)

        model.addAttribute("deliveryManList", deliveryManList)

        return "company/companyDeliveryManInformation"
    }
}
