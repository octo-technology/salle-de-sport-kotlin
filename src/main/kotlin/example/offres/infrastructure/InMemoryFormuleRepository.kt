package example.offres.infrastructure

import example.offres.commands.FormuleRepository
import example.offres.commands.FormuleRepository.FormuleNotFound
import example.offres.domain.formule.Formule
import example.offres.domain.formule.FormuleId

class InMemoryFormuleRepository : FormuleRepository {
    private val collection = hashMapOf<FormuleId, Formule>()
    override fun persist(nouvelleFormule: Formule) {
        collection[nouvelleFormule.id] = nouvelleFormule
    }

    override fun fetch(forumleId: String): Formule {
        val formuleTrouvée = collection[FormuleId(forumleId)]
        formuleTrouvée?.let {
            return it
        }
        throw FormuleNotFound(forumleId)
    }
}