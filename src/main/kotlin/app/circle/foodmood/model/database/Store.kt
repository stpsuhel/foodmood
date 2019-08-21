package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
class Store : AuditModel() {

    @NotNull
    @Size(min = 3, max = 50, message = "Store name cannot be empty")
    var name: String? = null

    @NotNull
    @Size(min = 3, max = 20, message = "Contact number cannot be empty")
    var contactNumber: String? = null

    @NotNull
    @Size(min = 3, max = 50, message = "Address cannot be empty")
    var address: String? = null

    @NotNull
    @Size(min = 3, max = 50, message = "Area name cannot be empty")
    var area: String? = null

    @NotNull
    @Size(min = 3, message = "Give a tag line")
    var tagline: String = ""

    @NotNull
    @Size(min = 3, max = 50, message = "Enter Store Open time")
    var openTime: String? = ""

    @NotNull
    @Size(min = 3, max = 50, message = "Enter Store Closed time")
    var closedTime: String = ""

    var locationLatitude: Double = 0.0

    var locationLongitude: Double = 0.0
}
