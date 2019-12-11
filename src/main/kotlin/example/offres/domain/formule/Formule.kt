package example.offres.domain.formule

import example._sharedkernel.eventbus.Event
import example._sharedkernel.uuid.RandomUUIDGenerator
import example.offres.domain.formule.Formule.Errors.DuréeInvalide
import example.offres.domain.formule.Formule.Errors.PrixInvalide

class Formule(
    val id: FormuleId,
    private val intitulé: String,
    private val durée: Durée,
    private var prixDeBase: PrixDeBase
) {
    private val domainEvents = mutableListOf<Event>()

    companion object {
        fun nouvelleFormule(intitulé: String, durée: String, prixDeBase: String) : Formule {
            val formule = Formule(
                intitulé,
                durée,
                prixDeBase
            )
            formule.domainEvents.add(
                NouvelleFormulePubliée(
                    formule.id.id,
                    formule.intitulé,
                    formule.prixDeBase.montant.toString(),
                    formule.durée.name
                )
            )
            return formule
        }
    }

    constructor(intitulé: String, durée: String, prixDeBase: String) : this(
        FormuleId(RandomUUIDGenerator.generateId()),
        intitulé,
        when (durée) {
            "MENSUELLE" -> Durée.MENSUELLE
            "ANNUELLE" -> Durée.ANNUELLE
            else -> throw DuréeInvalide(durée)
        },
        try {
            PrixDeBase(prixDeBase.toDouble())
        } catch (e: NumberFormatException) {
            throw PrixInvalide(prixDeBase)
        }
    )

    fun changerLePrixDeBase(nouveauPrix: String) {
        try {
            this.prixDeBase = PrixDeBase(nouveauPrix.toDouble())
            domainEvents.add(
                PrixDeBaseAChangé(
                    id.id,
                    this.prixDeBase.montant.toString()
                )
            )
        } catch (e: NumberFormatException) {
            throw PrixInvalide(nouveauPrix)
        }
    }

    fun getDomainEvents(): List<Event> {
        return domainEvents
    }

    sealed class Errors(message: String) : Exception(message) {
        data class DuréeInvalide(val durée: String) : Errors("Durée invalide: $durée")
        data class PrixInvalide(val prix: String) : Errors("Durée invalide: $prix")
    }
}

