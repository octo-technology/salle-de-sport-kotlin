package example.pilotage.commands

import example.pilotage.domain.AbonnementSouscrit
import example.pilotage.AbonnementSouscritRepository

class StoreAbonnementSouscritCommandHandler(
    private val repository: AbonnementSouscritRepository
) {
    operator fun invoke(command:StoreAbonnementSouscritCommand) : Result {
        return try {
            repository.persist(
                AbonnementSouscrit(
                    command.dateDebut,
                    command.dateFin,
                    command.prixMensuel
                )
            )
            Result.Success
        } catch (e: Exception) { // TODO réduire le scope
            Result.Failure
        }
    }

    sealed class Result {
        object Success: Result()
        object Failure: Result()
    }
}