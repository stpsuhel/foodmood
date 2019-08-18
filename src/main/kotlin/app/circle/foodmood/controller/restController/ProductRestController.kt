package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("product")
class ProductRestController(val productUtils: ProductUtils) {

}
