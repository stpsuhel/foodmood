package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import app.circle.foodmood.security.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Company() : AuditModel() {

    @NotNull
    var name: String? = null
    @NotNull
    var address: String? = ""

    @NotNull
    var contactInfo: String? = ""

    @NotNull
    var location: String? = ""

    @NotNull
    var phoneNumber: String? = ""

}



