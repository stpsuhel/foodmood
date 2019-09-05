package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(indexes = [Index(name = "user_address_data", columnList = "userId")])
class UserAddress : AuditModel() {

    @NotNull
    var userId: Long? = null

    @NotNull
    var description: String = ""

    var addressLineOne: String? = null

    var addressLineTwo: String? = null

    var locationLatitude: Double = 0.0

    var locationLongitude: Double = 0.0
}
