package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotNull


class DashboardDataModel() {
        var countCustomer: Int = 0
        var countAcceptedOrder: Int = 0
        var countProductItem: Int = 0
        var countRejectedOrder: Int = 0
}
