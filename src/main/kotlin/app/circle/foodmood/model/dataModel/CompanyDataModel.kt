package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotNull


class CompanyDataModel(
        @NotNull
        val name: String = "",

        @NotNull
        val address: String = "",

        @NotNull
        val contactInfo: String = "",

        @NotNull
        val location: String = "",

        @NotNull
        var phoneNumber: String = "",

        var permissionList: ArrayList<String> = arrayListOf()
)



