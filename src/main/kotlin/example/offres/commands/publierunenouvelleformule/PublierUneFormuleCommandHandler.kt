package example.offres.commands.publierunenouvelleformule

import example._sharedkernel.eventbus.EventPublisher
import example.offres.commands.FormuleRepository
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommandHandler.Result.Success
import example.offres.domain.formule.Formule
import example.offres.domain.formule.Formule.Errors

// Le Handler orchestre : d'abord on fait ça, puis ça, puis ça
class PublierUneFormuleCommandHandler (
    private val formuleRepository: FormuleRepository,
    private val eventPublisher: EventPublisher
)
{
    operator fun invoke(
        command: PublierUneFormuleCommand
    ) : Result {
        return try {
            val nouvelleFormule = Formule.nouvelleFormule(
                command.intitulé,
                command.durée,
                command.prixDeBase
            )
            formuleRepository.persist(nouvelleFormule)
            eventPublisher.publish(nouvelleFormule.getDomainEvents())
            Success
        } catch (e: Errors) {
            Result.Failure
        }
    }

    sealed class Result {
        object Success: Result()
        object Failure: Result()
    }
}