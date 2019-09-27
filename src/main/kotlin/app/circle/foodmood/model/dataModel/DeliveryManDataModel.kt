package app.circle.foodmood.model.dataModel




class DeliveryManDataModel{

        var id: Long? = null
        var companyId: Long? = null
        var name: String = ""
        var deliveryStatus: Int = -1
        var latitude: Int = 0
        var longitude: Int = 0
}

class DeliveryManDetails{
        var id: Long? = null
        var companyId: Long? = null
        var name: String = ""
        var email: String = ""
        var phone: String = ""
        var userName: String = ""
        var imageURL: String = ""
        var deliveryStatus: Int = -1
        var latitude: Int = 0
        var longitude: Int = 0
        var deliveryId: Long? = null
        var addressLineOne: String? = null
        var addressLineTwo: String? = null
}



