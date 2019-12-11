package example.souscription.domain

import example.offres.domain.formule.Durée

data class FormuleChoisie(
    val intitulé: String,
    val prix: Prix,
    val durée: Durée
)