package app.circle.foodmood.controller.webController

import app.circle.foodmood.controller.commonUtils.RoleUtils
import app.circle.foodmood.controller.commonUtils.StoreUtils
import app.circle.foodmood.controller.commonUtils.UserUtils
import app.circle.foodmood.model.dataModel.UserDataModel
import app.circle.foodmood.model.dataModel.UserDetails
import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.UserRepository
import app.circle.foodmood.security.User
import app.circle.foodmood.security.services.UserPrinciple
import app.circle.foodmood.utils.PrimaryRole
import app.circle.foodmood.utils.ProcessDataModel
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


@Controller
class AdministrationWebController(val administrationRepository: AdministrationRepository, val userRepository: UserRepository, val encoder: PasswordEncoder,
                                  val userUtils: UserUtils, val processDataModel: ProcessDataModel, val roleUtils: RoleUtils,
                                  val storeUtils: StoreUtils) {

    @RequestMapping("user-registration", method = [RequestMethod.GET])
    fun getUserRegistration(model: Model): String {
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val permission = roleUtils.getPermissionRoleListByCompany(companyId = userPrincipal.companyId)


        model.addAttribute("user", UserDataModel())
        model.addAttribute("permissionList", permission)
        return "administration/userRegistration"
    }


    @RequestMapping("user-information", method = [RequestMethod.GET])
    fun getUserInformation(model: Model): String {

        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        val allUser = userUtils.getUserList(userPrincipal.companyId)



        return "administration/companyUserInformation"
    }


    @RequestMapping("user-registration", method = [RequestMethod.POST])
    fun saveUser(
            @Validated @ModelAttribute userDataModel: UserDataModel, bindingResult: BindingResult, model: Model, redirectAttributes: RedirectAttributes): String {
        val userPrincipal = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (userDataModel.name!!.isNotEmpty() && userDataModel.phone.isNotEmpty() && userDataModel.userName!!.isNotEmpty() && userDataModel.password!!.isNotEmpty() && userDataModel.confirmPassword!!.isNotEmpty()) {
            if (userDataModel.password == userDataModel.confirmPassword) {

                if (userRepository.existsByUsername(userDataModel.userName)!!) {
                    return ""
                }

                if (userRepository.existsByEmail(userDataModel.email)!!) {
                    return ""
                }
                userDataModel.companyId = userPrincipal.companyId

                val user = User(userDataModel.name, userDataModel.userName, userDataModel.email, encoder.encode(userDataModel.password), userDataModel.companyId);

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
        userUtils.deleteCacheUserList(userPrincipal.companyId)
        return "redirect:./user-information"
    }

    @RequestMapping(value = ["my-profile"], method = [RequestMethod.GET])
    fun getUserProfile(model: Model): String {
        val user = UserDetails()

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val userInfo = administrationRepository.findByCompanyIdAndId(userPrinciple.companyId, userPrinciple.id)

        val userDetailsInfo = processDataModel.processUserItemToUserDetailsItem(user, userInfo!!)

        model.addAttribute("userInfo", userDetailsInfo)

        return "userProfile/myProfile"
    }

    @RequestMapping("user-details")
    fun getUserDetails(@RequestParam("id", required = false) id: String = "", model: Model): String {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (id.isNotEmpty()) {
            val userId = id.toLong()
            val companyPermission = roleUtils.getPermissionRoleListByCompany(userPrinciple.companyId)
            val userInfo = administrationRepository.findByCompanyIdAndId(userPrinciple.companyId, userId)

            val user = processDataModel.processUserToUserDataModel(userInfo!!)

            model.addAttribute("user", user)
            val roles = userInfo.roles


            var permisttionList = arrayListOf<String>()

            for (role in roles) {
                permisttionList.add(role.name.name)
            }

            user.permissionList = permisttionList
            model.addAttribute("userRoles", roles)
            model.addAttribute("companyPermissionList", companyPermission)

            model.addAttribute("userDetails", userInfo)
        } else {
            return "redirect:./error"
        }

        return "administration/userDetail"
    }

    @RequestMapping("store-information")
    fun storeInformation(model: Model): String {

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        val storeList = storeUtils.getAllCompanyStore(userPrinciple.companyId)

        model.addAttribute("storeList", storeList)

        return "administration/storeInformation"
    }

    @RequestMapping("add-store", method = [RequestMethod.GET])
    fun getAddUpdateStore(@RequestParam("id", required = false) id: String? = null, model: Model): String {

        if (id.isNullOrEmpty()){
            model.addAttribute("store", Store())
        }else{
            val storeId = id!!.toLong()
            val storeInfo = storeUtils.getStoreById(storeId)

            model.addAttribute("store", storeInfo)
        }

        return "administration/addStore"
    }

    @RequestMapping("add-store", method = [RequestMethod.POST])
    fun saveStore(@Validated @ModelAttribute("store") store: Store, @RequestParam("id", required = false) id: String? = null,
                  bindingResult: BindingResult, redirectAttributes: RedirectAttributes, model: Model): String {

        if(bindingResult.hasErrors()){
            return "administration/addStore"
        }

        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        storeUtils.saveStoreData(store)
        storeUtils.deleteAllStoreCompanyCache(companyId = userPrinciple.companyId)

        return "redirect:./store-information"
    }
}
