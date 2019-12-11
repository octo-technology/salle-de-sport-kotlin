package example.souscription.commands.programmerunemailrecapitulatif

import java.time.LocalDate

data class ProgrammerUnEmailRecapitulatifCommand (
    val intituléFormule: String,
    val prixFinal: String,
    val duréeFormule: String,
    val email: String,
    val isEtudiant: Boolean,
    val dateDébut: LocalDate,
    val dateFin: LocalDate
)
