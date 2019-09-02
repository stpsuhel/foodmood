package app.circle.foodmood.model.dataModel

import lombok.Lombok
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class UpdateTokenDataModel {

    @NotNull
    @NotEmpty
    var token: String? = null
}
