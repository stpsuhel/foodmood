package app.circle.foodmood.model.dataModel


data class OrderNotification(
        var data: Data = Data(),
        var to: String = ""
)

data class Data(
        var image: String = "",
        var message: String = "",
        var meta: Meta = Meta(),
        var title: String = "FoodMood - Order Notification"
)


data class Meta(
        var info: String = "",
        var type: String = ""
)
