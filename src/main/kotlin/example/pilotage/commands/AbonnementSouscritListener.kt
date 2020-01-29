package example.pilotage.commands

import example._sharedkernel.eventbus.EventListener

class AbonnementSouscritListener(
        private val handler: StoreAbonnementSouscritCommandHandler
) : EventListener<example.souscription.domain.AbonnementSouscrit> {
    override fun notify(event: example.souscription.domain.AbonnementSouscrit) {
        val prix = event.prixFinal.toDouble()
        val prixMensuel = if (event.duréeFormule == "ANNUELLE") {
            prix / 12
        } else {
            prix
        }

        handler(
                StoreAbonnementSouscritCommand(
                        event.dateDebut,
                        event.dateFin,
                        prixMensuel
                )
        )
    }

}
