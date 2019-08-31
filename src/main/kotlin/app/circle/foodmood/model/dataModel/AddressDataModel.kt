package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class AddressDataModel {
    var description: String = ""

    @NotNull
    @NotEmpty
    var addressLineOne: String? = null


    @NotEmpty
    @NotNull
    var addressLineTwo: String? = null

    @NotNull
    var locationLatitude: Double? = null

    @NotNull
    var locationLongitude: Double? = null

}
