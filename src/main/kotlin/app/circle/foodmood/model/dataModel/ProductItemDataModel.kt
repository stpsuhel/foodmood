package app.circle.foodmood.model.dataModel

import app.circle.foodmood.utils.ID_NOT_FOUND
import app.circle.foodmood.utils.ID_NOT_FOUND_INT
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class ProductItemDataModel {

    var name: String = ""
    var description: String = ""
    var price: Int? = null
    var id: Long = ID_NOT_FOUND
    var companyId: Long = ID_NOT_FOUND
    var categoryId: Long = ID_NOT_FOUND
    var storeId: Long = ID_NOT_FOUND
    var isDiscount: Boolean = false
    var discountPrice: Int? = null
    var isFreeDelivery: Boolean = false
    var storeName: String = ""
    var status: Int = ID_NOT_FOUND_INT
    var categoryList: ArrayList<Long> = arrayListOf()
    var imageList: ArrayList<String> = arrayListOf()
    var primaryImageUrl: String = ""

}


class CompanyProductItemDataModel {
    @NotNull
    @Size(min = 3, max = 50, message = "Product Name cannot be Empty or less then three")
    var name: String = ""

    @NotNull
    @Size(min = 3, max = 100, message = "Product Description cannot be Empty or less then three")
    var description: String = ""

    @NotNull(message = "")
    var price: Int? = null

    @NotNull
    var storeId: Long? = null

    var discountPrice: Int? = 0

    var isDiscount: Boolean = false

    var freeDelivery: Boolean = false

    @NotNull
    var categoryId: Long? = null
    var id: Long? = null
    var companyId: Long? = null

    var imageURL: String = ""

}
