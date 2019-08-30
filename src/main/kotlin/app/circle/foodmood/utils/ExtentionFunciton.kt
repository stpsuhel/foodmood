package app.circle.foodmood.utils

import org.jasypt.util.text.BasicTextEncryptor

fun String.capitalizeWords(): String = split(" ").map { it.toLowerCase().capitalize() }.joinToString(" ")

fun String.tokenEqualText(string: String): Boolean {
    val strongEncryptor = BasicTextEncryptor()
    strongEncryptor.setPassword(appVersion)
    val decrypt = strongEncryptor.decrypt(string)
    return decrypt == this

}
