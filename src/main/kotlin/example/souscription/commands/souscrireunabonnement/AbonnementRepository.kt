package example.souscription.commands.souscrireunabonnement

import example.souscription.domain.Abonnement

interface AbonnementRepository {
    fun persist(abonnement: Abonnement)
}
