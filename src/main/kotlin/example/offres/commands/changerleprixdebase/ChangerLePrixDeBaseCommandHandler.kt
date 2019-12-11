package example.offres.commands.changerleprixdebase

import example._sharedkernel.eventbus.EventPublisher
import example.offres.commands.FormuleRepository
import example.offres.commands.FormuleRepository.FormuleNotFound
import example.offres.commands.changerleprixdebase.ChangerLePrixDeBaseCommandHandler.Result.Failure
import example.offres.commands.changerleprixdebase.ChangerLePrixDeBaseCommandHandler.Result.Success
import example.offres.domain.formule.Formule.Errors

class ChangerLePrixDeBaseCommandHandler (
    private val formuleRepository: FormuleRepository,
    private val eventPublisher: EventPublisher
)
{
    operator fun invoke(
        command: ChangerLePrixDeBaseCommand
    ) : Result {
        return try {
            val formule = formuleRepository.fetch(command.forumleId)
            formule.changerLePrixDeBase(command.nouveauPrixDeBase)
            formuleRepository.persist(formule)
            eventPublisher.publish(formule.getDomainEvents())
            Success
        } catch (e: FormuleNotFound) {
            Failure
        } catch (e: Errors) {
            Failure
        }
    }

    sealed class Result {
        object Success: Result()
        object Failure: Result()
    }
}