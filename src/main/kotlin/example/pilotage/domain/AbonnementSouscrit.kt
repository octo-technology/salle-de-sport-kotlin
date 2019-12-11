package example.pilotage.domain

import java.time.LocalDate

data class AbonnementSouscrit(
    val dateDebut: LocalDate,
    val dateFin: LocalDate,
    val prixMensuel: Double
)