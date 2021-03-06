package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(indexes = [Index(name = "order_product_data", columnList = "productId,orderId,storeId")])
class OrderProduct : AuditModel() {
    @NotNull
    var orderId: Long? = null

    @NotNull
    var productId: Long? = null

    @NotNull
    var storeId: Long? = null

    @NotNull
    var hasDiscount: Boolean = false

    var perProductDiscountPrice: Int? = null

    @NotNull
    var perProductPrice: Int? = null

    var quantity: Int = 0

    @NotNull
    var orderDate: Int? = null

}
