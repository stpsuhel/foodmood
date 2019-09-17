package app.circle.foodmood.model.database

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
class RestaurantContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotEmpty
    @NotNull
    var ownerName: String? = null

    @NotEmpty
    @NotNull
    var restaurantName: String? = null

    @NotEmpty
    @NotNull
    var contactNumber: String? = null


}
