package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
class ProductItem : AuditModel() {

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
}
