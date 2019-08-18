package app.circle.foodmood.model.dataModel

import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.ID_NOT_FOUND_INT

class ProductItemDataModel {

    var name: String = ""
    var description: String = ""
    var price: Int? = null
    var id: Long = ID_NOT_FOUND
    var companyId: Long = ID_NOT_FOUND
    var storeId: Long = ID_NOT_FOUND
    var isDiscount: Boolean = false
    var discountPrice: Int? = null
    var isFreeDelivery: Boolean = false
    var storeName: String = ""
    var status: Int = ID_NOT_FOUND_INT
}
