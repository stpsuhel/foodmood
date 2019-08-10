package app.circle.foodmood.model.database

import app.circle.foodmood.model.AuditModel
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint


@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["companyId", "roleId"])])
class CompanyPermission : AuditModel() {
    var roleId: Long? = null
}
