package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
@Table(indexes = [Index(name = "source_image_data", columnList = "sourceId,sourceType")])
class SourceImage : AuditModel() {

    @NotNull
    var sourceId: Long? = null

    @NotNull
    var sourceType: Int? = null

    @NotNull
    @NotEmpty
    var imageURL: String? = null
}
