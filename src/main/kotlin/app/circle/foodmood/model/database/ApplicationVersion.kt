package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import app.circle.foodmood.model.AuditModelWithOutCompanyId
import javax.persistence.Entity
import javax.validation.constraints.NotNull


@Entity
class ApplicationVersion() : AuditModel() {

    @NotNull
    var versionName: String? = null

    @NotNull
    var versionCode: Int = -1

    @NotNull
    var date: Int? = null

    var isForceUpdate = false

    var updateMessage = ""
}
