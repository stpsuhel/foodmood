package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.database.Company
import app.circle.foodmood.repository.CompanyRepository
import org.springframework.web.bind.annotation.*


@RestController
class CompanyController(val companyRepository: CompanyRepository) {

    @PostMapping(value = ["createUpdateCompanyInfo"])
    fun createUpdateCompany(@RequestBody company: Company): Company {
        return companyRepository.save(company)
    }
}
