package example.souscription.domain

import java.time.LocalDate

data class PeriodeDeValidité (
    val dateDebut: LocalDate,
    var dateFin: LocalDate
)
