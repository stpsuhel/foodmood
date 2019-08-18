package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.dataModel.PermissionDataModel
import app.circle.foodmood.repository.AdministrationRepository
import app.circle.foodmood.repository.CompanyPermissionRepository
import app.circle.foodmood.repository.RoleRepository
import app.circle.foodmood.security.Role
import app.circle.foodmood.security.RoleName
import app.circle.foodmood.utils.Status
import app.circle.foodmood.utils.capitalizeWords
import org.springframework.stereotype.Service


@Service
class RoleUtils(val administrationRepository: AdministrationRepository,
                val companyPermissionRepository: CompanyPermissionRepository,
                val roleRepository: RoleRepository) {

    fun getCompanyRegistrationRoles(): List<RoleName> {
        val values = RoleName.values()
        return values.filter { roleName ->
            (!roleName.name.contains("ROLE_USER", true)).and(
                    (!roleName.name.contains("ROLE_ADMIN", true)).and(
                            (!roleName.name.contains("COMPANY_MANAGEMENT", true))
                    ))
        }
    }


    fun getPermissionRoleByName(name: String): RoleName {

        return RoleName.valueOf(name)

    }


    fun getRoleListByPermission(permissionList: ArrayList<String>): ArrayList<Role> {
        val roleList = ArrayList<RoleName>()
        permissionList.forEach {
            roleList.add(getPermissionRoleByName(it))
        }

        return roleRepository.getAllByNameInOrderByNameAsc(roleList)
    }


    fun getPermissionListFromRoleIdList(roleIdList: ArrayList<Long>): java.util.ArrayList<Role> {
        return roleRepository.getAllByIdIn(roleIdList)

    }

    fun getPermissionRoleListByCompany(companyId: Long): ArrayList<PermissionDataModel> {
        val permissionCompany = companyPermissionRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
        val permissionList = ArrayList<PermissionDataModel>()

        val permissionIdList = ArrayList<Long>();
        permissionCompany.forEach {
            permissionIdList.add(it.roleId!!)
        }


        val permissionListFromRoleIdList = getPermissionListFromRoleIdList(permissionIdList)
        permissionListFromRoleIdList.forEach {

            val permission = PermissionDataModel()
            permission.role_text = it.name.name
            permission.roleId = it.id
            permission.name = it.name.name.replace("_", " ").capitalizeWords()

            permissionList.add(permission)
        }
        return permissionList

    }





}
