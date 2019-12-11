package example.pilotage.commands

import java.time.LocalDate

data class StoreAbonnementSouscritCommand(
    val dateDebut: LocalDate,
    val dateFin: LocalDate,
    val prixMensuel: Double
)