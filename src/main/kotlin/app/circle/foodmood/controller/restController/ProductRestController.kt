package app.circle.foodmood.controller.restController

import app.circle.foodmood.controller.commonUtils.ProductUtils
import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.ProductItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpHeaders


@RestController
@RequestMapping("product")
class ProductRestController(val productUtils: ProductUtils) {

}
