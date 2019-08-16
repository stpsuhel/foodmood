package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.Store
import app.circle.foodmood.repository.StoreRepository
import app.circle.foodmood.security.services.UserPrinciple
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class StoreUtils(val storeRepository: StoreRepository) {

    fun saveStoreData(store: Store) {
        val userPrinciple = SecurityContextHolder.getContext().authentication.principal as UserPrinciple

        if (store.name!!.isNotEmpty() && store.contactNumber!!.isNotEmpty() && store.address!!.isNotEmpty()){
            store.companyId = userPrinciple.companyId

            storeRepository.save(store)
        }
        else{
            throw  Exception("Mandatory Field is empty")
        }

    }
}
