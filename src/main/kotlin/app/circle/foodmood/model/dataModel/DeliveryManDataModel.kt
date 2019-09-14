package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotNull


class DeliveryManDataModel{

        var id: Long? = null
        var companyId: Long? = null
        var name: String = ""
        var deliveryStatus: Int = -1
        var latitude: Int = 0
        var longitude: Int = 0
}



