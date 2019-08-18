package app.circle.foodmood.model.dataModel

import app.circle.foodmood.model.database.ProductItem
import app.circle.foodmood.model.database.Store

class StoreDataModel {
    var details: Store? = null
    var productList: List<ProductItem> = mutableListOf()
}
