package example.pilotage.infrastructure

import example.pilotage.AbonnementSouscritRepository
import example.pilotage.domain.AbonnementSouscrit

class InMemoryAbonnementSouscritRepository : AbonnementSouscritRepository {
    private val collection = mutableListOf<AbonnementSouscrit>()
    override fun persist(abonnement: AbonnementSouscrit) {
        collection.add(abonnement)
    }

    override fun fetchAll(): List<AbonnementSouscrit> {
        return collection
    }
}