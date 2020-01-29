package example.pilotage.infrastructure

import example._sharedkernel.eventbus.EventListener
import example.pilotage.AbonnementSouscritRepository
import example.pilotage.commands.AbonnementSouscritListener
import example.pilotage.commands.StoreAbonnementSouscritCommandHandler
import example.pilotage.queries.ChiffreAffaireMensuelQueryHandler
import example.pilotage.domain.AbonnementSouscrit

object DependenciesInjection {
    private var abonnementSouscritRepository: AbonnementSouscritRepository? = null
    private var storeAbonnementSouscritCommandHandler: StoreAbonnementSouscritCommandHandler? = null
    private var abonnementSouscritListener: EventListener<example.souscription.domain.AbonnementSouscrit>? = null
    private var chiffreAffaireMensuelQueryHandler: ChiffreAffaireMensuelQueryHandler? = null

    fun provideAbonnementSouscritRepository(): AbonnementSouscritRepository {
        if (abonnementSouscritRepository == null) {
            abonnementSouscritRepository = InMemoryAbonnementSouscritRepository()
        }
        return abonnementSouscritRepository!!
    }

    fun provideStoreAbonnementSouscritCommandHandler(): StoreAbonnementSouscritCommandHandler {
        if (storeAbonnementSouscritCommandHandler == null) {
            storeAbonnementSouscritCommandHandler = StoreAbonnementSouscritCommandHandler(
                provideAbonnementSouscritRepository()
            )
        }
        return storeAbonnementSouscritCommandHandler!!
    }

    fun provideAbonnementSouscritListener(): EventListener<example.souscription.domain.AbonnementSouscrit> {
        if (abonnementSouscritListener == null) {
            abonnementSouscritListener = AbonnementSouscritListener(
                provideStoreAbonnementSouscritCommandHandler()
            )
        }
        return abonnementSouscritListener!!
    }

    fun provideChiffreAffaireMensuelQueryHandler(): ChiffreAffaireMensuelQueryHandler {
        if (chiffreAffaireMensuelQueryHandler == null) {
            chiffreAffaireMensuelQueryHandler = ChiffreAffaireMensuelQueryHandler(
                provideAbonnementSouscritRepository()
            )
        }
        return chiffreAffaireMensuelQueryHandler!!
    }
}
