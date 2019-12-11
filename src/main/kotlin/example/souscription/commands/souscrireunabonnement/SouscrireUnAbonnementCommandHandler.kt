package example.souscription.commands.souscrireunabonnement

import example._sharedkernel.eventbus.EventPublisher
import example.souscription.commands.souscrireunabonnement.SouscrireUnAbonnementCommandHandler.Result.Failure
import example.souscription.commands.souscrireunabonnement.SouscrireUnAbonnementCommandHandler.Result.Success
import example.souscription.domain.Abonnement
import example.souscription.domain.Abonnement.Errors

//TODO TU
class SouscrireUnAbonnementCommandHandler(
    private val abonnementRepository: AbonnementRepository,
    private val eventPublisher: EventPublisher
) {
    operator fun invoke(command: SouscrireUnAbonnementCommand) : Result {
        return try {
            val abonnementSouscrit = Abonnement.souscrire(
                command.intituléFormuleChoisie,
                command.prixDeBaseFormuleChoisie,
                command.duréeFormuleChoisie,
                command.email,
                command.isEtudiant
            )
            abonnementRepository.persist(abonnementSouscrit)
            eventPublisher.publish(abonnementSouscrit.getDomainEvents())
            Success
        } catch (e: Errors) {
            Failure
        }
    }

    sealed class Result {
        object Success : Result()
        object Failure: Result()
    }
}