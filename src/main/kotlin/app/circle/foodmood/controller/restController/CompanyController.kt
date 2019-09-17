package app.circle.foodmood.controller.restController

import app.circle.foodmood.model.Response
import app.circle.foodmood.model.database.Company
import app.circle.foodmood.model.database.RestaurantContactInfo
import app.circle.foodmood.repository.CompanyRepository
import app.circle.foodmood.repository.RegistrationRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class CompanyController(val companyRepository: CompanyRepository , val registrationRepository: RegistrationRepository) {

    @PostMapping(value = ["createUpdateCompanyInfo"])
    fun createUpdateCompany(@RequestBody company: Company): Company {
        return companyRepository.save(company)
    }


    @PostMapping("save/restaurant-info")
    fun saveRestaurantContactInfo(@RequestBody restaurantInfo: RestaurantContactInfo): Response<RestaurantContactInfo> {
        val response = Response<RestaurantContactInfo>()
        val save = registrationRepository.save(restaurantInfo)

        response.isResultAvailable = true
        response.isSuccessful = true
        response.result = save
        return response

    }
}
