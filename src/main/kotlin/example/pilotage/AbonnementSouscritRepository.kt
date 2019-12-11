package example.pilotage

import example.pilotage.domain.AbonnementSouscrit

interface AbonnementSouscritRepository {
    fun persist(abonnement: AbonnementSouscrit)
    fun fetchAll() : List<AbonnementSouscrit>
}

