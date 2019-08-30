package app.circle.foodmood.utils

enum class Status(val value: Int) {
    Deleted(0),
    Active(1),
}


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


const val defaultCompany = 2L



const val appVersion = "f00dMo0d_aPP_=_="


enum class PaymentType(value: Int) {
    CASH_ON_DELIVERY(1)
}
