package example.offres.domain.formule

import example._sharedkernel.eventbus.Event

data class PrixDeBaseAChangé (
    val id: String,
    val nouveauPrixDeBase: String
) : Event