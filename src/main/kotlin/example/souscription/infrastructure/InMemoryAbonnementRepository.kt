package example.souscription.infrastructure

import example.souscription.commands.souscrireunabonnement.AbonnementRepository
import example.souscription.domain.Abonnement
import example.souscription.domain.AbonnementId

class InMemoryAbonnementRepository : AbonnementRepository {
    private val collection = hashMapOf<AbonnementId, Abonnement>()
    override fun persist(abonnement: Abonnement) {
        collection[abonnement.id] = abonnement
    }
}