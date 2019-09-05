package app.circle.foodmood.model.database

import app.circle.foodmood.utils.ID_NOT_FOUND
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @NotEmpty
    @NotNull
    var token: String? = null


    val userId: Long? = ID_NOT_FOUND


}
