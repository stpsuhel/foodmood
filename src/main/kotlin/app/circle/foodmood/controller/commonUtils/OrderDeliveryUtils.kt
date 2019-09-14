package app.circle.foodmood.controller.commonUtils

import app.circle.foodmood.model.database.DeliveryMan
import app.circle.foodmood.model.database.Order
import app.circle.foodmood.model.database.OrderProduct
import app.circle.foodmood.repository.DeliveryManRepository
import app.circle.foodmood.repository.OrderProductRepository
import app.circle.foodmood.repository.OrderRepository
import app.circle.foodmood.utils.Status
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class OrderDeliveryUtils(val deliveryManRepository: DeliveryManRepository) {

    fun getAllDeliveryManByCompanyId(companyId: Long): ArrayList<DeliveryMan>{
        return deliveryManRepository.getAllByCompanyIdAndStatus(companyId, Status.Active.value)
    }

}
