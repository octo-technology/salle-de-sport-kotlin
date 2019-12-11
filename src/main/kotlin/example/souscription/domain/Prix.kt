package example.souscription.domain

import kotlin.math.round

// TODO TU
data class Prix(var montant: Double) {
    fun appliquerRéduction(réduction: Réduction) {
        montant = (montant * (1f - réduction.ratio)).round(2)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}
