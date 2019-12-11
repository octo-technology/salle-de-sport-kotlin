package example.souscription.domain

import example._sharedkernel.eventbus.Event
import java.time.LocalDate

data class AbonnementSouscrit(
    val id: String,
    val recipientEmailAddress: String,
    val intituléFormule: String,
    val prixFinal: String,
    val duréeFormule: String,
    val isEtudiant: Boolean,
    val dateDebut: LocalDate,
    val dateFin: LocalDate
) : Event
