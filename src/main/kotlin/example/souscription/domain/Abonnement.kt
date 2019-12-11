package example.souscription.domain

import example._sharedkernel.eventbus.Event
import example._sharedkernel.uuid.RandomUUIDGenerator
import example._sharedkernel.time.Today
import example.offres.domain.formule.Durée
import example.souscription.domain.Abonné.Etudiant
import example.souscription.domain.Abonné.Standard
import example.souscription.domain.Abonnement.Errors.DuréeInvalide
import example.souscription.domain.Abonnement.Errors.PrixInvalide
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// TODO TU
class Abonnement(
    val id: AbonnementId,
    val formuleChoisie: FormuleChoisie,
    val abonné: Abonné,
    val périodeDeValidité: PeriodeDeValidité
) {
    private val domainEvents = mutableListOf<Event>()

    companion object {
        fun souscrire(
            intituléFormuleChoisie: String,
            prixDeBaseFormuleChoisie: String,
            duréeFormuleChoisie: String,
            email: String,
            isEtudian: Boolean
        ): Abonnement {
            val abonnementSouscrit = Abonnement(
                intituléFormuleChoisie,
                prixDeBaseFormuleChoisie,
                duréeFormuleChoisie,
                email,
                isEtudian,
                PeriodeDeValidité(
                   Today.getDate(),
                   Today.getDate()
                )
            )
            val ratio = computeRatio(abonnementSouscrit)
            abonnementSouscrit.formuleChoisie.prix.appliquerRéduction(Réduction(ratio))

            val dateDeFin = computeDateDeFin(
                abonnementSouscrit.périodeDeValidité.dateDebut,
                abonnementSouscrit.formuleChoisie.durée
            )
            abonnementSouscrit.périodeDeValidité.dateFin = dateDeFin

            abonnementSouscrit.domainEvents.add(
                AbonnementSouscrit(
                    id = abonnementSouscrit.id.id,
                    recipientEmailAddress = abonnementSouscrit.abonné.email,
                    intituléFormule = abonnementSouscrit.formuleChoisie.intitulé,
                    prixFinal = abonnementSouscrit.formuleChoisie.prix.montant.toString(),
                    duréeFormule = abonnementSouscrit.formuleChoisie.durée.name,
                    isEtudiant = abonnementSouscrit.abonné is Etudiant,
                    dateDebut = abonnementSouscrit.périodeDeValidité.dateDebut,
                    dateFin = abonnementSouscrit.périodeDeValidité.dateFin
                )
            )
            return abonnementSouscrit
        }

        private fun computeDateDeFin(dateDebut: LocalDate, durée: Durée): LocalDate {
            return if (durée == Durée.MENSUELLE) {
                dateDebut.plus(1, ChronoUnit.MONTHS)
            } else {
                dateDebut.plus(1, ChronoUnit.YEARS)
            }
        }

        private fun computeRatio(abonnementSouscrit: Abonnement): Float {
            var ratio = 0f
            if (abonnementSouscrit.abonné is Etudiant) {
                ratio += 0.2f
            }
            if (abonnementSouscrit.formuleChoisie.durée == Durée.ANNUELLE) {
                ratio += 0.3f
            }
            return ratio
        }
    }

    constructor(
        intituléFormuleChoisie: String,
        prixDeBaseFormuleChoisie: String,
        duréeFormuleChoisie: String,
        email: String,
        isEtudian: Boolean,
        périodeDeValidité: PeriodeDeValidité
    ) : this(
        AbonnementId(RandomUUIDGenerator.generateId()),
        FormuleChoisie(
            intituléFormuleChoisie,
            try {
                Prix(prixDeBaseFormuleChoisie.toDouble())
            } catch (e: NumberFormatException) {
                throw PrixInvalide(prixDeBaseFormuleChoisie)
            },
            when (duréeFormuleChoisie) {
                "MENSUELLE" -> Durée.MENSUELLE
                "ANNUELLE" -> Durée.ANNUELLE
                else -> throw DuréeInvalide(duréeFormuleChoisie)
            }
        ),
        if (isEtudian) {
            Etudiant(email)
        } else {
            Standard(email)
        },
        périodeDeValidité
    )


    fun getDomainEvents(): List<Event> {
        return domainEvents
    }

    sealed class Errors(message: String) : Exception(message) {
        data class DuréeInvalide(val durée: String) : Errors("Durée invalide: $durée")
        data class PrixInvalide(val prix: String) : Errors("Durée invalide: $prix")
    }
}

