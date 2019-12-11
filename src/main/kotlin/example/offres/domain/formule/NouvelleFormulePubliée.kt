package example.offres.domain.formule

import example._sharedkernel.eventbus.Event

data class NouvelleFormulePubliée (
    val id: String,
    val initulé: String,
    val prixDeBase: String,
    val durée: String
) : Event

