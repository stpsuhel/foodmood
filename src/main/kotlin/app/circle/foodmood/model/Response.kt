package app.circle.foodmood.model



class Response<T>() {

    var isResultAvailable: Boolean = false
    var isSuccessful: Boolean = false
    var result: T? = null
    var message: Array<String>? = null
}
