package example.pilotage.queries

import example.pilotage.AbonnementSouscritRepository
import example.pilotage.queries.ChiffreAffaireMensuelQueryHandler.Result.*
import java.time.LocalDate

class ChiffreAffaireMensuelQueryHandler (
    private val repository: AbonnementSouscritRepository
) {
    operator fun invoke(query: ChiffreAffaireMensuelQuery) : Result {
        return try {
            val startOfMonth = LocalDate.of(query.year, query.month, 1)
            val endOfMonth = LocalDate.of(query.year, query.month, startOfMonth.lengthOfMonth())
            val chiffreAffaireMensuel = repository.fetchAll().filter {
                it.dateDebut.compareTo(startOfMonth) <= 0
                    && it.dateFin.compareTo(endOfMonth) >= 0
            }.sumByDouble { it.prixMensuel }
            Success(chiffreAffaireMensuel)
        } catch (e: Exception) {
            Failure
        }
    }

    sealed class Result{
        data class Success(val chiffreAffaireMensuel: Double) : Result()
        object Failure : Result()
    }
}