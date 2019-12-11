package example.pilotage.commands

import example._sharedkernel.eventbus.Event
import example._sharedkernel.eventbus.EventListener
import example.souscription.domain.AbonnementSouscrit

class AbonnementSouscritListener(
    private val handler: StoreAbonnementSouscritCommandHandler
) : EventListener {
    override fun notify(event: Event) {
        if (event is AbonnementSouscrit) {
            val prix = event.prixFinal.toDouble()
            handler(
                StoreAbonnementSouscritCommand(
                    event.dateDebut,
                    event.dateFin,
                    if (event.duréeFormule == "ANNUELLE") {
                        prix / 12
                    } else {
                        prix
                    }
                )
            )
        }
    }

}