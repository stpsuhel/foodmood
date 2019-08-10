package app.circle.foodmood.model

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class StudentRegistration(
        @NotNull
        @Size(min = 2, max = 100) val fullName: String,
        @NotNull val firstName: String,
        @NotNull val lastName: String,
        @NotNull val gradeId: Long,
        val email: String? = "") {

}
