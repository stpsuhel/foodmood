package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotNull

class PromotionDataModel {

    @NotNull
    var title: String? = null

    @NotNull
    var text: String? = null

    @NotNull
    var imageUrl: String? = null
}
