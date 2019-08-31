package app.circle.foodmood.model.dataModel

import javax.validation.constraints.NotNull


class DashboardDataModel() {
        var countCustomer: Long = 0L
        var countOrder: Long = 0L
        var countProductItem: Long = 0L
        var totalEarning: Long = 0L
}
