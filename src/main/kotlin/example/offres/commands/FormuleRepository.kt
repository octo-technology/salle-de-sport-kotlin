package example.offres.commands

import example.offres.domain.formule.Formule

interface FormuleRepository {
    fun persist(nouvelleFormule: Formule)

    @Throws(FormuleNotFound::class)
    fun fetch(forumleId: String): Formule

    data class FormuleNotFound(val forumleId: String): Exception("La Formule portant l'id $forumleId n'existe pas")
}
