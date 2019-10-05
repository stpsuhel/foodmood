package app.circle.foodmood.utils

enum class Status(val value: Int) {
    Deleted(0),
    Active(1),
}

const val FIREBASE_URL = "https://fcm.googleapis.com/fcm/send"
const val ID_NOT_FOUND: Long = -1L
const val ID_NOT_FOUND_INT: Int = -1
const val DISCOUNT_NOT_FOUND_INT: Int = -1
const val SIZE_EMPTY: Int = 0
const val REJECT_FOR_TEACHER_ID: String = "500"


const val STUDENT_PASSWORD_CONSTANT = "student-"


val feedTypeList = arrayOf("feed-item-secondary", "feed-item-danger", "feed-item-primary", "feed-item-success", "feed-item-warning")

const val MAX_HINT_LENGTH = 99

const val START_MONTH_DATE = "0101"
const val END_MONTH_DATE = "1231"

const val TOP_TRENDING = 10

const val defaultCompany = 2L


const val appVersion = "f00dMo0d_aPP_=_="


enum class PaymentType(val value: Int) {
    CASH_ON_DELIVERY(1)
}

enum class OrderStatus(val value: Int) {
    PENDING_FOR_APPROVAL(1),
    ACCEPT_BY_RESTAURANT(2),
    COOKING_ON_RESTAURANT(3),
    READY_TO_PICK_UP(4),
    FRAUD_ORDER(5),
    PICK_UP_BY_DELIVERY_MAN(6),
    START_DELIVERY(7),
    NEAR_DELIVERY_ADDRESS(8),
    DELIVERED_SUCCESSFULLY(9),
    ORDER_CANCELED(10),

}


enum class ImageSourceType(val value: Int) {
    PRODUCT_IMAGE(1),
    USER_PROFILE_IMAGE(2),
    COMPANY_PROFILE_IMAGE(3),
    CATEGORY_IMAGE(3),
}


enum class APP(val value: Int) {
    FOOD_MOOD(1),
    CITY_CENTER(2),
    DAILY_BAZER(3),
}


enum class DeliveryType(val value: Int) {
    FREE_DELIVERY(5),
    PAID_DELIVERY(6),
}

enum class DeliveryManStatus(val value: Int) {
    ON_DELIVERY(1),
    FREE_NOW(0),
}

class Constant {
    companion object {
        var IMAGE_NOT_AVAILABLE_URL = "http://foodmood.app/file-demo-0.0.1-SNAPSHOT/downloadFile/fm_not_available.png"
        var ORDER_NOTIFICATION_TOPIC = "foodmood_order_notification"
        const val FOODMOOD_EVENT_TOPIC = "foodmood_event"
        const val FOODMOOD_PROMOTION_NOTIFICATION = "foodmood_promotion_notification"



        const val FOODMOOD_PRIMARY_TOPIC = "food_mood_primary_topic"
        const val FOODMOOD_PROMOTION_TOPIC = "food_mood_promotion_topic"

        const val FOODMOOD_ORDER_NOTIFICATIOM = "Order Notification"
        const val DATABASE_CLEAR_EVENT = "database_clear_event"

    }
}

enum class NotificationType(val value: Int) {
    ImageNotification(1),
    SimpleTextNotification(0),
}
