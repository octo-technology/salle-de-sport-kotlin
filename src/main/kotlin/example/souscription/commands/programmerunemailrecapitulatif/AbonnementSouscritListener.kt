package example.souscription.commands.programmerunemailrecapitulatif

import example._sharedkernel.eventbus.Event
import example._sharedkernel.eventbus.EventListener
import example.souscription.domain.AbonnementSouscrit

class AbonnementSouscritListener(
    private val handler: ProgrammerUnEmailRecapitulatifCommandHandler
) : EventListener {
    override fun notify(event: Event) {
        if (event is AbonnementSouscrit) {
            handler(
                ProgrammerUnEmailRecapitulatifCommand(
                    event.intituléFormule,
                    event.prixFinal,
                    event.duréeFormule,
                    event.recipientEmailAddress,
                    event.isEtudiant,
                    event.dateDebut,
                    event.dateFin
                )
            )
        }
    }
}