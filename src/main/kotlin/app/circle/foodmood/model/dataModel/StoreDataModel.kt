package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.AuditModel
import app.circle.foodmood.model.database.ProductItem
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class StoreDataModel {
    var details: StoreDetails? = null
    var productList: List<ProductItem> = mutableListOf()
}


class StoreDetails : AuditModel() {
    var name: String? = null
    var contactNumber: String? = null

    var address: String? = null

    var area: String? = null


    var tagline: String = ""


    var openTime: String? = ""

    var closedTime: String = ""

    var locationLatitude: Double = 0.0

    var locationLongitude: Double = 0.0

    var imageUrl: String = ""
}
